package net.ddns.minersonline.better_cc.blocks.powermachine;

import net.ddns.minersonline.better_cc.blocks.machinebase.UpgradableMachineTileEntityBase;
import net.ddns.minersonline.better_cc.setup.ModTileEntityTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class PowerMachineTileEntity extends UpgradableMachineTileEntityBase {

    public PowerMachineTileEntity() {
        super(ModTileEntityTypes.POWER_MACHINE.get());
    }
    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container.better-cc.power_machine");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        System.out.println("OPEN power machine");
        return new PowerMachineContainer(id, playerInventory, this, this.fields);
    }

    public void encodeExtraData(PacketBuffer buffer) {
        buffer.writeByte(fields.getCount());
    }

}