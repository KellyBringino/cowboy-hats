package net.dontouchat.cowboyhats.recipe;


import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.CowboyHatItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class LeatherworkingRecipe implements Recipe<SimpleContainer> {
    private final ItemStack input;
    private final ItemStack extra;
    private final ItemStack output;
    private final ResourceLocation id;

    public LeatherworkingRecipe(ItemStack input, ItemStack extra, ItemStack output, ResourceLocation id) {
        this.input = input;
        this.extra = extra;
        this.output = output;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
//        if(pLevel.isClientSide()) {
//            return false;
//        }

        boolean in1 = input.is(pContainer.getItem(0).getItem())
                && input.getCount() <= pContainer.getItem(0).getCount();
        boolean in2 =
                (extra.is(pContainer.getItem(1).getItem())
                && extra.getCount() <= pContainer.getItem(1).getCount())
                || extra == ItemStack.EMPTY;

        return in1 && in2;
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> ingredients = NonNullList.withSize(2,Ingredient.EMPTY);
        ingredients.set(0,Ingredient.of(input));
        ingredients.set(1,Ingredient.of(extra));
        return ingredients;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }
    public ItemStack getInputItem() {
        return input.copy();
    }
    public ItemStack getExtraItem() {
        return extra.copy();
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<LeatherworkingRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "leatherworking";
    }

    public static class Serializer implements RecipeSerializer<LeatherworkingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        @SuppressWarnings("removal")
        public static final ResourceLocation ID = new ResourceLocation(CowboyHatsMod.MODID, "leatherworking");

        @Override
        public LeatherworkingRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "output"));
            ItemStack input = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "input"));
            ItemStack extra;
            try{
                extra = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "extra"));
            }catch (JsonSyntaxException e){
                extra = ItemStack.EMPTY;
            }

            return new LeatherworkingRecipe(input, extra, output, pRecipeId);
        }

        @Override
        public @Nullable LeatherworkingRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {

            ItemStack input = pBuffer.readItem();
            ItemStack extra = pBuffer.readItem();
            ItemStack output = pBuffer.readItem();
            return new LeatherworkingRecipe(input,extra, output, pRecipeId);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, LeatherworkingRecipe pRecipe) {
            pBuffer.writeItem(pRecipe.getInputItem());

            pBuffer.writeItem(pRecipe.getExtraItem());


            pBuffer.writeItem(pRecipe.output);
        }
    }
}