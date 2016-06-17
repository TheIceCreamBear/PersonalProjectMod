package com.joseph.personalprojectmod.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class BlueStoneOre extends ModBlockOre {
	public BlueStoneOre(String unlocalizedName) {
		super(unlocalizedName, Material.rock, null, 0, 1, 1);
		super.drop = Item.getItemFromBlock(this);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		return Item.getItemFromBlock(this);
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random rand) {
		return 1;
	}
	
	
}