package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ModHoe extends ItemHoe {
	public ModHoe(String unlocalizedName, ToolMaterial mat) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
		final BlockPos leftUpMostPos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
		final BlockPos origPos = new BlockPos(pos);
		
		if (!player.canPlayerEdit(pos.offset(side), side, stack)) {
			return false;
		} else {
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
			if (hook != 0) {
				return hook > 0;
			}

			for (int x = 0; x < 3; x++) {
				for (int z = 0; z < 3; z++) {
					pos = new BlockPos(leftUpMostPos.getX() + x, leftUpMostPos.getY(), leftUpMostPos.getZ() + z);

					IBlockState iblockstate = world.getBlockState(pos);
					Block block = iblockstate.getBlock();

					if (side != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
						if (block == Blocks.grass) {
							this.useHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
						}

						if (block == Blocks.dirt) {
							switch (SwitchDirtType.TYPE_LOOKUP[((BlockDirt.DirtType) iblockstate
									.getValue(BlockDirt.VARIANT)).ordinal()]) {
							case 1:
								this.useHoe(stack, player, world, pos, Blocks.farmland.getDefaultState());
							case 2:
								this.useHoe(stack, player, world, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
							}
						}
					}
				}
			}
			return this.useHoe(stack, player, world, origPos, Blocks.farmland.getDefaultState());
		}
	}

	protected boolean useHoe(ItemStack stack, EntityPlayer player, World worldIn, BlockPos target, IBlockState newState) {
		worldIn.playSoundEffect(target.getX() + 0.5F, target.getY() + 0.5F, target.getZ() + 0.5F, newState.getBlock().stepSound.getStepSound(), (newState.getBlock().stepSound.getVolume() + 1.0F) / 2.0F, newState.getBlock().stepSound.getFrequency() * 0.8F);

		if (worldIn.isRemote) {
			return true;
		} else {
			worldIn.setBlockState(target, newState);
			stack.damageItem(1, player);
			return true;
		}
	}

	static final class SwitchDirtType {

		static final int[] TYPE_LOOKUP = new int[BlockDirt.DirtType.values().length];

		static {
			try {
				TYPE_LOOKUP[BlockDirt.DirtType.DIRT.ordinal()] = 1;
			} catch (NoSuchFieldError var2) {
				;
			}

			try {
				TYPE_LOOKUP[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
			} catch (NoSuchFieldError var1) {
				;
			}
		}
	}
}