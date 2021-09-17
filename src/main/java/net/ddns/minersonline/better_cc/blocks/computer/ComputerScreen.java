package net.ddns.minersonline.better_cc.blocks.computer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.ddns.minersonline.better_cc.better_cc;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class ComputerScreen extends ContainerScreen<ComputerContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/term.png");
    public static final ResourceLocation UTILS_TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/utils.png");
    ComputerContainer container;
    int xSize, ySize;

    public ComputerScreen(ComputerContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);

        this.xSize = 248;
        this.ySize = 177;

        this.container = container;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY-8, 4210752);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        this.renderBackground(matrixStack);

        super.render(matrixStack, x, y, partialTicks);
        this.renderTooltip(matrixStack, x, y);

    }

    @Override
    public final boolean keyPressed( int key, int scancode, int modifiers )
    {
        //container.
        //cpuRAM.key = key;
        //cpuCore.setProgramCounter(0x38); // RST38H, keyboard interrupt

        return super.keyPressed( key, scancode, modifiers );
    }


    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) {
            return;
        }

        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(TEXTURE);

        int posX = (this.width - this.xSize) /  2;
        int posY = (this.height - this.ySize) / 2;

        blit(matrixStack, posX, posY, 0, 0, this.xSize, this.ySize);

        // Power Bar
        minecraft.getTextureManager().bind(UTILS_TEXTURE);
        blit(matrixStack, posX, posY , 12, 31, 15, 54);
        blit(matrixStack, posX, posY , 29, 31, 21, 65);
    }
}