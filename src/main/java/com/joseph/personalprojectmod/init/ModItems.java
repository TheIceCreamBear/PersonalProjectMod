package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.items.BasicItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
	public static Item blueStoneIngot = new BasicItem("blue_stone_ingot");
	
	// SELF NOTE: WHEN EVER A NEW ITEM IS ADDED, IT NEEDS TO BE REISTERED IN ItemRenderRegister
	public static void createItems() {
		GameRegistry.registerItem(blueStoneIngot, "blue_stone_ingot");
	}
}