package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.blocks.BlockTEElectricFurnace;
import com.joseph.personalprojectmod.blocks.BlockTEOreCrusher;
import com.joseph.personalprojectmod.blocks.BlueStoneOre;
import com.joseph.personalprojectmod.blocks.ItemBlockMeta;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block blueStoneOre = new BlueStoneOre("blue_stone_ore");
	public static Block teOreCrusher = new BlockTEOreCrusher("te_ore_crusher");
	public static Block teEleFurnace = new BlockTEElectricFurnace("te_ele_furnace");
	
	// SELF NOTE: WHEN EVER A NEW BLOCK IS ADDED, IT NEEDS TO BE REISTERED IN BlockRenderRegister
	public static void createBlocks() {
		GameRegistry.registerBlock(blueStoneOre, "blue_stone_ore");
		GameRegistry.registerBlock(teOreCrusher, "te_ore_crusher");
		GameRegistry.registerBlock(teEleFurnace, "te_ele_furnace");
	}
}