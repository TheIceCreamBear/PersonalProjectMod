package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.refrence.EnumItems;

import net.minecraft.item.Item;

public class ItemBlueStoneDust extends Item {
	public ItemBlueStoneDust() {
		this.setUnlocalizedName(EnumItems.BLUE_STONE_DUST.getUnlocalizedName());
		this.setRegistryName(EnumItems.BLUE_STONE_DUST.getRegistryName());
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
}