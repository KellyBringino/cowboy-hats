package net.dontouchat.cowboyhats.screen;

import net.dontouchat.cowboyhats.block.ModBlocks;
import net.dontouchat.cowboyhats.item.custom.CowboyHatItem;
import net.dontouchat.cowboyhats.recipe.InfusionRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import java.util.Optional;

public class InfusionMenu extends AbstractContainerMenu {
    private final Level level;
    private final ContainerLevelAccess access;
    private static final int INPUT_SLOT = 0;
    private static final int INFUSION_SLOTS_END = 7;

    public final Container container = new SimpleContainer(7) {
        public void setChanged() {
            super.setChanged();
            InfusionMenu.this.slotsChanged(this);
        }
    };


    public InfusionMenu(int pContainerId, Inventory pPlayerInventory, FriendlyByteBuf extraData) {
        this(pContainerId, pPlayerInventory, ContainerLevelAccess.NULL);
    }

    public InfusionMenu(int pContainerId, Inventory pPlayerInventory, ContainerLevelAccess pAccess) {
        super(ModMenuTypes.INFUSIONTABLE_MENU.get(), pContainerId);
        System.out.println("hi");
        this.level = pPlayerInventory.player.level();
        this.access = pAccess;

        this.addSlot(new Slot(this.container, 0, 80, 35));
        this.addSlot(new Slot(this.container, 1, 80, 62));
        this.addSlot(new Slot(this.container, 2, 80, 8));
        this.addSlot(new Slot(this.container, 3, 53, 24));
        this.addSlot(new Slot(this.container, 4, 53, 47));
        this.addSlot(new Slot(this.container, 5, 107, 24));
        this.addSlot(new Slot(this.container, 6, 107, 47));

        addPlayerInventory(pPlayerInventory);
        addPlayerHotbar(pPlayerInventory);
    }

    private void craftItem() {
        Optional oRecipe = getCurrentRecipe();
        if(oRecipe.isEmpty()){
            return;
        }
        InfusionRecipe recipe = (InfusionRecipe) oRecipe.get();
        for(int i = 1; i < INFUSION_SLOTS_END;i++){
            this.container.getItem(i).shrink(1);
            if (this.container.getItem(i).isEmpty()) {
                this.container.setItem(i, ItemStack.EMPTY);
            }
            this.slots.get(i).setChanged();
        }
        ItemStack hat = this.container.getItem(INPUT_SLOT);

        if(recipe.getIsReroll())
        {
            hat = ((CowboyHatItem)hat.getItem()).rerollEffects(hat);
        }else{
            hat = ((CowboyHatItem)hat.getItem()).upgradeTier(hat);
        }
        this.container.setItem(INPUT_SLOT,hat);
        this.slots.get(INPUT_SLOT).setChanged();
        this.container.setChanged();
    }

    private boolean hasRecipe() {
        Optional<InfusionRecipe> recipe = getCurrentRecipe();

        return recipe.isPresent();
    }
    private Optional<InfusionRecipe> getCurrentRecipe() {
        SimpleContainer inventory = (SimpleContainer) this.container;
//        for(int i = 0; i < this.container.getContainerSize(); i++) {
//            inventory.setItem(i, this.container.getItem(i));
//        }

        return this.level.getRecipeManager().getRecipeFor(InfusionRecipe.Type.INSTANCE, inventory, level);
    }

    public boolean clickMenuButton(Player pPlayer, int pId) {
        craftItem();
        return true;
    }

    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, pos) -> {
            this.clearContainer(pPlayer, this.container);
        });
    }

    @Override
    public boolean stillValid(Player pPlayer) {return stillValid(this.access, pPlayer, ModBlocks.INFUSION_TABLE.get());}

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots
    // and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT *
            PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

    // THIS YOU HAVE TO DEFINE!
    private static final int TE_INVENTORY_SLOT_COUNT = 7;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack,
                    VANILLA_FIRST_SLOT_INDEX,
                    VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT,
                    false)
            ) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }
}