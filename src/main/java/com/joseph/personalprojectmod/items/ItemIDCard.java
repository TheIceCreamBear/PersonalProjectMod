package com.joseph.personalprojectmod.items;

import com.joseph.personalprojectmod.client.render.ItemRenderRegister;
import com.joseph.personalprojectmod.init.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemIDCard extends BasicItem {
	private static final String NBT_ID = "IDCard.PlayerID";
	
	public String playerID = "";

	public ItemIDCard(String unlocalizedName) {
		super(unlocalizedName);
		this.setMaxStackSize(1);
	}
	
	private ItemIDCard() {
		this("id_card");
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		if (player.isSneaking()) {
//			ItemIDCard tmp = new ItemIDCard();
//			ItemRenderRegister.reg(tmp);
//			tmp.playerID = "";
			itemStack.setItem(ModItems.idCardRef);
		} else {
			ItemIDCard tmp = new ItemIDCard();
			ItemRenderRegister.reg(tmp);
			tmp.playerID = player.getName();
			itemStack.setItem(tmp);			
		}
		return itemStack;
	}

	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		ItemIDCard tmp = new ItemIDCard();
		ItemRenderRegister.reg(tmp);
		tmp.playerID = player.getName();
		stack.setItem(tmp);
	}

	@Override
	public boolean updateItemStackNBT(NBTTagCompound nbt) {
		if (!nbt.getString(NBT_ID).equals(playerID)) {
			nbt.removeTag(NBT_ID);
			nbt.setString(NBT_ID, this.playerID);
			return true;
		}
		return false;
	}
}