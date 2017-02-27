package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.blocks.BlockTEElectricFurnace;
import com.joseph.personalprojectmod.blocks.BlockTEElectricGenerator;
import com.joseph.personalprojectmod.blocks.BlockTEOreCrusher;
import com.joseph.personalprojectmod.blocks.BlockTEPowerBox;
import com.joseph.personalprojectmod.blocks.BlueStoneOre;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block blueStoneOre;
	public static Block teOreCrusher;
	public static Block teEleFurnace;
	public static Block teEleGenerator;
	public static Block tePowerBox;
	
	public static void init() {
		blueStoneOre = new BlueStoneOre();
		teOreCrusher = new BlockTEOreCrusher();
		teEleFurnace = new BlockTEElectricFurnace();
		teEleGenerator = new BlockTEElectricGenerator();
		tePowerBox = new BlockTEPowerBox();
	}
	
	public static void register() {
		// Blocks
		regBlock(blueStoneOre);
		
		// TileEntities
		regBlock(teEleFurnace);
		regBlock(teEleGenerator);
		regBlock(teOreCrusher);
		regBlock(tePowerBox);
	}
	
	private static void regBlock(Block block) {
		GameRegistry.register(block);
		ItemBlock itmBlk = new ItemBlock(block);
		itmBlk.setRegistryName(block.getRegistryName());
		GameRegistry.register(itmBlk);
	}
	
	public static void registerRenders() {
		// Blocks
		regRenderBlock(blueStoneOre);
		
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