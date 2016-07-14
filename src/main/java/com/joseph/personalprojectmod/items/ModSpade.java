package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemSpade;

public class ModSpade extends ItemSpade {
	public ModSpade(String unlocalizedName, ToolMaterial mat) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
}