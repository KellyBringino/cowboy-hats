package net.dontouchat.cowboyhats.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.block.ModBlocks;
import net.dontouchat.cowboyhats.recipe.LeatherworkingRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("removal")
public class LeatherworkingCategory implements IRecipeCategory<LeatherworkingRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(CowboyHatsMod.MODID, "leatherworking");
    public static final ResourceLocation TEXTURE = new ResourceLocation(CowboyHatsMod.MODID,
            "textures/gui/leatherworkingtable_gui.png");

    public static final RecipeType<LeatherworkingRecipe> LEATHERWORKING_TYPE =
            new RecipeType<>(UID, LeatherworkingRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public LeatherworkingCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 0, 0, 176, 85);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.LEATHER_WORKING_TABLE.get()));
    }

    @Override
    public RecipeType<LeatherworkingRecipe> getRecipeType() {
        return LEATHERWORKING_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("block.cowboyhats.leatherworkingtable");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, LeatherworkingRecipe recipe, IFocusGroup iFocusGroup) {
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 25).addItemStack(recipe.getInputItem());
        builder.addSlot(RecipeIngredientRole.INPUT, 20, 43).addItemStack(recipe.getExtraItem());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 143, 33).addItemStack(recipe.getResultItem(null));

    }
}
