package com.joseph.personalprojectmod.guicontainer;

import com.joseph.personalprojectmod.tileentity.TileEntityElectricFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTEElectricFurnace extends Container {
	private TileEntityElectricFurnace te;
	
	public ContainerTEElectricFurnace(IInventory playerInv, TileEntityElectricFurnace te) {
		this.te = te;
		
		// TODO add the te's slots
		
		// Input slot on left
		this.addSlotToContainer(new Slot(te, 0, 33, 31));
		
		// Input slot on right
		this.addSlotToContainer(new Slot(te, 1, 51, 31));
		
		// Output slot one
		this.addSlotToContainer(new Slot(te, 2, 116, 31));
				
		// Output slot two
		this.addSlotToContainer(new Slot(te, 3, 134, 31));
		
		
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

	        // [...] Custom behavior

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