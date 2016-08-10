package com.joseph.personalprojectmod.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModCrafting {
	public static void initCrafting() {
		GameRegistry.addSmelting(ModBlocks.blueStoneOre, new ItemStack(ModItems.blueStoneIngot), 1.5f);
		GameRegistry.addSmelting(ModItems.blueStoneDust, new ItemStack(ModItems.blueStoneIngot), 1.5f);		
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneAxe), new Object[] { "## ", "#I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneAxe), new Object[] { " ##", " I#", " I ", '#', ModItems.blueStoneIngot, 'I', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneHoe), new Object[] { "## ", " I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStonePickaxe), new Object[] { "###", " I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneSpade), new Object[] { " # ", " I ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneSword), new Object[] { " # ", " # ", " I ", '#', ModItems.blueStoneIngot, 'I', Items.stick});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneHelmet), new Object[] {"###", "# #", "   ", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneHelmet), new Object[] {"   ", "###", "# #", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneChestplate), new Object[] {"# #", "###", "###", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneLeggings), new Object[] {"###", "# #", "# #", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneBoots), new Object[] {"# #", "# #", "   ", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(ModItems.blueStoneBoots), new Object[] {"   ", "# #", "# #", '#', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.teOreCrusher)), new Object[] {"###", "#I#", "#$#", '#', Items.iron_ingot, 'I', Items.redstone, '$', ModItems.blueStoneIngot});
		GameRegistry.addRecipe(new ItemStack(Item.getItemFromBlock(ModBlocks.teEleFurnace)), new Object[] {"###", "#F#", "#R#", '#', Items.iron_ingot, 'F', Item.getItemFromBlock(Blocks.furnace), 'R', Item.getItemFromBlock(Blocks.redstone_block)});
		
	}
}