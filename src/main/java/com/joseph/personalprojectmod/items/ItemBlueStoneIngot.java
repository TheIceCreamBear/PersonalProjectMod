package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.reference.EnumItems;

import net.minecraft.item.Item;

public class ItemBlueStoneIngot extends Item {
	public ItemBlueStoneIngot() {
		this.setUnlocalizedName(EnumItems.BLUE_STONE_INGOT.getUnlocalizedName());
		this.setRegistryName(EnumItems.BLUE_STONE_INGOT.getRegistryName());
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
}