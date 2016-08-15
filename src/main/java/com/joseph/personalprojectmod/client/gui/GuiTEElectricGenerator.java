package com.joseph.personalprojectmod.client.gui;

import com.joseph.personalprojectmod.guicontainer.ContainerTEElectricGenerator;
import com.joseph.personalprojectmod.tileentity.TileEntityElectricGenerator;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTEElectricGenerator extends GuiContainer {
	private IInventory playerInv;
	private TileEntityElectricGenerator te;
	
	public GuiTEElectricGenerator(IInventory playerInv, TileEntityElectricGenerator te) {
		super(new ContainerTEElectricGenerator(playerInv, te));
		
		this.playerInv = playerInv;
		this.te = te;
		
		this.xSize = 176;
		this.ySize = 166;
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float particalTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		this.mc.getTextureManager().bindTexture(new ResourceLocation("personalprojectmod:textures/gui/ele_generator.png"));
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
		
		this.drawTexturedModalRect(this.guiLeft + 98, this.guiTop + 39, 0, 180, this.getScalledPower(), 8);
		this.drawTexturedModalRect(this.guiLeft + 95, this.guiTop + 36, 0, 166, 54, 14);
		
		if (this.te.getField(0) > 0) {
			int i = this.getFuelLeft();
			this.drawTexturedModalRect(this.guiLeft + 25, this.guiTop + 14 + 12 - i, 176, 12 - i, 14, i + 1);
		}
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseC, int mouseY) {
		String s = this.te.getDisplayName().getFormattedText();
		this.fontRendererObj.drawString(s, this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);
	}
	
	private int getScalledPower() {
    	int stored = this.te.getField(2);
    	int max = this.te.getField(3);
    	return stored != 0 && max != 0 ? stored * 48 / max : 0;
    }
	
	private int getFuelLeft() {
		int time = this.te.getField(1);
		if (time == 0) {
			time = 200;
		}
		return this.te.getField(0) * 13 / time;
	}
}