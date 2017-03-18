package com.joseph.personalprojectmod.client.render;

import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.refrence.Refrence;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRenderRegister {
	@SideOnly(Side.CLIENT)
	public static void registerBlockRender() {
		// ORES
		reg(ModBlocks.blueStoneOre);
		
		// GENERIC
		reg(ModBlocks.explodo);
		
		// TILE ENTITES
		reg(ModBlocks.teOreCrusher);
		reg(ModBlocks.teEleFurnace);
		reg(ModBlocks.teEleGenerator);
		reg(ModBlocks.tePowerBox);
		
	}
	
	public static void reg(Block blk) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(blk), 0, new ModelResourceLocation(Refrence.LOWER_CASE_MOD_ID + ":" + blk.getUnlocalizedName().substring(5), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void preInit() {
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teEleFurnace), new ModelResourceLocation("personalprojectmod:te_ele_furnace", "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teOreCrusher), new ModelResourceLocation("personalprojectmod:te_ore_crusher", "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teEleGenerator), new ModelResourceLocation("personalprojectmod:te_ele_generator", "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.tePowerBox), new ModelResourceLocation("personalprojectmod:te_power_box", "inventory"));
	}
}