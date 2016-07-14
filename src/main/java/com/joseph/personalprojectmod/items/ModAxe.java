package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemAxe;

public class ModAxe extends ItemAxe {
	public ModAxe(String unlocalizedName, ToolMaterial mat) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
}