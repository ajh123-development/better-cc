package net.ddns.minersonline.better_cc;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class UpgradeableMachineGUIBase<T extends Container> extends ContainerScreen<T> {
    public void setTEXTURE(ResourceLocation TEXTURE) {
        this.TEXTURE = TEXTURE;
    }

    private ResourceLocation TEXTURE;
    private static final ResourceLocation UTILS_TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/utils.png");
    private static final ResourceLocation UPGRADE_TEXTURE = new ResourceLocation(better_cc.MOD_ID, "textures/gui/upgrade_machine.png");
    int xSize, ySize, titleYOffset;
    Container container;

    private boolean showInventory = true;

    public UpgradeableMachineGUIBase(T container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        this.xSize = 248;
        this.ySize = 177;
        this.titleYOffset = 0;
        this.container = container;
    }

    @Override
    public void render(MatrixStack matrixStack, int x, int y, float partialTicks) {
        this.renderBackground(matrixStack);

        super.render(matrixStack, x, y, partialTicks);
        this.renderTooltip(matrixStack, x, y);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int x, int y) {
        this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY-8, 4210752);
        if (this.showInventory){
            this.font.draw(matrixStack, this.inventory.getDisplayName(), (float)this.inventoryLabelX, (float)this.inventoryLabelY, 4210752);
        }
    }

    public void showInventory(boolean showInventory) {
        this.showInventory = showInventory;
        if (showInventory) {
            //this.container.
        }
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        if (minecraft == null) {
            return;
        }

        RenderSystem.color4f(1, 1, 1, 1);
        if (showInventory){
            minecraft.getTextureManager().bind(UPGRADE_TEXTURE);
        } else {
            minecraft.getTextureManager().bind(TEXTURE);
        }

        int posX = (this.width - this.xSize) /  2;
        int posY = (this.height - this.ySize) / 2;

        blit(matrixStack, posX, posY, 0, 0, this.xSize, this.ySize);

        // Power Bar
        minecraft.getTextureManager().bind(UTILS_TEXTURE);
        blit(matrixStack, posX-12, posY+6 , 12, 31, 15, 54);
        blit(matrixStack, posX-17, posY , 28, 31, 21, 65);

        // Tabs
        blit(matrixStack, posX-20, posY+65 , 32, 0, 24, 28);
        blit(matrixStack, posX-17, posY+65+28 , 56, 0, 21, 28);
    }

}
