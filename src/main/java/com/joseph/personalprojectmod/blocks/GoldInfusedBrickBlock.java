package com.joseph.personalprojectmod.blocks;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.reference.EnumBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class GoldInfusedBrickBlock extends Block {
	public GoldInfusedBrickBlock() {
		super(Material.ROCK);
		this.setUnlocalizedName(EnumBlocks.GOLD_BRICK.getUnlocalizedName());
		this.setRegistryName(EnumBlocks.GOLD_BRICK.getRegistryName());
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
		this.setHardness(2.0f);
		this.setResistance(10.0f);
		this.setSoundType(SoundType.STONE);
	}
}