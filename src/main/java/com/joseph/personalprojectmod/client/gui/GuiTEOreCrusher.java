package com.joseph.personalprojectmod.client.gui;

import com.joseph.personalprojectmod.guicontainer.ContainerTEOreCrusher;
import com.joseph.personalprojectmod.tileentity.TileEntityOreCrusher;
import com.joseph.personalprojectmod.util.LogHelper;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiTEOreCrusher extends GuiContainer {
	private IInventory playerInv;
	private TileEntityOreCrusher te;
	
	public GuiTEOreCrusher(IInventory playerInv, TileEntityOreCrusher te) {
        super(new ContainerTEOreCrusher(playerInv, te));
        
        this.playerInv = playerInv;
        this.te = te;

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    	this.mc.getTextureManager().bindTexture(new ResourceLocation("personalprojectmod:textures/gui/ore_crusher.png"));
    	this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    	
    	// Arrow
    	this.drawTexturedModalRect(this.guiLeft + 80, this.guiTop + 30, 176, 0, this.getProgress(), 17);
    	
    	// Power Meter
    	this.drawTexturedModalRect(this.guiLeft + 17, this.guiTop + 15 + 47 - this.getScalledPower(), 214, 47 - this.getScalledPower(), 8, this.getScalledPower() + 1);
    	this.drawTexturedModalRect(this.guiLeft + 14, this.guiTop + 12, 200, 0, 14, 54);
    	
//    	LogHelper.info(this.getScalledPower());
//    	this.fontRendererObj.drawString(this.te.getField(2) + " / " + this.te.getField(3), this.guiLeft + 80, this.guiTop + 60, 4210752);
    	
    }
    
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    	String s = this.te.getDisplayName().getFormattedText();
		this.fontRendererObj.drawString(s, 88 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		this.fontRendererObj.drawString(this.playerInv.getDisplayName().getUnformattedText(), 8, 72, 4210752);
    }
    
    private int getProgress() {
    	int time = te.getField(0);
    	return time != 0 ? time * 24 / 150 : 0;
    }
    
    private int getScalledPower() {
    	int stored = this.te.getField(2);
    	int max = this.te.getField(3);
    	return stored != 0 && max != 0 ? stored * 48 / max : 0;
    }
}