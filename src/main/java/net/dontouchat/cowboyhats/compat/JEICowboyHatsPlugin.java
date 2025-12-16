package net.dontouchat.cowboyhats.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.recipe.InfusionRecipe;
import net.dontouchat.cowboyhats.recipe.LeatherworkingRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEICowboyHatsPlugin implements IModPlugin {
    @Override
    @SuppressWarnings("removal")
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(CowboyHatsMod.MODID,"jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new LeatherworkingCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<LeatherworkingRecipe> leatherworkingRecipes = recipeManager.getAllRecipesFor(LeatherworkingRecipe.Type.INSTANCE);
        registration.addRecipes(LeatherworkingCategory.LEATHERWORKING_TYPE, leatherworkingRecipes);
    }


}
