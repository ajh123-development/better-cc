package net.ddns.minersonline.better_cc.blocks.powermachine;

import net.ddns.minersonline.better_cc.blocks.machinebase.UpgradeableMachineContainerBase;
import net.ddns.minersonline.better_cc.setup.ModContainerTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;

public class PowerMachineContainer extends UpgradeableMachineContainerBase {
    public PowerMachineContainer(int id, PlayerInventory playerInventory, PacketBuffer buffer) {
        this(id, playerInventory, new PowerMachineTileEntity(), new IntArray(buffer.readByte()));
    }

    public PowerMachineContainer(int id, PlayerInventory playerInventory, IInventory inventory, IIntArray fields) {
        super(id, playerInventory, inventory, fields, ModContainerTypes.POWER_MACHINE.get());
    }
}