package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.blocks.BlueStoneOre;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block blueStoneOre = new BlueStoneOre("blue_stone_ore");
	
	// SELF NOTE: WHEN EVER A NEW BLOCK IS ADDED, IT NEEDS TO BE REISTERED IN BlockRenderRegister
	public static void createBlocks() {
		GameRegistry.registerBlock(blueStoneOre, "blue_stone_ore");
	}
}