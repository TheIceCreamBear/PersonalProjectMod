package com.joseph.personalprojectmod.client.render;

import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.refrence.Refrence;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRenderRegister {
	@SideOnly(Side.CLIENT)
	public static void preInit() {
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teEleFurnace), new ModelResourceLocation(ModBlocks.teEleFurnace.getRegistryName(), "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teOreCrusher), new ModelResourceLocation(ModBlocks.teEleGenerator.getRegistryName(), "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teEleGenerator), new ModelResourceLocation(ModBlocks.teOreCrusher.getRegistryName(), "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.tePowerBox), new ModelResourceLocation(ModBlocks.tePowerBox.getRegistryName(), "inventory"));
	}
}