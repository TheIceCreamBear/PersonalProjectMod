package com.joseph.personalprojectmod.guicontainer;

import com.joseph.personalprojectmod.recipie.OreCrusherRecipes;
import com.joseph.personalprojectmod.tileentity.TileEntityOreCrusher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerTEOreCrusher extends Container {
	private TileEntityOreCrusher te;
	private int field0;
	private int field1;
	private int field2;
	private int field3;
	
	public ContainerTEOreCrusher(IInventory playerInv, TileEntityOreCrusher te) {
		this.te = te;
		
		// inputSlot TODO - make slot extension \/ \/
		this.addSlotToContainer(new Slot(te, 0, 53, 31));
		
		// Output slot one
		this.addSlotToContainer(new Slot(te, 1, 116, 31));
		
		// Output slot two
		this.addSlotToContainer(new Slot(te, 2, 134, 31));
		
		// Player Inventory
		for (int y = 0; y < 3; ++y) {
	        for (int x = 0; x < 9; ++x) {
	            this.addSlotToContainer(new Slot(playerInv, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
	        }
	    }

	    // Player Inventory
	    for (int x = 0; x < 9; ++x) {
	        this.addSlotToContainer(new Slot(playerInv, x, 8 + x * 18, 142));
	    }
	}
	
	@Override
	public void onCraftGuiOpened(ICrafting listener) {
        super.onCraftGuiOpened(listener);
        listener.sendAllWindowProperties(this, this.te);
    }
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for (int i = 0; i < this.crafters.size(); i++) {
			ICrafting icrafting = (ICrafting)this.crafters.get(i);
			
			if (this.field0 != this.te.getField(0)) {
				icrafting.sendProgressBarUpdate(this, 0, this.te.getField(0));
			}
			if (this.field1 != this.te.getField(1)) {
				icrafting.sendProgressBarUpdate(this, 1, this.te.getField(1));
			}
			if (this.field2 != this.te.getField(2)) {
				icrafting.sendProgressBarUpdate(this, 2, this.te.getField(2));
			}
			if (this.field3 != this.te.getField(3)) {
				icrafting.sendProgressBarUpdate(this, 3, this.te.getField(3));
			}
		}
		
		this.field0 = this.te.getField(0);
		this.field1 = this.te.getField(1);
		this.field2 = this.te.getField(2);
		this.field3 = this.te.getField(3);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.te.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
	    ItemStack previous = null;
	    Slot slot = (Slot) this.inventorySlots.get(fromSlot);

	    if (slot != null && slot.getHasStack()) {
	        ItemStack current = slot.getStack();
	        previous = current.copy();

	        if (fromSlot == 1 || fromSlot == 2) {
	        	if (!this.mergeItemStack(current, 3, 39, true))
	        		return null;
	        	slot.onSlotChange(current, previous);
	        } else if (fromSlot != 0) {
	        	if (OreCrusherRecipes.instance().getReslut(current) != null) {
	        		if (!this.mergeItemStack(current, 0, 1, false)) {
	        			return null;
	        		}
	        	}
	        } else if (!this.mergeItemStack(current, 3, 39, false)) {
	        	return null;
	        }

	        if (current.stackSize == 0)
	            slot.putStack((ItemStack) null);
	        else
	            slot.onSlotChanged();

	        if (current.stackSize == previous.stackSize)
	            return null;
	        slot.onPickupFromSlot(playerIn, current);
	    }
	    return previous;
	}
}