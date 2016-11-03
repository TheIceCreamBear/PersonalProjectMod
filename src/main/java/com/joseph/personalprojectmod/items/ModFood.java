package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ModFood extends ItemFood {
	private PotionEffect[] effects;
	
	public ModFood(String unlocalizedName, int amount, float saturation, boolean isWolf, PotionEffect... effects) {
		super(amount, saturation, isWolf);
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
		this.effects = effects;
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
		
		for (int i = 0; i < effects.length; i++) {
			if (!world.isRemote && this.effects[i] != null && this.effects[i].getPotionID() > 0) {
				player.addPotionEffect(new PotionEffect(this.effects[i]));
			}
		}
	}
}