package net.dontouchat.cowboyhats.recipe;

import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, CowboyHatsMod.MODID);

    public static final RegistryObject<RecipeSerializer<LeatherworkingRecipe>> LEATHERWORKINGTABLE_SERIALIZER =
            SERIALIZERS.register("leatherworking", () -> LeatherworkingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<InfusionRecipe>> INFUSIONTABLE_SERIALIZER =
            SERIALIZERS.register("infusion", () -> InfusionRecipe.Serializer.INSTANCE);

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}