package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemPickaxe;

public class ModPickaxe extends ItemPickaxe {
	public ModPickaxe(String unlocalizedName, ToolMaterial mat) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
}