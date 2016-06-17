package com.joseph.personalprojectmod.blocks;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BasicBlock extends Block {
	public BasicBlock(String unlocalizedName, Material material, float hardness, float resistance) {
		super(material);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
		this.setHardness(hardness);
		this.setResistance(resistance);
	}
	
	public BasicBlock(String unlocalizedName, float hardness, float resistance) {
		this(unlocalizedName, Material.rock, hardness, resistance);
	}
	
	public BasicBlock(String unlocalizedName, Material mat) {
		this(unlocalizedName, mat, 2.0f, 10.0f);
	}	
	
	public BasicBlock(String unlocalizedName) {
		this(unlocalizedName, Material.rock);
	}
}