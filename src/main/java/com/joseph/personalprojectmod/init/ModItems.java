package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.items.BasicItem;
import com.joseph.personalprojectmod.items.ModArmor;
import com.joseph.personalprojectmod.items.ModAxe;
import com.joseph.personalprojectmod.items.ModHoe;
import com.joseph.personalprojectmod.items.ModPickaxe;
import com.joseph.personalprojectmod.items.ModSpade;
import com.joseph.personalprojectmod.items.ModSword;

import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
	// Materials
	public static ToolMaterial BLUE_STONE = EnumHelper.addToolMaterial("BLUE_STONE", 3, 1250, 15.0f, 4.0f, 30);
	public static ArmorMaterial BLUE_STONE_ARMOR = EnumHelper.addArmorMaterial("BLUE_STONE_ARMOR", "personalprojectmod:bluestone", 20, new int[] {4, 8, 6, 4}, 30);
	
	// Tools
	public static Item blueStonePickaxe = new ModPickaxe("blue_stone_pickaxe", BLUE_STONE);
	public static Item blueStoneAxe = new ModAxe("blue_stone_axe", BLUE_STONE);
	public static Item blueStoneHoe = new ModHoe("blue_stone_hoe", BLUE_STONE);
	public static Item blueStoneSpade = new ModSpade("blue_stone_spade", BLUE_STONE);
	public static Item blueStoneSword = new ModSword("blue_stone_sword", BLUE_STONE);
	
	// Armor
	public static Item blueStoneHelmet = new ModArmor("blue_stone_helmet", BLUE_STONE_ARMOR, 1, 0);
	public static Item blueStoneChestplate = new ModArmor("blue_stone_chestplate", BLUE_STONE_ARMOR, 1, 1);
	public static Item blueStoneLeggings = new ModArmor("blue_stone_leggings", BLUE_STONE_ARMOR, 2, 2);
	public static Item blueStoneBoots = new ModArmor("blue_stone_boots", BLUE_STONE_ARMOR, 1, 3);
	
	// Generic Items
	public static Item blueStoneIngot = new BasicItem("blue_stone_ingot");
	public static Item blueStoneDust = new BasicItem("blue_stone_dust");
	
	
	// SELF NOTE: WHEN EVER A NEW ITEM IS ADDED, IT NEEDS TO BE REISTERED IN ItemRenderRegister
	public static void createItems() {
		// Generic Items
		GameRegistry.registerItem(blueStoneIngot, "blue_stone_ingot");
		GameRegistry.registerItem(blueStoneDust, "blue_stone_dust");
		
		// Tools
		GameRegistry.registerItem(blueStoneSpade, "blue_stone_spade");
		GameRegistry.registerItem(blueStonePickaxe, "blue_stone_pickaxe");
		GameRegistry.registerItem(blueStoneAxe, "blue_stone_axe");
		GameRegistry.registerItem(blueStoneHoe, "blue_stone_hoe");
		GameRegistry.registerItem(blueStoneSword, "blue_stone_sword");
		
		// Armor
		GameRegistry.registerItem(blueStoneHelmet, "blue_stone_helmet");
		GameRegistry.registerItem(blueStoneChestplate, "blue_stone_chestplate");
		GameRegistry.registerItem(blueStoneLeggings, "blue_stone_leggings");
		GameRegistry.registerItem(blueStoneBoots, "blue_stone_boots");
	}
}