package net.ddns.minersonline.better_cc.blocks.computer;

import net.ddns.minersonline.better_cc.UpgradeableMachineGUIBase;
import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ComputerScreen extends UpgradeableMachineGUIBase<ComputerContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/term.png");

    public ComputerScreen(ComputerContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        setTEXTURE(TEXTURE);
        showInventory(true);
    }

    @Override
    public final boolean keyPressed( int key, int scancode, int modifiers )
    {
        //container.
        //cpuRAM.key = key;
        //cpuCore.setProgramCounter(0x38); // RST38H, keyboard interrupt

        return super.keyPressed( key, scancode, modifiers );
    }

}