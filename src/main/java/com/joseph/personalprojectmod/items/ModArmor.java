package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.refrence.ConfigRef;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ModArmor extends ItemArmor {
	public ModArmor(String unlocalizedName, ArmorMaterial mat, int renderIndex, int armorType) {
		super(mat, renderIndex, armorType);
		
		this.setUnlocalizedName(unlocalizedName);
		this.setCreativeTab(CreativeTabsPPM.PPM_ITEMS_TAB);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (ConfigRef.doArmorEffect) {
			if (stack.getItem() == ModItems.blueStoneHelmet) {
				effectPlayer(player, Potion.fireResistance, 1);
				effectPlayer(player, Potion.waterBreathing, 1);
				effectPlayer(player, Potion.nightVision, 0);
			}
			
			if (stack.getItem() == ModItems.blueStoneChestplate) {
				effectPlayer(player, Potion.digSpeed, 1);
			}
			
			if (stack.getItem() == ModItems.blueStoneLeggings) {
				
			}
			
			if (stack.getItem() == ModItems.blueStoneBoots) {
				
			}
			
			if (checkFullBlueStoneArmor(player)) {
				effectPlayer(player, Potion.regeneration, 2);
			}
		}
	}
	
	private void effectPlayer(EntityPlayer player, Potion potion, int amplifier) {
		if (player.getActivePotionEffect(potion) == null || player.getActivePotionEffect(potion).getDuration() <= 1) {
			player.addPotionEffect(new PotionEffect(potion.id, 159, amplifier, true, true));
		}
	}
	
	private boolean checkFullBlueStoneArmor(EntityPlayer player) {
		return player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == ModItems.blueStoneHelmet
		        && player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == ModItems.blueStoneChestplate
		        && player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() == ModItems.blueStoneLeggings
		        && player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == ModItems.blueStoneBoots;
	}
}