package com.joseph.personalprojectmod.blocks;

import com.joseph.personalprojectmod.PersonalProject;
import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.refrence.GuiIDRef;
import com.joseph.personalprojectmod.tileentity.TileEntityOreCrusher;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockTEOreCrusher extends BlockContainer {
	public BlockTEOreCrusher(String unlocalizedName) {
		super(Material.iron);
		this.setUnlocalizedName(unlocalizedName);
		this.setHardness(2.0f);
		this.setResistance(6.0f);
		this.setHarvestLevel("pickaxe", 2);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int mate) {
		return new TileEntityOreCrusher();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		TileEntityOreCrusher te = (TileEntityOreCrusher)world.getTileEntity(pos);
		InventoryHelper.dropInventoryItems(world, pos, te);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if (stack.hasDisplayName()) {
			((TileEntityOreCrusher)world.getTileEntity(pos)).setCustomName(stack.getDisplayName());
		}
	}
	
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
	    if (!world.isRemote) {
	        player.openGui(PersonalProject.instance, GuiIDRef.ORE_CRUSHER_GUI, world, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
}