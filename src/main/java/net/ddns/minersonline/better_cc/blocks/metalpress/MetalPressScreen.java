package net.ddns.minersonline.better_cc.blocks.metalpress;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.ddns.minersonline.better_cc.better_cc;

public class MetalPressScreen extends ContainerScreen<MetalPressContainer> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/metal_press.png");
    public static final ResourceLocation UTILS_TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/utils.png");

    public MetalPressScreen(MetalPressContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, x, y, partialTicks);
        this.renderTooltip(matrixStack, x, y);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) {
            return;
        }

        RenderSystem.color4f(1, 1, 1, 1);
        minecraft.getTextureManager().bind(TEXTURE);

        int posX = (this.width - this.imageWidth) / 2;
        int posY = (this.height - this.imageHeight) / 2;

        blit(matrixStack, posX, posY, 0, 0, this.imageWidth, this.imageHeight);

        // Progress arrow
        blit(matrixStack, posX + 79, posY + 35, 176, 14, menu.getProgressArrowScale() + 1, 16);

        // Power Bar
        minecraft.getTextureManager().bind(UTILS_TEXTURE);
        blit(matrixStack, posX + 16*9+8, posY + 16, 12, 31, 15, 55);
    }
}