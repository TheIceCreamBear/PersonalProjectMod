package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemHoe;
import net.minecraft.item.Item.ToolMaterial;

public class ModHoe extends ItemHoe {
	public ModHoe(ToolMaterial mat, String unlocalizedName, String registryName) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CreativeTabsPPM.PPM_TOOLS_TAB);
	}
}