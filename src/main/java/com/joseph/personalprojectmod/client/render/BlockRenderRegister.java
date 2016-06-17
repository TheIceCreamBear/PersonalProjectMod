package com.joseph.personalprojectmod.client.render;

import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.refrence.Refrence;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRenderRegister {
	@SideOnly(Side.CLIENT)
	public static void registerBlockRender() {
		reg(ModBlocks.blueStoneOre);
	}
	
	public static void reg(Block blk) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(blk), 0, new ModelResourceLocation(Refrence.LOWER_CASE_MOD_ID + ":" + blk.getUnlocalizedName().substring(5), "inventory"));
	}
}