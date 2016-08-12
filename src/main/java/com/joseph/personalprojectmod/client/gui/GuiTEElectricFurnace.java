package com.joseph.personalprojectmod.client.gui;

import com.joseph.personalprojectmod.guicontainer.ContainerTEElectricFurnace;
import com.joseph.personalprojectmod.tileentity.TileEntityElectricFurnace;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTEElectricFurnace extends GuiContainer {
	private IInventory playerInv;
	private TileEntityElectricFurnace te;
	
	public GuiTEElectricFurnace(IInventory playerInv, TileEntityElectricFurnace te) {
		super(new ContainerTEElectricFurnace(playerInv, te));
		
		this.playerInv = playerInv;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	this.mc.getTextureManager().bindTexture(new ResourceLocation("personalprojectmod:textures/gui/ele_furnace.png"));
    	this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    	
    	// Top of Arrow
    	this.drawTexturedModalRect(80 + this.guiLeft, 30 + this.guiTop, 176, 0, this.getTopProgress(), 9);
    	
    	// Bottom Arrow
    	this.drawTexturedModalRect(80 + this.guiLeft, 30 + this.guiTop + 9, 176, 9, this.getBottomProgress(), 10);
    	
    	// Power Meter
    	this.drawTexturedModalRect(this.guiLeft + 67, this.guiTop + 61, 0, 180, this.getScalledPower(), 8);
    	this.drawTexturedModalRect(this.guiLeft + 64, this.guiTop + 58, 0, 166, 54, 14);
	}
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	String s = this.te.getDisplayName().getFormattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);
    }
    
    private int getTopProgress() {
    	int time = te.getField(0);
    	int total = te.getField(1);
    	return time != 0 && total != 0 ? time * 24 / total : 0;
    }
    
    private int getBottomProgress() {
    	int time = te.getField(2);
    	int total = te.getField(3);
    	return time != 0 && total != 0 ? time * 24 / total : 0;
    }
    
    private int getScalledPower() {
    	int stored = this.te.getField(4);
    	int max = this.te.getField(5);
    	return stored != 0 && max != 0 ? stored * 48 / max : 0;
    }
}