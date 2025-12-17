package net.dontouchat.cowboyhats.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.dontouchat.cowboyhats.item.custom.CowboyArmorItem;
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

public class InfusionRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final int tier;
    private final ResourceLocation id;
    private final boolean isReroll;

    public InfusionRecipe(NonNullList<Ingredient> inputItems, int tier, ResourceLocation id, boolean isReroll) {
        this.inputItems = inputItems;
        this.tier = tier;
        this.id = id;
        this.isReroll = isReroll;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {

        if(!inputItems.get(0).test(pContainer.getItem(0))){
            return false;
        }
        if(((CowboyArmorItem) pContainer.getItem(0).getItem()).getTier(pContainer.getItem(0)) != this.tier){
            return false;
        }
        for(int i = 1; i < pContainer.getContainerSize();i++){
            if(inputItems.size() <= i){
                if(!pContainer.getItem(i).isEmpty()){
                    return false;
                }
            }
            else{
                if(!inputItems.get(i).test(pContainer.getItem(i))){
                    return false;
                }
            }
        }

        return true;
    }

    public boolean getIsReroll(){return isReroll;}

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return ItemStack.EMPTY;
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

    public static class Type implements RecipeType<InfusionRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "infusion";
    }

    public static class Serializer implements RecipeSerializer<InfusionRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        @SuppressWarnings("removal")
        public static final ResourceLocation ID = new ResourceLocation(CowboyHatsMod.MODID, "infusion");

        @Override
        public InfusionRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
            int tier = GsonHelper.getAsInt(pSerializedRecipe, "tier", 1);

            boolean isReroll = GsonHelper.getAsBoolean(pSerializedRecipe,"isreroll",false);

            JsonArray ingredients = GsonHelper.getAsJsonArray(pSerializedRecipe, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new InfusionRecipe(inputs, tier, pRecipeId,isReroll);
        }

        @Override
        public @Nullable InfusionRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(pBuffer));
            }
            int tier = pBuffer.readVarInt();
            boolean isReroll = pBuffer.readBoolean();
            return new InfusionRecipe(inputs, tier, pRecipeId,isReroll);
        }

        @Override
        public void toNetwork(FriendlyByteBuf pBuffer, InfusionRecipe pRecipe) {
            pBuffer.writeInt(pRecipe.inputItems.size());

            for (Ingredient ingredient : pRecipe.getIngredients()) {
                ingredient.toNetwork(pBuffer);
            }
            pBuffer.writeVarInt(pRecipe.tier);
            pBuffer.writeBoolean(pRecipe.isReroll);
        }
    }
}