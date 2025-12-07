package net.dontouchat.cowboyhats.block.entity;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.block.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, CowboyHatsMod.MODID);

    public static final RegistryObject<BlockEntityType<LeatherworkingTableBlockEntity>> LEATHERWORKINGTABLE_BE =
            BLOCK_ENTITIES.register("leatherworkingtable_be", () ->
                    BlockEntityType.Builder.of(LeatherworkingTableBlockEntity::new,
                            ModBlocks.LEATHER_WORKING_TABLE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}