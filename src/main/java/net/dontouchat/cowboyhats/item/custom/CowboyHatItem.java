package net.dontouchat.cowboyhats.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.ModArmorMaterials;
import net.dontouchat.cowboyhats.item.ModItems;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
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
    public enum CowboyEffect{
        LUCKY,
        ARMORED,
        TOUGH,
        HEAVY
    }
    private Map<CowboyEffect,String[]> effectmap = new HashMap();
    private Random random;
    public CowboyHatItem(ArmorMaterial pMaterial, Type slot, Properties pProperties,int tier)
    {
        super(pMaterial,slot,pProperties);
        this.effectmap.put(CowboyEffect.LUCKY,new String[]{"lucky","Lucky"});
        this.effectmap.put(CowboyEffect.ARMORED,new String[]{"armored","Armored"});
        this.effectmap.put(CowboyEffect.TOUGH,new String[]{"tough","Tough"});
        this.effectmap.put(CowboyEffect.HEAVY,new String[]{"heavy","Heavy"});
        this.random = new Random(System.currentTimeMillis());
    }
    public  int getTier(ItemStack pStack){return pStack.getTag().getInt("cowboyhats.tier");}
    public boolean tryLuck(ItemStack pStack){
        return pStack.getTag().getBoolean("cowboyhats." + CowboyEffect.LUCKY)
                && random.nextFloat() < 0.5f;
    }
    public ItemStack upgradeTier(ItemStack pStack){
        int tier =  getTier(pStack) + 1;
        ItemStack outItem = pStack.copy();
        CompoundTag nbtTier = new CompoundTag();
        nbtTier.putInt("cowboyhats.tier",tier);

        if(tier > 0)
        {
            List<CowboyEffect> effects = Arrays.asList(CowboyEffect.values());
            for(CowboyEffect e : effectmap.keySet()){

                if(pStack.getTag().getBoolean("cowboyhats." + effectmap.get(e)[0])){
                    effects.remove(e);
                }
            }

            CowboyEffect effect = effects.get(random.nextInt(effects.size()));

            if(effect == CowboyEffect.ARMORED){
            }

            nbtTier.putBoolean("cowboyhats." + effectmap.get(effect)[0],true);
            outItem.setTag(nbtTier);
            System.out.println(pStack.getTag());
        }

        return outItem;
    }

    // Let's add our animation controller
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, 20,
                state -> PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {return this.cache;}

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if(getTier(pStack)>0 && Screen.hasShiftDown()){
            for(CowboyEffect effect : effectmap.keySet()){
                if(pStack.getTag().getBoolean("cowboyhats." + effectmap.get(effect)[0])){
                    pTooltipComponents.add(Component.literal(effectmap.get(effect)[1]));
                }
            }
        }else{
            pTooltipComponents.add(Component.literal("Tier " + (getTier(pStack) + 1)));
            if(getTier(pStack) > 0){
                pTooltipComponents.add(Component.literal("press shift to see cowboy effects"));
            }
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

//        @Override
//    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
//        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
//        if(this.getType() == Type.HELMET && slotIndex == 39){
//        }
//    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        ItemStack hat = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!hat.isEmpty() && hat.getItem() instanceof CowboyHatItem)
        {
            if(((CowboyHatItem) hat.getItem()).tryLuck(hat)){
                event.setCanceled(true);
            }
        }
    }

}