package com.joseph.personalprojectmod.blocks;

import java.util.Random;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.init.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ModBlockOre extends Block {
	protected Item drop;
	private int meta;
	private int least;
	private int most;
	
	public ModBlockOre(String unlocalizedName, String registryName, Material mat, Item drop, int meta, int least, int most) {
		super(mat);
		this.drop = drop;
		this.meta = meta;
		this.least = least;
		this.most = most;
		this.setHarvestLevel("pickaxe", 1);
		this.setHardness(6.0f);
		this.setResistance(10.0f);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
		this.setSoundType(SoundType.STONE);
	}
	
	public ModBlockOre(String unlocalizedName, String registryName, Material mat, Item drop, int least, int most) {
		this(unlocalizedName, registryName, mat, drop, 0, least, most);
	}
	
	public ModBlockOre(String unlocalizedName, String registryName, Material mat, Item drop) {
		this(unlocalizedName, registryName, mat, drop, 1, 1);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune) {
		return this.drop;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return this.meta;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		if (this.least >= this.most) {
			return this.least;
		}
		return this.least + random.nextInt(this.most - this.least + fortune + 1);
	}
	
	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune) {
        Random rand = world instanceof World ? ((World)world).rand : new Random();
        if (this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)) {
            int i = 0;

            if (this == ModBlocks.bluStnOre) {
                i = MathHelper.getInt(rand, 2, 5);
            }

            return i;
        }
        return 0;
    }
}