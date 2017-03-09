package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.item.ItemSpade;
import net.minecraft.item.Item.ToolMaterial;

public class ModSpade extends ItemSpade {
	public ModSpade(ToolMaterial mat, String unlocalizedName, String registryName, float reducedEfficency) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CreativeTabsPPM.PPM_TOOLS_TAB);
		this.efficiencyOnProperMaterial = reducedEfficency; // its too fast
	}
}