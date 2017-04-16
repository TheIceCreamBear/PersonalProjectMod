package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.reference.EnumItems;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class ItemBlueApple extends ModFood {
	public ItemBlueApple() {
		super(6, 1.5f, false, new PotionEffect(Potion.getPotionById(10), 100, 0));
		this.setUnlocalizedName(EnumItems.BLUE_APPLE.getUnlocalizedName());
		this.setRegistryName(EnumItems.BLUE_APPLE.getRegistryName());
	}
}