package net.ddns.minersonline.better_cc.blocks.powermachine;

import net.ddns.minersonline.better_cc.blocks.machinebase.UpgradeableMachineGUIBase;
import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class PowerMachineScreen extends UpgradeableMachineGUIBase<PowerMachineContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/term.png");

    public PowerMachineScreen(PowerMachineContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        setTEXTURE(TEXTURE);
        showInventory(true);
    }
}