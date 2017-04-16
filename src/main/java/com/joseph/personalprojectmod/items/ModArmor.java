package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.creativetabs.CreativeTabsPPM;
import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.reference.ConfigRef;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ModArmor extends ItemArmor {
	public ModArmor(String unlocalizedName, String registryName, ArmorMaterial mat, int renderIndex, EntityEquipmentSlot armorType) {
		super(mat, renderIndex, armorType);
		this.setUnlocalizedName(unlocalizedName);
		this.setRegistryName(registryName);
		this.setCreativeTab(CreativeTabsPPM.PPM_TOOLS_TAB);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if (ConfigRef.doArmorEffect) {
			if (stack.getItem() == ModItems.blueStoneHelmet) {
				effectPlayer(player, Potion.getPotionById(13), 1); // water breathing
				effectPlayer(player, Potion.getPotionById(16), 0); // night vision
			}
			
			if (stack.getItem() == ModItems.blueStoneChestplate) {
				effectPlayer(player, Potion.getPotionById(3), 1); // haste
			}
			
			if (stack.getItem() == ModItems.blueStoneLeggings) {
				effectPlayer(player, Potion.getPotionById(12), 1); // fire resistance
			}
			
			if (stack.getItem() == ModItems.blueStoneBoots) {
			}
			
			if (checkFullBlueStoneArmor(player)) {
				effectPlayer(player, Potion.getPotionById(10), 2); // regeneration
			}
		}
	}
	
	private void effectPlayer(EntityPlayer player, Potion potion, int amplifier) {
		if (player.getActivePotionEffect(potion) == null || player.getActivePotionEffect(potion).getDuration() <= 200) {
			player.addPotionEffect(new PotionEffect(potion, 1600, amplifier, true, true));
		}
	}
	
	private boolean checkFullBlueStoneArmor(EntityPlayer player) {
		return player.inventory.armorItemInSlot(3) != null && player.inventory.armorItemInSlot(3).getItem() == ModItems.blueStoneHelmet
		        && player.inventory.armorItemInSlot(2) != null && player.inventory.armorItemInSlot(2).getItem() == ModItems.blueStoneChestplate
		        && player.inventory.armorItemInSlot(1) != null && player.inventory.armorItemInSlot(1).getItem() == ModItems.blueStoneLeggings
		        && player.inventory.armorItemInSlot(0) != null && player.inventory.armorItemInSlot(0).getItem() == ModItems.blueStoneBoots;
	}
}