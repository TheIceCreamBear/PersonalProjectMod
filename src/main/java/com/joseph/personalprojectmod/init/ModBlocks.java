package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.blocks.BlockTEElectricFurnace;
import com.joseph.personalprojectmod.blocks.BlockTEElectricGenerator;
import com.joseph.personalprojectmod.blocks.BlockTEOreCrusher;
import com.joseph.personalprojectmod.blocks.BlockTEPowerBox;
import com.joseph.personalprojectmod.blocks.BlueStoneOre;
import com.joseph.personalprojectmod.blocks.ExplodoBlock;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	// ORE
	public static Block blueStoneOre = new BlueStoneOre("blue_stone_ore");
	
	// GENERIC
	public static Block explodo = new ExplodoBlock("explodo_block");
	
	// TILE ENTITIES
	public static Block teOreCrusher = new BlockTEOreCrusher("te_ore_crusher");
	public static Block teEleFurnace = new BlockTEElectricFurnace("te_ele_furnace");
	public static Block teEleGenerator = new BlockTEElectricGenerator("te_ele_generator");
	public static Block tePowerBox = new BlockTEPowerBox("te_power_box");
	
	// SELF NOTE: WHEN EVER A NEW BLOCK IS ADDED, IT NEEDS TO BE REISTERED IN BlockRenderRegister
	public static void createBlocks() {
		GameRegistry.registerBlock(blueStoneOre, "blue_stone_ore");
		GameRegistry.registerBlock(teOreCrusher, "te_ore_crusher");
		GameRegistry.registerBlock(teEleFurnace, "te_ele_furnace");
		GameRegistry.registerBlock(teEleGenerator, "te_ele_generator");
		GameRegistry.registerBlock(tePowerBox, "te_power_box");
		GameRegistry.registerBlock(explodo, "explodo_block");
	}
}