package com.joseph.personalprojectmod.handlers;

import com.joseph.personalprojectmod.client.gui.GuiTEElectricFurnace;
import com.joseph.personalprojectmod.client.gui.GuiTEOreCrusher;
import com.joseph.personalprojectmod.guicontainer.ContainerTEElectricFurnace;
import com.joseph.personalprojectmod.guicontainer.ContainerTEOreCrusher;
import com.joseph.personalprojectmod.refrence.GuiIDRef;
import com.joseph.personalprojectmod.tileentity.TileEntityElectricFurnace;
import com.joseph.personalprojectmod.tileentity.TileEntityOreCrusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GuiIDRef.ORE_CRUSHER_GUI) {
			return new ContainerTEOreCrusher(player.inventory, (TileEntityOreCrusher)world.getTileEntity(new BlockPos(x, y, z)));
		} else if (id == GuiIDRef.ELECTRIC_FURNAE_GUI) {
			return new ContainerTEElectricFurnace(player.inventory, (TileEntityElectricFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if (id == GuiIDRef.ORE_CRUSHER_GUI) {
			return new GuiTEOreCrusher(player.inventory, (TileEntityOreCrusher)world.getTileEntity(new BlockPos(x, y, z)));
		} else if (id == GuiIDRef.ELECTRIC_FURNAE_GUI) {
			return new GuiTEElectricFurnace(player.inventory, (TileEntityElectricFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}
}