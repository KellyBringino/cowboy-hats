package net.dontouchat.cowboyhats.block.entity;

import com.google.common.collect.Lists;
import net.dontouchat.cowboyhats.recipe.LeatherworkingRecipe;
import net.dontouchat.cowboyhats.screen.LeatherworkingTableMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LeatherworkingTableBlockEntity extends BlockEntity implements MenuProvider {
    Runnable slotUpdateListener = () -> {
    };
    private final ItemStackHandler itemHandler = new ItemStackHandler(3){
        @Override
        protected void onContentsChanged(int slot) {
            LeatherworkingTableBlockEntity.this.slotsChanged(this);
            LeatherworkingTableBlockEntity.this.slotUpdateListener.run();
            super.onContentsChanged(slot);
        }
    };

    private static final int INPUT_SLOT1 = 0;
    private static final int INPUT_SLOT2 = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    private List<LeatherworkingRecipe> recipes = Lists.newArrayList();
    public final DataSlot selectedRecipeIndex = DataSlot.standalone();
    private ItemStack input = ItemStack.EMPTY;
    private ItemStack extra = ItemStack.EMPTY;

    public LeatherworkingTableBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.LEATHERWORKINGTABLE_BE.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots() - 1; i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.cowboyhats.leatherworkingtable");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new LeatherworkingTableMenu(pContainerId, pPlayerInventory, this);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public boolean clickMenuButton(Player pPlayer, int pId) {
        if (this.isValidRecipeIndex(pId)) {
            this.selectedRecipeIndex.set(pId);
            this.setupResultSlot();
        }
        return true;
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        slotsChanged(itemHandler);
    }

    public void setupResultSlot() {
        if (!this.recipes.isEmpty() && this.isValidRecipeIndex(this.selectedRecipeIndex.get())) {
            LeatherworkingRecipe leatherworkingRecipe = this.recipes.get(this.selectedRecipeIndex.get());
            ItemStack itemstack = leatherworkingRecipe.assemble(new SimpleContainer(itemHandler.getSlots()), this.level.registryAccess());
            if (itemstack.isItemEnabled(this.level.enabledFeatures())) {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT,itemstack);
            } else {
                this.itemHandler.setStackInSlot(OUTPUT_SLOT,ItemStack.EMPTY);
            }
        } else {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT,ItemStack.EMPTY);
        }
    }

    public boolean isValidRecipeIndex(int pRecipeIndex) {
        return pRecipeIndex >= 0 && pRecipeIndex < this.recipes.size();
    }
    public void slotsChanged(ItemStackHandler pInventory) {
        ItemStack itemstack1 = pInventory.getStackInSlot(0);
        ItemStack itemstack2 = pInventory.getStackInSlot(1);
        if (
                !itemstack1.is(this.input.getItem())
                || itemstack1.getCount() != this.input.getCount()
                || !itemstack2.is(this.extra.getItem())
                || itemstack2.getCount() != this.extra.getCount()
        ) {
            this.input = itemstack1.copy();
            this.extra = itemstack2.copy();
            this.setupRecipeList(pInventory);
            this.setupResultSlot();
            setChanged(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    private void setupRecipeList(ItemStackHandler pItemStackHandler) {
        this.recipes.clear();
        this.selectedRecipeIndex.set(-1);
        ItemStack stack1 = pItemStackHandler.getStackInSlot(0);
        ItemStack stack2 = pItemStackHandler.getStackInSlot(1);
        this.itemHandler.setStackInSlot(OUTPUT_SLOT,ItemStack.EMPTY);
        if (!stack1.isEmpty() || !stack2.isEmpty()) {
            SimpleContainer inventory = new SimpleContainer(pItemStackHandler.getSlots());
            for(int i = 0; i < pItemStackHandler.getSlots(); i++) {
                inventory.setItem(i, pItemStackHandler.getStackInSlot(i));
            }
            this.recipes = this.level.getRecipeManager().getRecipesFor(LeatherworkingRecipe.Type.INSTANCE,inventory,getLevel());
        }
    }

    private ItemStack craftItem() {
        LeatherworkingRecipe lwr = getCurrentRecipe();
        if(lwr != null)
        {
            this.itemHandler.extractItem(INPUT_SLOT1, lwr.getInputItem().getCount(), false);
            this.itemHandler.extractItem(INPUT_SLOT2, lwr.getExtraItem().getCount(), false);
            setupResultSlot();
            return this.itemHandler.getStackInSlot(OUTPUT_SLOT);
        }
        return ItemStack.EMPTY;
    }

    private boolean hasRecipe() {
        LeatherworkingRecipe recipe = getCurrentRecipe();
        return recipe != null;
    }

    private LeatherworkingRecipe getCurrentRecipe() {
        return this.recipes.get(this.selectedRecipeIndex.get());
    }

    public ItemStack takeItem(){
        return craftItem();
    }

    public List<ItemStack> getRelevantItems() {return List.of(itemHandler.getStackInSlot(0));}
    public int getSelectedRecipeIndex() {return this.selectedRecipeIndex.get();}
    public void registerUpdateListener(Runnable pListener) {slotUpdateListener = pListener;}
    public boolean hasInputItem() {return !itemHandler.getStackInSlot(0).isEmpty() && !this.recipes.isEmpty();}
    public List<LeatherworkingRecipe> getRecipes() {return this.recipes;}
    public int getNumRecipes() {return this.recipes.size();}
}