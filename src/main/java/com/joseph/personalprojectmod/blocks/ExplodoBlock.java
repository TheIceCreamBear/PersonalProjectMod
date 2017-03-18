package com.joseph.personalprojectmod.blocks;

import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.items.ItemIDCard;
import com.joseph.personalprojectmod.util.LogHelper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ExplodoBlock extends BasicBlock {

	public ExplodoBlock(String unlocalizedName) {
		super(unlocalizedName);
		
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
		if (player.inventory.getCurrentItem() != null) {
			if (player.inventory.getCurrentItem().getItem() instanceof ItemIDCard) {
				ItemIDCard idCard = (ItemIDCard) player.inventory.getCurrentItem().getItem();
				if (!idCard.playerID.equals("kartingman25")) {
					world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 20f, false, true);
				} else {
					
				}
				LogHelper.info(idCard == ModItems.idCardRef);
				ItemIDCard tmp = (ItemIDCard) ModItems.idCardRef;
				LogHelper.info(idCard.playerID);
				LogHelper.info(tmp.playerID);
				return false;
			}			
		}
		// Make explode on someting
		world.newExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1000f, false, true);
		return false;
	}
}