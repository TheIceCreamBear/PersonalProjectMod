package com.joseph.personalprojectmod.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void initCrafting() {
		GameRegistry.addSmelting(ModBlocks.bluStnOre, new ItemStack(ModItems.blueStoneIngot), 1.5f);
		GameRegistry.addSmelting(ModItems.blueStoneDust, new ItemStack(ModItems.blueStoneIngot), 1.5f);		
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneAxe), new Object[] { "## ", "#I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneAxe), new Object[] { " ##", " I#", " I ", '#', ModItems.blueStoneIngot, 'I', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneHoe), new Object[] { "## ", " I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStonePickaxe), new Object[] { "###", " I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneSpade), new Object[] { " # ", " I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneSword), new Object[] { " # ", " # ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.STICK});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneHelmet), new Object[] {"###", "# #", "   ", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneHelmet), new Object[] {"   ", "###", "# #", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneChestplate), new Object[] {"# #", "###", "###", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneLeggings), new Object[] {"###", "# #", "# #", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneBoots), new Object[] {"# #", "# #", "   ", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneBoots), new Object[] {"   ", "# #", "# #", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.teOreCrusher)), new Object[] {"###", "#I#", "#$#", '#', Items.IRON_INGOT, 'I', Items.REDSTONE, '$', ModItems.blueStoneIngot}); // TODO change
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.teEleFurnace)), new Object[] {"###", "#F#", "#R#", '#', Items.IRON_INGOT, 'F', Item.getItemFromBlock(Blocks.FURNACE), 'R', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK)});
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.teEleGenerator)), new Object[] {"FFF", "FRF", "BfB", 'F', Items.IRON_INGOT, 'R', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 'B', ModItems.blueStoneIngot, 'f', Item.getItemFromBlock(Blocks.FURNACE)});
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.tePowerBox)), new Object[] {"FBF", "BRB", "FBF", 'F', Items.IRON_INGOT, 'R', Item.getItemFromBlock(Blocks.REDSTONE_BLOCK), 'B', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueApple), new Object[] {"###", "#I#", "###", '#', ModItems.blueStoneIngot, 'I', Items.APPLE});
		GameRegistry.addRecipe(new ItemStack(ModItems.BLUESTONESICKLE), new Object[] {" B ", "  B", "SB ", 'B', ModItems.blueStoneIngot, 'S', Items.STICK});
	}
}