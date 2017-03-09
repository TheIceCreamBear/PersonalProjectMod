package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModHoe extends ItemHoe {
	public ModHoe(ToolMaterial mat, String unlocalizedName, String registryName) {
		super(mat);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CreativeTabsPPM.PPM_TOOLS_TAB);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		boolean successHook = false;
		
		ItemStack stack = player.getHeldItem(hand);
		
		final BlockPos leftUpMostPos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ() - 1);
		final BlockPos origPos = new BlockPos(pos);
		
		if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
			return EnumActionResult.FAIL;
		} else if (player.isSneaking()) {
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
			if (hook != 0) {
				return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
			}
			
			IBlockState iblockstate = world.getBlockState(pos);
			Block block = iblockstate.getBlock();
			
			if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
				if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
					this.setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
					return EnumActionResult.SUCCESS;
				}
				
				if (block == Blocks.DIRT) {
					switch ((BlockDirt.DirtType) iblockstate.getValue(BlockDirt.VARIANT)) {
						case DIRT:
							this.setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
							return EnumActionResult.SUCCESS;
						case COARSE_DIRT:
							this.setBlock(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
							return EnumActionResult.SUCCESS;
						case PODZOL:
							break;
						default:
							break;
					}
				}
			}
			return EnumActionResult.PASS;
		} else {
			int hook = net.minecraftforge.event.ForgeEventFactory.onHoeUse(stack, player, world, pos);
			if (hook != 0) {
				return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
			}

			for (int x = 0; x < 3; x++) {
				for (int z = 0; z < 3; z++) {
					pos = new BlockPos(leftUpMostPos.getX() + x, leftUpMostPos.getY(), leftUpMostPos.getZ() + z);

					IBlockState iblockstate = world.getBlockState(pos);
					Block block = iblockstate.getBlock();

					if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
						if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
							this.setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
							successHook = true;
						}

						if (block == Blocks.DIRT) {
							switch ((BlockDirt.DirtType)iblockstate.getValue(BlockDirt.VARIANT)) {
								case DIRT:
									this.setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
									successHook = true;
									break;
								case COARSE_DIRT:
									this.setBlock(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
									successHook = true;
									break;
								case PODZOL:
									break;
								default:
									break;
							}
						}
					}
				}
			}
			if (successHook) {				
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.PASS;
		}
	}

	protected void setBlock(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, IBlockState newState) {
		worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);

		if (!worldIn.isRemote) {
			worldIn.setBlockState(pos, newState);
			stack.damageItem(1, player);
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