package com.joseph.personalprojectmod.items;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Sets;
import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModSickle extends ItemTool {
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet(new Block[] {Blocks.BROWN_MUSHROOM, Blocks.CACTUS, Blocks.CHORUS_FLOWER, Blocks.CHORUS_PLANT, Blocks.DEADBUSH, Blocks.DOUBLE_PLANT, Blocks.LEAVES, Blocks.LEAVES2, Blocks.RED_FLOWER, Blocks.RED_MUSHROOM, Blocks.REEDS, Blocks.TALLGRASS, Blocks.VINE, Blocks.YELLOW_FLOWER});
	
	public ModSickle(ToolMaterial materialIn, String unlocalizedName, String registryName) {
		super(1.5f, 0.0f, materialIn, EFFECTIVE_ON);
		this.setCreativeTab(CreativeTabsPPM.PPM_TOOLS_TAB);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
	}
	
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
		final BlockPos leftDownFarMostPos = new BlockPos(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2);
		BlockPos tmpPos = leftDownFarMostPos;
		
		if (!worldIn.isRemote) {
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					for (int z = 0; z < 5; z++) {
						tmpPos = new BlockPos(leftDownFarMostPos.getX() + x, leftDownFarMostPos.getY() + y, leftDownFarMostPos.getZ() + z);
						if (removeBlock(worldIn, tmpPos)) {
							stack.damageItem(1, entityLiving);							
						}
					}
				}
			}
		}
		
		return true;
	}
	
	private boolean removeBlock(World world, BlockPos pos) {
		IBlockState airState = Blocks.AIR.getDefaultState();
		IBlockState blockstate = world.getBlockState(pos);
		Block block = blockstate.getBlock();
		if (EFFECTIVE_ON.contains(block)) {
//			block.breakBlock(world, pos, blockstate);
//			List<ItemStack> drops = block.getDrops(world, pos, blockstate, 2);
			world.destroyBlock(pos, true);
//			world.playSound((double) pos.getX(), (double) pos.getY(), (double) pos.getZ(), SoundEvents.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f, true);
//			world.setBlockState(pos, airState, 11);
			return true;
		}
		return false;
	}
}