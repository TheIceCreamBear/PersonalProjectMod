package com.joseph.personalprojectmod.init;

import com.joseph.personalprojectmod.items.ItemBlueApple;
import com.joseph.personalprojectmod.items.ItemBlueStoneDust;
import com.joseph.personalprojectmod.items.ItemBlueStoneIngot;
import com.joseph.personalprojectmod.items.ModArmor;
import com.joseph.personalprojectmod.items.ModAxe;
import com.joseph.personalprojectmod.items.ModHoe;
import com.joseph.personalprojectmod.items.ModPickaxe;
import com.joseph.personalprojectmod.items.ModSickle;
import com.joseph.personalprojectmod.items.ModSpade;
import com.joseph.personalprojectmod.items.ModSword;
import com.joseph.personalprojectmod.refrence.EnumItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

public final class ModItems {
	// Materials
	public static ToolMaterial BLUE_STONE;
	public static ArmorMaterial BLUE_STONE_ARMOR;
	
	// Tools
	public static Item blueStonePickaxe;
	public static Item blueStoneAxe;
	public static Item blueStoneHoe;
	public static Item blueStoneSpade;
	public static Item blueStoneSword;
	public static Item BLUESTONESICKLE;
	
	// Armor
	public static Item blueStoneHelmet;
	public static Item blueStoneChestplate;
	public static Item blueStoneLeggings;
	public static Item blueStoneBoots;
	
	// Generic Items
	public static Item blueStoneIngot;
	public static Item blueStoneDust;
	public static Item blueApple;	
	
	public static void init() {
		BLUE_STONE = EnumHelper.addToolMaterial("BLUE_STONE", 3, 1250, 15.0f, 4.0f, 30);
		BLUE_STONE_ARMOR = EnumHelper.addArmorMaterial("BLUE_STONE_ARMOR", "personalprojectmod:bluestone", 20, new int[] {4, 8, 6, 4}, 30, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.5f);
		
		// Generic Items
		blueStoneIngot = new ItemBlueStoneIngot();
		blueStoneDust = new ItemBlueStoneDust();
		blueApple = new ItemBlueApple();
		
		// Tools
		blueStonePickaxe = new ModPickaxe(BLUE_STONE, EnumItems.BLUE_STONE_PICKAXE.getUnlocalizedName(), EnumItems.BLUE_STONE_PICKAXE.getRegistryName());
		blueStoneAxe = new ModAxe(BLUE_STONE, EnumItems.BLUE_STONE_AXE.getUnlocalizedName(), EnumItems.BLUE_STONE_AXE.getRegistryName());
		blueStoneHoe = new ModHoe(BLUE_STONE, EnumItems.BLUE_STONE_HOE.getUnlocalizedName(), EnumItems.BLUE_STONE_HOE.getRegistryName());
		blueStoneSpade = new ModSpade(BLUE_STONE, EnumItems.BLUE_STONE_SPADE.getUnlocalizedName(), EnumItems.BLUE_STONE_SPADE.getRegistryName(), 13.0f);
		blueStoneSword = new ModSword(BLUE_STONE, EnumItems.BLUE_STONE_SWORD.getUnlocalizedName(), EnumItems.BLUE_STONE_SWORD.getRegistryName());
		BLUESTONESICKLE = new ModSickle(BLUE_STONE, EnumItems.BLUE_STONE_SICKLE.getUnlocalizedName(), EnumItems.BLUE_STONE_SICKLE.getRegistryName());
		
		// Armor
		blueStoneHelmet = new ModArmor(EnumItems.BLUE_STONE_HELMET.getUnlocalizedName(), EnumItems.BLUE_STONE_HELMET.getRegistryName(), BLUE_STONE_ARMOR, 1, EntityEquipmentSlot.HEAD);
		blueStoneChestplate = new ModArmor(EnumItems.BLUE_STONE_CHEST.getUnlocalizedName(), EnumItems.BLUE_STONE_CHEST.getRegistryName(), BLUE_STONE_ARMOR, 1, EntityEquipmentSlot.CHEST);
		blueStoneLeggings = new ModArmor(EnumItems.BLUE_STONE_LEGS.getUnlocalizedName(), EnumItems.BLUE_STONE_LEGS.getRegistryName(), BLUE_STONE_ARMOR, 2, EntityEquipmentSlot.LEGS);
		blueStoneBoots = new ModArmor(EnumItems.BLUE_STONE_BOOTS.getUnlocalizedName(), EnumItems.BLUE_STONE_BOOTS.getRegistryName(), BLUE_STONE_ARMOR, 1, EntityEquipmentSlot.FEET);
		
	}
	
	public static void register() {
		// Generic Items
		GameRegistry.register(blueStoneIngot);
		GameRegistry.register(blueStoneDust);
		GameRegistry.register(blueApple);
		
		// Tools
		GameRegistry.register(blueStonePickaxe);
		GameRegistry.register(blueStoneAxe);
		GameRegistry.register(blueStoneHoe);
		GameRegistry.register(blueStoneSpade);
		GameRegistry.register(blueStoneSword);
		GameRegistry.register(BLUESTONESICKLE);
		
		// Armor
		GameRegistry.register(blueStoneHelmet);
		GameRegistry.register(blueStoneChestplate);
		GameRegistry.register(blueStoneLeggings);
		GameRegistry.register(blueStoneBoots);
	}
	
	public static void registerRenders() {
		// Generic Items
		regRenderItem(blueStoneIngot);
		regRenderItem(blueStoneDust);
		regRenderItem(blueApple);
		
		// Tools
		regRenderItem(blueStonePickaxe);
		regRenderItem(blueStoneAxe);
		regRenderItem(blueStoneHoe);
		regRenderItem(blueStoneSpade);
		regRenderItem(blueStoneSword);
		regRenderItem(BLUESTONESICKLE);
		
		// Armor
		regRenderItem(blueStoneHelmet);
		regRenderItem(blueStoneChestplate);
		regRenderItem(blueStoneLeggings);
		regRenderItem(blueStoneBoots);
	}
	
	private static void regRenderItem(Item item) {
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}