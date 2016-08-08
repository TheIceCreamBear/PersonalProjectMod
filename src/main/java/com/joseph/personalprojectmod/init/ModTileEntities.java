package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.tileentity.TileEntityElectricFurnace;
import com.joseph.personalprojectmod.tileentity.TileEntityElectricGenerator;
import com.joseph.personalprojectmod.tileentity.TileEntityOreCrusher;
import com.joseph.personalprojectmod.tileentity.TileEntityPowerBox;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	public static void createTileEntities() {
		GameRegistry.registerTileEntity(TileEntityOreCrusher.class, "ore_crusher");
		GameRegistry.registerTileEntity(TileEntityElectricFurnace.class, "ppm_ele_furnace");
		GameRegistry.registerTileEntity(TileEntityElectricGenerator.class, "ppm_ele_generator");
		GameRegistry.registerTileEntity(TileEntityPowerBox.class, "ppm_power_box");
	}
}