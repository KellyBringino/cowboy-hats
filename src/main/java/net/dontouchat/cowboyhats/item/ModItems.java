package net.dontouchat.cowboyhats.item;

import net.dontouchat.cowboyhats.CowboyHatsMod;
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

    public static void register(IEventBus eventbus){
        ITEMS.register(eventbus);
    }

}
