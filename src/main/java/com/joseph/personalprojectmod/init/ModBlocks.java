package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.blocks.BlockTEElectricFurnace;
import com.joseph.personalprojectmod.blocks.BlockTEElectricGenerator;
import com.joseph.personalprojectmod.blocks.BlockTEOreCrusher;
import com.joseph.personalprojectmod.blocks.BlockTEPowerBox;
import com.joseph.personalprojectmod.blocks.BlueStoneOre;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {
	public static Block bluStnOre;
	public static Block teOreCrusher;
	public static Block teEleFurnace;
	public static Block teEleGenerator;
	public static Block tePowerBox;
	
	public static void init() {
		bluStnOre = new BlueStoneOre();
		teOreCrusher = new BlockTEOreCrusher();
		teEleFurnace = new BlockTEElectricFurnace();
		teEleGenerator = new BlockTEElectricGenerator();
		tePowerBox = new BlockTEPowerBox();
	}
	
	public static void register() {
		// Blocks
		regBlock(bluStnOre);
		
		// TileEntities
		regBlock(teEleFurnace);
		regBlock(teEleGenerator);
		regBlock(teOreCrusher);
		regBlock(tePowerBox);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerVariants() {
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teEleFurnace), new ModelResourceLocation(ModBlocks.teEleFurnace.getRegistryName(), "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teOreCrusher), new ModelResourceLocation(ModBlocks.teEleGenerator.getRegistryName(), "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.teEleGenerator), new ModelResourceLocation(ModBlocks.teOreCrusher.getRegistryName(), "inventory"));
		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.tePowerBox), new ModelResourceLocation(ModBlocks.tePowerBox.getRegistryName(), "inventory"));
	}
	
	private static void regBlock(Block block) {
		GameRegistry.register(block);
		ItemBlock itmBlk = new ItemBlock(block);
		itmBlk.setRegistryName(block.getRegistryName());
		GameRegistry.register(itmBlk);
	}
	
	public static void registerRenders() {
		// Blocks
		regRenderBlock(bluStnOre);
		
		// TileEntities
		regRenderBlock(teEleFurnace);
		regRenderBlock(teEleGenerator);
		regRenderBlock(teOreCrusher);
		regRenderBlock(tePowerBox);
	}
	
	private static void regRenderBlock(Block block) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
}