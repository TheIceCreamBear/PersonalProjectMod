package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.tileentity.TileEntityOreCrusher;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {
	public static void createTileEntities() {
		GameRegistry.registerTileEntity(TileEntityOreCrusher.class, "ore_crusher");
	}
}