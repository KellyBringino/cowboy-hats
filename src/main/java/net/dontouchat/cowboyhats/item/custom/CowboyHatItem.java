package net.dontouchat.cowboyhats.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.ModArmorMaterials;
import net.minecraft.Util;
import net.minecraft.client.gui.screens.Screen;
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
    private static final EnumMap<ArmorItem.Type, UUID> ARMOR_MODIFIER_UUID_PER_TYPE = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266744_) -> {
        p_266744_.put(ArmorItem.Type.BOOTS, UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"));
        p_266744_.put(ArmorItem.Type.LEGGINGS, UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"));
        p_266744_.put(ArmorItem.Type.CHESTPLATE, UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"));
        p_266744_.put(ArmorItem.Type.HELMET, UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"));
    });
    private EnumMap<ArmorItem.Type, Integer> armoredProtForType = Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266655_) -> {
        p_266655_.put(ArmorItem.Type.BOOTS, 3);
        p_266655_.put(ArmorItem.Type.LEGGINGS, 6);
        p_266655_.put(ArmorItem.Type.CHESTPLATE, 8);
        p_266655_.put(ArmorItem.Type.HELMET, 3);
    });
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private final int tier;
    public enum CowboyEffect{
        LUCKY,
        ARMORED,
        TOUGH,
        HEAVY
    }
    private Map<CowboyEffect,Boolean> cowboyModifiers = new HashMap<>();
    private Random random;
    private  int defense;
    private  float toughness;
    protected  float knockbackResistance;
    private final Multimap<Attribute, AttributeModifier> defaultModifiers;
    public CowboyHatItem(ArmorMaterial pMaterial, Type slot, Properties pProperties,int tier)
    {
        super(pMaterial,slot,pProperties);
        this.tier = tier;
        this.random = new Random(System.currentTimeMillis());

        this.defense = pMaterial.getDefenseForType(getType());
        this.toughness = pMaterial.getToughness();
        this.knockbackResistance = pMaterial.getKnockbackResistance();
        for(int i=0;i<CowboyEffect.values().length;i++)
        {
            cowboyModifiers.put(CowboyEffect.values()[i],false);
        }

        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();

        if(tier > 1)
        {
            Random random = new Random(System.currentTimeMillis());
            for(int i = 1; i == tier;i++){
                CowboyEffect curEffect = CowboyEffect.values()[random.nextInt(CowboyEffect.values().length)];
                cowboyModifiers.put(curEffect,true);
            }
            if(cowboyModifiers.get(CowboyEffect.ARMORED)){
                this.defense = this.armoredProtForType.get(getType());
            }
            if(cowboyModifiers.get(CowboyEffect.TOUGH)){
                this.toughness = 3.0f;
            }
            if(cowboyModifiers.get(CowboyEffect.HEAVY)){
                this.knockbackResistance = 0.1f;
            }
        }
        UUID uuid = ARMOR_MODIFIER_UUID_PER_TYPE.get(getType());
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier", (double)this.defense, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness", (double)this.toughness, AttributeModifier.Operation.ADDITION));
        if (this.knockbackResistance > 0) {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.knockbackResistance, AttributeModifier.Operation.ADDITION));
        }

        this.defaultModifiers = builder.build();
    }
    public int getTier(){return tier;}
    public boolean tryLuck(){return cowboyModifiers.get(CowboyEffect.LUCKY) && random.nextFloat() < 0.5f;}
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        return pEquipmentSlot ==
                this.type.getSlot() ?
                this.defaultModifiers :
                super.getDefaultAttributeModifiers(pEquipmentSlot);
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
        String tipToAdd = "";
        if(getTier()>1 && Screen.hasShiftDown()){
            if(cowboyModifiers.get(CowboyEffect.ARMORED)){
                tipToAdd += "Armored\n";
            }
            if(cowboyModifiers.get(CowboyEffect.TOUGH)){
                tipToAdd += "Tough\n";
            }
            if(cowboyModifiers.get(CowboyEffect.HEAVY)){
                tipToAdd += "Heavy\n";
            }
            if(cowboyModifiers.get(CowboyEffect.LUCKY)){
                tipToAdd += "Lucky\n";
            }
        }else{
            tipToAdd = "Tier " + getTier();
            if(getTier() > 1){
                tipToAdd += " press shift to see cowboy effects";
            }
        }
        pTooltipComponents.add(Component.literal(tipToAdd));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    //    @Override
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
            if(((CowboyHatItem) hat.getItem()).tryLuck()){
                event.setCanceled(true);
            }
        }
    }

}