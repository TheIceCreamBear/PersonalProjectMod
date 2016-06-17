package com.joseph.personalprojectmod.creativetabs;

import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.refrence.Refrence;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabsPPM {
	public static final CreativeTabs PPM_ITEMS_TAB = new CreativeTabs(Refrence.LOWER_CASE_MOD_ID) {
		@Override
		public Item getTabIconItem() {
			return ModItems.blueStoneIngot; //TODO
		}
	};
}