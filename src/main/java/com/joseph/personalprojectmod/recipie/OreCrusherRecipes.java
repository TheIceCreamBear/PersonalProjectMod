package com.joseph.personalprojectmod.recipie;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.init.ModItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreCrusherRecipes {
	private static final OreCrusherRecipes instance = new OreCrusherRecipes();
	private Map<ItemStack, ItemStack> recipeList = Maps.<ItemStack, ItemStack>newHashMap();
	
	public static OreCrusherRecipes instance() {
		return instance;
	}
	
	private OreCrusherRecipes() {
		if (OreDictionary.doesOreNameExist("dustIron")) {
			this.addCrushingForBlock(Blocks.IRON_ORE, new ItemStack(OreDictionary.getOres("dustIron").get(0).getItem(), 2, OreDictionary.getOres("dustIron").get(0).getMetadata()));			
		}
		if (OreDictionary.doesOreNameExist("dustGold")) {
			this.addCrushingForBlock(Blocks.GOLD_ORE, new ItemStack(OreDictionary.getOres("dustGold").get(0).getItem(), 2, OreDictionary.getOres("dustGold").get(0).getMetadata()));
		}
		if (OreDictionary.doesOreNameExist("dustTin") && OreDictionary.doesOreNameExist("oreTin")) {
			this.addCrushingRecipe(OreDictionary.getOres("oreTin").get(0), new ItemStack(OreDictionary.getOres("dustTin").get(0).getItem(), 2, OreDictionary.getOres("dustTin").get(0).getMetadata()));
		}
		if (OreDictionary.doesOreNameExist("oreCopper") && OreDictionary.doesOreNameExist("dustCopper")) {
			this.addCrushingRecipe(OreDictionary.getOres("oreCopper").get(0), new ItemStack(OreDictionary.getOres("dustCopper").get(0).getItem(), 2, OreDictionary.getOres("dustCopper").get(0).getMetadata()));
		}
		if (OreDictionary.doesOreNameExist("oreLead") && OreDictionary.doesOreNameExist("dustLead")) {
			this.addCrushingRecipe(OreDictionary.getOres("oreLead").get(0), new ItemStack(OreDictionary.getOres("dustLead").get(0).getItem(), 2, OreDictionary.getOres("dustLead").get(0).getMetadata()));
		}
		this.addCrushingForBlock(ModBlocks.blueStoneOre, new ItemStack(ModItems.blueStoneDust, 2));
	}
	
	public ItemStack getReslut(ItemStack stack) {
		for (Entry<ItemStack, ItemStack> entry : this.recipeList.entrySet()) {
			if (this.compareItemStacks(stack, (ItemStack)entry.getKey())) {
				return (ItemStack)entry.getValue();
			}
		}
		return null;
	}
	
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	
	public void addCrushingRecipe(ItemStack input, ItemStack output) {
		this.recipeList.put(input, output);
	}
	
	public void addCrushing(Item input, ItemStack output) {
		this.addCrushingRecipe(new ItemStack(input, 1, Short.MAX_VALUE), output);
	}
	
	public void addCrushingForBlock(Block input, ItemStack output) {
		this.addCrushing(Item.getItemFromBlock(input), output);
	}
}