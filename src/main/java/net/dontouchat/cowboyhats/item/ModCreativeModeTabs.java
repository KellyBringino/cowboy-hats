package net.dontouchat.cowboyhats.item;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CowboyHatsMod.MODID);

    public static final RegistryObject<CreativeModeTab> COWBOY_HAT_TAB = CREATIVE_MODE_TABS.register("cowboy_hat_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CATTLEMAN.get()))
                    .title(Component.translatable("creativetab.cowboy_hat_tab"))
                    .displayItems((pParameters, pOutput) ->{
                        pOutput.accept(ModBlocks.LEATHER_WORKING_TABLE.get());
                        pOutput.accept(ModBlocks.INFUSION_TABLE.get());
                        pOutput.accept(ModItems.LEATHERSTRIPS.get());
                        pOutput.accept(ModItems.SARSAPARILLA.get());
                        pOutput.accept(ModItems.CATTLEMAN.get());
                        pOutput.accept(ModItems.GAMBLER.get());
                        pOutput.accept(ModItems.DERBY.get());
                        pOutput.accept(ModItems.OPENCROWN.get());
                        pOutput.accept(ModItems.COMICAL.get());
                        pOutput.accept(ModItems.TRADITIONALBOOTS.get());
                        pOutput.accept(ModItems.BUCKAROOBOOTS.get());
                        pOutput.accept(ModItems.SLICKBOOTS.get());
                        pOutput.accept(ModItems.SNAKESKINBOOTS.get());
                        pOutput.accept(ModItems.PLASTICBOOTS.get());
                    })
                    .build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
