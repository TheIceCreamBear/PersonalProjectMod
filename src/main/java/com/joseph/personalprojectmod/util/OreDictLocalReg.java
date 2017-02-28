package com.joseph.personalprojectmod.util;

import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.init.ModItems;

import net.minecraftforge.oredict.OreDictionary;

public class OreDictLocalReg {
	public static void registerAllOreDict() {
		OreDictionary.registerOre("oreBlueStone", ModBlocks.bluStnOre);
		OreDictionary.registerOre("dustBlueStone", ModItems.blueStoneDust);
		OreDictionary.registerOre("ingotBlueStone", ModItems.blueStoneIngot);
	}
}