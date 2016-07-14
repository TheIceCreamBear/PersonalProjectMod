package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemHoe;

public class ModHoe extends ItemHoe {
	public ModHoe(String unlocalizedName, ToolMaterial mat) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
}