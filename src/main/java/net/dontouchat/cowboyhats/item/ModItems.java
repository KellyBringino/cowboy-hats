package net.dontouchat.cowboyhats.item;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.*;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
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
    public static final RegistryObject<Item> GAMBLER = ITEMS.register("gambler",
            () -> new GamblerArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> DERBY = ITEMS.register("derby",
            () -> new DerbyArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> OPENCROWN = ITEMS.register("opencrown",
            () -> new OpenCrownArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> COMICAL = ITEMS.register("comical",
            () -> new ComicalArmorItem(ModArmorMaterials.COWBOY,ArmorItem.Type.HELMET, new Item.Properties()));

    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }

}
