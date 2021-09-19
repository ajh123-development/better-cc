package net.ddns.minersonline.better_cc.blocks.computer;

import net.ddns.minersonline.better_cc.UpgradeableMachineContainerBase;
import net.ddns.minersonline.better_cc.setup.ModContainerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class ComputerContainer extends UpgradeableMachineContainerBase {
    public ComputerContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, new ComputerTileEntity(), new IntArray(buffer.readByte()));
    }

    public ComputerContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields) {
        super(id, playerInventory, inventory, fields, ModContainerTypes.COMPUTER.get());
    }
}