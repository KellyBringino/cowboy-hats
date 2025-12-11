package net.dontouchat.cowboyhats.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.dontouchat.cowboyhats.CowboyHatsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;

public class InfusionScreen extends AbstractContainerScreen<InfusionMenu> {
    private final int buttonX = 20;
    private final int buttonY = 18;

    private final int buttonPosX = 0;
    private final int buttonPosY = 0;

    @SuppressWarnings("removal")
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(CowboyHatsMod.MODID, "textures/gui/infusiontable_gui.png");

    public InfusionScreen(InfusionMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        int l = this.leftPos + 0;
        int i1 = this.topPos + 0;
        this.renderButtons(guiGraphics, pMouseX, pMouseY, l, i1);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, delta);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int i = this.leftPos + buttonPosX;
        int j = this.topPos + buttonPosY;

        double d0 = pMouseX - (double)(i + buttonX);
        double d1 = pMouseY - (double)(j + buttonY);
        if (
                d0 >= 0.0D
                        && d1 >= 0.0D
                        && d0 < buttonX
                        && d1 < buttonY
                        && this.menu.clickMenuButton(this.minecraft.player,0)
        )
        {
            System.out.println("button push");
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            this.minecraft.gameMode.handleInventoryButtonClick((this.menu).containerId, 0);
            return true;
        }

        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    private void renderButtons(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pX, int pY) {
        int k = pX + buttonX;
        int i1 = pY + buttonY;
        int j1 = this.imageHeight + 2;
        if (pMouseX >= k && pMouseY >= i1 && pMouseX < k + buttonX && pMouseY < i1 + buttonY) {
            j1 += buttonY + 1;
        }

        pGuiGraphics.blit(TEXTURE, k, i1, 0, j1, buttonX, buttonY);
    }
}