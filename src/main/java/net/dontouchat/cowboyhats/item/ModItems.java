package net.dontouchat.cowboyhats.item;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CowboyHatsMod.MODID);

    public static final RegistryObject<Item> LEATHERSTRIPS = ITEMS.register("leatherstrips",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CATTLEMAN = ITEMS.register("cattleman",
            () -> new CattlemanArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDCATTLEMAN = ITEMS.register("armoredcattleman",
            () -> new CattlemanArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> GAMBLER = ITEMS.register("gambler",
            () -> new GamblerArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDGAMBLER = ITEMS.register("armoredgambler",
            () -> new GamblerArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> DERBY = ITEMS.register("derby",
            () -> new DerbyArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDDERBY = ITEMS.register("armoredderby",
            () -> new DerbyArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> OPENCROWN = ITEMS.register("opencrown",
            () -> new OpenCrownArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDOPENCROWN = ITEMS.register("armoredopencrown",
            () -> new OpenCrownArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> COMICAL = ITEMS.register("comical",
            () -> new ComicalArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDCOMICAL = ITEMS.register("armoredcomical",
            () -> new ComicalArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.HELMET, new Item.Properties()));


    public static final RegistryObject<Item> TRADITIONALBOOTS = ITEMS.register("traditionalboots",
            () -> new TraditionalBootsArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDTRADITIONALBOOTS = ITEMS.register("armoredtraditionalboots",
            () -> new TraditionalBootsArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BUCKAROOBOOTS = ITEMS.register("buckarooboots",
            () -> new BuckarooBootsArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.BOOTS, new Item.Properties()));
    public static final RegistryObject<Item> ARMOREDBUCKAROOBOOTS = ITEMS.register("armoredbuckarooboots",
            () -> new BuckarooBootsArmorItem(ModArmorMaterials.ARMOREDCOWBOY,ArmorItem.Type.BOOTS, new Item.Properties()));

    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }

}
