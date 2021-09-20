package net.ddns.minersonline.better_cc.blocks.machinebase;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIntArray;

public abstract class UpgradeableMachineContainerBase extends Container {
    public final IInventory inventory;
    public IIntArray fields;


    public UpgradeableMachineContainerBase(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields, ContainerType type) {
        super(type, id);
        this.inventory = inventory;
        this.fields = fields;

        // Upgrades
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                int index = x + y;
                int posX = 98 + x * 18;
                int posY = 22 + y * 18;
                this.addSlot(new Slot(this.inventory, index, posX, posY));
            }
        }

        // Player backpack
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                int index = 9 + x + y * 9;
                int posX = 44 + x * 18;
                int posY = 98 + y * 18;
                this.addSlot(new Slot(playerInventory, index, posX, posY));
            }
        }

        // Player hotbar
        for (int x = 0; x < 9; ++x) {
            int posX = 44 + x * 18;
            int posY = 156;
            this.addSlot(new Slot(playerInventory, x, posX, posY));
        }
    }


    @Override
    public boolean stillValid(PlayerEntity player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            final int inventorySize = 2;
            final int playerInventoryEnd = inventorySize + 27;
            final int playerHotbarEnd = playerInventoryEnd + 9;

            if (index == 1) {
                if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (index != 0) {
                if (!this.moveItemStackTo(itemstack1, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, inventorySize, playerHotbarEnd, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

}

