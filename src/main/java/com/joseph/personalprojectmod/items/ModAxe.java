package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemAxe;

public class ModAxe extends ItemAxe {
	public ModAxe(ToolMaterial mat, String unlocalizedName, String registryName) {
		super(mat, 8.0f, -3.0f);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CreativeTabsPPM.PPM_TOOLS_TAB);
	}
}