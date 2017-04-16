package com.joseph.personalprojectmod.creativetabs;

import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.reference.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabsPPM {
	public static final CreativeTabs PPM_ITEMS_TAB = new CreativeTabs(Reference.MOD_ID + ".items") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.blueStoneIngot);
		}
	};
	public static final CreativeTabs PPM_TOOLS_TAB = new CreativeTabs(Reference.MOD_ID + ".tools") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.blueStonePickaxe);
		}
	};
	public static final CreativeTabs PPM_FOOD_TAB = new CreativeTabs(Reference.MOD_ID + ".food") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.blueApple);
		}
	};
}