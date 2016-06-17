package com.joseph.personalprojectmod.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void initCrafting() {
		GameRegistry.addSmelting(ModBlocks.blueStoneOre, new ItemStack(ModItems.blueStoneIngot), 1.5f);
	}
}