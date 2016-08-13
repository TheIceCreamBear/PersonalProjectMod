package com.joseph.personalprojectmod.client.gui;

import com.joseph.personalprojectmod.guicontainer.ContainerTEPowerBox;
import com.joseph.personalprojectmod.tileentity.TileEntityPowerBox;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTEPowerBox extends GuiContainer {
	private IInventory playerInv;
	private TileEntityPowerBox te;
	
	public GuiTEPowerBox(IInventory playerInv, TileEntityPowerBox te) {
		super(new ContainerTEPowerBox(playerInv, te));
		
		this.playerInv = playerInv;
        this.te = te;

        this.xSize = 176;
        this.ySize = 166;
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	this.mc.getTextureManager().bindTexture(new ResourceLocation("personalprojectmod:textures/gui/power_box.png"));
    	this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    	
    	this.drawTexturedModalRect(this.guiLeft + 93, this.guiTop + 38, 176, 14, this.getScalledPower(), 8);
    	
    	this.drawTexturedModalRect(this.guiLeft + 90, this.guiTop + 35, 176, 0, 54, 14);
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	String s = this.te.getDisplayName().getFormattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);
    }
    
    private int getScalledPower() {
    	int stored = this.te.getField(0);
    	int max = this.te.getField(1);
    	return stored != 0 && max != 0 ? stored * 48 / max : 0;
    }
}