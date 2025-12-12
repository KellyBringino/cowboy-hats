package net.dontouchat.cowboyhats.item.custom;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.*;

@Mod.EventBusSubscriber(modid = CowboyHatsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CowboyHatItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private Random random;
    private final float luckChance = 0.1f;
    private final float pickpocketChance = 0.1f;
    private Map<Item,Float> pickpocketItems = new HashMap();
    public enum CowboyEffect{
        LUCKY,
        ARMORED,
        RELAXEDSLEEPER,
        TOUGHRIDER,
        PICKPOCKET,
        BLOODTHIRSTY
    }
    private Map<CowboyEffect,String[]> effectmap = new HashMap();
    public CowboyHatItem(ArmorMaterial pMaterial, Type slot, Properties pProperties,int tier)
    {
        super(pMaterial,slot,pProperties);

        this.pickpocketItems.put(Items.IRON_NUGGET,0.5f);
        this.pickpocketItems.put(Items.GOLD_NUGGET,0.4f);
        this.pickpocketItems.put(Items.EMERALD,0.095f);
        this.pickpocketItems.put(Items.DIAMOND,0.005f);

        this.effectmap.put(CowboyEffect.LUCKY,new String[]{"lucky","Lucky"});
        this.effectmap.put(CowboyEffect.ARMORED,new String[]{"armored","Armored"});
        this.effectmap.put(CowboyEffect.RELAXEDSLEEPER,new String[]{"relaxedsleeper","Relaxed Sleeper"});
        this.effectmap.put(CowboyEffect.TOUGHRIDER,new String[]{"toughrider","Tough Rider"});
        this.effectmap.put(CowboyEffect.PICKPOCKET,new String[]{"pickpocket","Pickpocket"});
        this.effectmap.put(CowboyEffect.BLOODTHIRSTY,new String[]{"bloodthirsty","Bloodthirsty"});
        this.random = new Random(System.currentTimeMillis());
    }
    public  int getTier(ItemStack pStack){return pStack.getTag().getInt("cowboyhats.tier");}
    public boolean tryLuck(ItemStack pStack){
        return pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.LUCKY)[0])
                && random.nextFloat() <= luckChance;
    }
    public boolean tryWakeupEffect(ItemStack pStack){
        return pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.RELAXEDSLEEPER)[0]);
    }
    public void tryPickpocket(ItemStack pStack, Player player){
        if (pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.PICKPOCKET)[0])){
            if(random.nextFloat() < pickpocketChance){
                float itemChance = random.nextFloat();
                float platform = 0.0f;
                for(Item i : pickpocketItems.keySet()){
                    if(itemChance <= pickpocketItems.get(i) + platform){
                        player.addItem(new ItemStack(i));
                        break;
                    }else{
                        platform += pickpocketItems.get(i);
                    }
                }
            }
        }
    }
    public void tryBloodthirst(ItemStack pStack, Player player){
        if (pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.BLOODTHIRSTY)[0]))
        {
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,80));
        }
    }
    private CompoundTag NBTBase(CompoundTag NBT){
        NBT.putInt("cowboyhats.tier",0);
        for(CowboyEffect e : effectmap.keySet()){
            NBT.putBoolean("cowboyhats." + effectmap.get(e)[0],false);
        }
        return NBT;
    }
    public ItemStack upgradeTier(ItemStack pStack){
        int tier =  getTier(pStack) + 1;
        ItemStack outItem = pStack.copy();
        CompoundTag nbtTier = pStack.getTag();
        nbtTier.putInt("cowboyhats.tier",tier);

        if(tier > 0)
        {
            List<CowboyEffect> effects = new ArrayList(Arrays.asList(CowboyEffect.values()));
            for(CowboyEffect e : effectmap.keySet()){
                if(pStack.getTag().getBoolean("cowboyhats." + effectmap.get(e)[0])){
                    effects.remove(e);
                }
            }

            CowboyEffect effect = effects.get(random.nextInt(effects.size()));

            if(effect == CowboyEffect.ARMORED){
                ItemStack oItem = new ItemStack(getArmoredVariant(true),1);
                outItem = oItem;
            }

            nbtTier.putBoolean("cowboyhats." + effectmap.get(effect)[0],true);
            outItem.setTag(nbtTier);
        }

        return outItem;
    }

    public ItemStack rerollEffects(ItemStack pStack){
        int tier =  getTier(pStack);
        System.out.println(tier);
        ItemStack outItem = new ItemStack(getArmoredVariant(false),1);
        CompoundTag nbtTags = NBTBase(pStack.getTag());
        outItem.setTag(nbtTags);
        for(int i = 0; i < tier;i++){
            System.out.println(outItem.getTag());
            outItem = upgradeTier(outItem);
        }
        return outItem;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20,
                state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;}

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        Style style = Style.EMPTY.withColor(TextColor.fromRgb(0xA9A9A9)).withItalic(true);
        if(getTier(pStack)>0 && Screen.hasShiftDown()){
            for(CowboyEffect effect : effectmap.keySet()){
                if(pStack.getTag().getBoolean("cowboyhats." + effectmap.get(effect)[0])){
                    pTooltipComponents.add(Component.literal(effectmap.get(effect)[1]).withStyle(style));
                }
            }
        }else{
            pTooltipComponents.add(Component.literal("Tier " + (getTier(pStack) + 1)).withStyle(style));
            if(getTier(pStack) > 0){
                pTooltipComponents.add(Component.literal("press shift to see cowboy effects").withStyle(style));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public ItemLike getArmoredVariant(boolean value){
        if(value){
            return ModItems.ARMOREDCATTLEMAN.get();
        }else{
            return ModItems.CATTLEMAN.get();
        }
    }

        @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if(slotIndex == 39){
            if(player.isPassenger() && stack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.TOUGHRIDER)[0])){
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,20));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        ItemStack hat = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!hat.isEmpty() && hat.getItem() instanceof CowboyHatItem)
        {
            if(((CowboyHatItem) hat.getItem()).tryLuck(hat)){
                event.setCanceled(true);
                return;
            }
        }

        Entity hurter = event.getSource().getEntity();
        if(hurter instanceof Player && entity instanceof Monster) {
            Player player = (Player) hurter;
            hat = player.getItemBySlot(EquipmentSlot.HEAD);
            ((CowboyHatItem) hat.getItem()).tryPickpocket(hat,player);
        }
    }

    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event){
        Player player = event.getEntity();
        ItemStack hat = player.getItemBySlot(EquipmentSlot.HEAD);
        if (!hat.isEmpty() && hat.getItem() instanceof CowboyHatItem)
        {
            if(((CowboyHatItem) hat.getItem()).tryWakeupEffect(hat)){
                player.addEffect(new MobEffectInstance(MobEffects.SATURATION,20));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION,200));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event){
        LivingEntity entity = event.getEntity();
        Entity hurter = event.getSource().getEntity();
        if(hurter instanceof Player){
            Player player = (Player) hurter;
            ItemStack hat = player.getItemBySlot(EquipmentSlot.HEAD);
            if (!hat.isEmpty() && hat.getItem() instanceof CowboyHatItem)
            {
                ((CowboyHatItem) hat.getItem()).tryBloodthirst(hat,player);
            }
        }
    }
}