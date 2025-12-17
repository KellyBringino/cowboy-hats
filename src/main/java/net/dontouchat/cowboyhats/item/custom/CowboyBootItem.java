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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@Mod.EventBusSubscriber(modid = CowboyHatsMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CowboyBootItem extends CowboyArmorItem {
    private Random random;
    private final float luckChance = 0.1f;
    private final float travelDist = 500.0f;
    private final int travelEXP = 5;
    public enum CowboyEffect{
        LUCKY,
        ARMORED,
        TRAVELER,
        FASTRIDER,
        ESCAPERIDER,
        SPRINGY
    }
    private Map<CowboyEffect,String[]> effectmap = new HashMap();
    public CowboyBootItem(ArmorMaterial pMaterial, Type slot, Properties pProperties)
    {
        super(pMaterial,slot,pProperties);

        this.effectmap.put(CowboyEffect.LUCKY,new String[]{"lucky","Lucky"});
        this.effectmap.put(CowboyEffect.ARMORED,new String[]{"armored","Armored"});
        this.effectmap.put(CowboyEffect.TRAVELER,new String[]{"traveler","Traveler"});
        this.effectmap.put(CowboyEffect.FASTRIDER,new String[]{"fastrider","Fast Rider"});
        this.effectmap.put(CowboyEffect.ESCAPERIDER,new String[]{"escaperider","Escape Rider"});
        this.effectmap.put(CowboyEffect.SPRINGY,new String[]{"springy","Springy"});
        this.random = new Random(System.currentTimeMillis());
    }
    public boolean tryLuck(ItemStack pStack){
        return pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.LUCKY)[0])
                && random.nextFloat() <= luckChance;
    }
    public boolean tryTravel(ItemStack pStack){
        return pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.TRAVELER)[0]);
    }
    public boolean tryEscape(LivingEntity entity, ItemStack pStack){
        return pStack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.ESCAPERIDER)[0]) &&
                entity.isPassenger();
    }
    private CompoundTag NBTBase(CompoundTag NBT){
        NBT.putInt("cowboyhats.tier",0);
        for(CowboyEffect e : effectmap.keySet()){
            NBT.putBoolean("cowboyhats." + effectmap.get(e)[0],false);
        }
        return NBT;
    }
    @Override
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
    @Override
    public ItemStack rerollEffects(ItemStack pStack){
        int tier =  getTier(pStack);
        ItemStack outItem = new ItemStack(getArmoredVariant(false),1);
        CompoundTag nbtTags = NBTBase(pStack.getTag());
        outItem.setTag(nbtTags);
        for(int i = 0; i < tier;i++){
            outItem = upgradeTier(outItem);
        }
        return outItem;
    }

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
        return ModItems.TRADITIONALBOOTS.get();
    }

        @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);
        if(slotIndex == 36){
            if(player.isPassenger() && stack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.FASTRIDER)[0])){
                Entity entity = player.getVehicle();
                if(entity instanceof LivingEntity){
                    ((LivingEntity)entity).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,20));
                }
            }

            if(stack.getTag().getBoolean("cowboyhats." + effectmap.get(CowboyEffect.SPRINGY)[0])){
                player.addEffect(new MobEffectInstance(MobEffects.JUMP,20));
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event){
        LivingEntity entity = event.getEntity();
        ItemStack boot = entity.getItemBySlot(EquipmentSlot.FEET);
        if (!boot.isEmpty() && boot.getItem() instanceof CowboyBootItem)
        {
            if(((CowboyBootItem) boot.getItem()).tryEscape(entity,boot)){
                ((LivingEntity) entity.getVehicle()).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,2));
            }

            if(((CowboyBootItem) boot.getItem()).tryLuck(boot)){
                event.setCanceled(true);
            }
        }

        if(entity.isVehicle()){
            boot = ((LivingEntity) entity.getFirstPassenger()).getItemBySlot(EquipmentSlot.FEET);
            if (!boot.isEmpty() && boot.getItem() instanceof CowboyBootItem){
                if(((CowboyBootItem) boot.getItem()).tryEscape((LivingEntity) entity.getFirstPassenger(),boot)){
                    entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,2));
                }
            }
        }
    }
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){
        Player player = event.player;
        ItemStack boot = player.getItemBySlot(EquipmentSlot.FEET);
        if(!boot.isEmpty() && boot.getItem() instanceof CowboyBootItem){
            if(((CowboyBootItem) boot.getItem()).tryTravel(boot)){
                float dist = boot.getTag().getFloat("cowboyhats.distance");
                double deltaX = player.getX() - player.xOld;
                double deltaY = player.getY() - player.yOld;
                double deltaZ = player.getZ() - player.zOld;
                double distMoved = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
                dist += (float) distMoved;
                if(dist >= ((CowboyBootItem) boot.getItem()).travelDist){
                    player.giveExperiencePoints(((CowboyBootItem) boot.getItem()).travelEXP);
                    dist = 0.0f;
                }
                CompoundTag tag = boot.getTag();
                tag.putFloat("cowboyhats.distance",dist);
                boot.setTag(tag);
            }
        }
    }
}