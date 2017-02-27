package com.joseph.personalprojectmod.creativetabs;

import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.refrence.Refrence;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CreativeTabsPPM {
	public static final CreativeTabs PPM_ITEMS_TAB = new CreativeTabs(Refrence.MOD_ID) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.blueStoneIngot);
		}
	};
	public static final CreativeTabs PPM_TOOLS_TAB = new CreativeTabs(Refrence.MOD_ID) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.blueStonePickaxe);
		}
	};
	public static final CreativeTabs PPM_FOOD_TAB = new CreativeTabs(Refrence.MOD_ID) {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.blueApple);
		}
	};
}