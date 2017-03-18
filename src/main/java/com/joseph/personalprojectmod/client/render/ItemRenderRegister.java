package com.joseph.personalprojectmod.client.render;

import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.refrence.Refrence;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

public class ItemRenderRegister {
	public static void registerItemRender() {
		reg(ModItems.blueStoneIngot);
		reg(ModItems.blueStoneDust);
		reg(ModItems.blueApple);
		reg(ModItems.idCardRef);
		
		// Tools
		reg(ModItems.blueStoneSpade);
		reg(ModItems.blueStonePickaxe);
		reg(ModItems.blueStoneAxe);
		reg(ModItems.blueStoneHoe);
		reg(ModItems.blueStoneSword);
		
		// Armor
		reg(ModItems.blueStoneHelmet);
		reg(ModItems.blueStoneChestplate);
		reg(ModItems.blueStoneLeggings);
		reg(ModItems.blueStoneBoots);
	}
	
	public static void reg(Item item) {		
		Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Refrence.LOWER_CASE_MOD_ID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
	}
}