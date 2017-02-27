package com.joseph.personalprojectmod.guicontainer;

import com.joseph.personalprojectmod.tileentity.TileEntityElectricFurnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerTEElectricFurnace extends Container {
	private TileEntityElectricFurnace te;
	private int field0;
	private int field1;
	private int field2;
	private int field3;
	private int field4;
	private int field5;
	
	public ContainerTEElectricFurnace(IInventory playerInv, TileEntityElectricFurnace te) {
		this.te = te;
		
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
	
//	@Override
//	public void onCraftGuiOpened(ICrafting listener) {
//		super.onCraftGuiOpened(listener);
//		listener.sendAllWindowProperties(this, this.te);
//	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); i++) {
			IContainerListener icrafting = (IContainerListener) this.listeners.get(i);
			
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
			
			if (this.field4 != this.te.getField(4)) {
				icrafting.sendProgressBarUpdate(this, 4, this.te.getField(4));
			}
			
			if (this.field5 != this.te.getField(5)) {
				icrafting.sendProgressBarUpdate(this, 5, this.te.getField(5));
			}
		}
		
		this.field0 = this.te.getField(0);
		this.field1 = this.te.getField(1);
		this.field2 = this.te.getField(2);
		this.field3 = this.te.getField(3);
		this.field4 = this.te.getField(4);
		this.field5 = this.te.getField(5);
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		this.te.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = (Slot) this.inventorySlots.get(fromSlot);
		
		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();
			
			if (fromSlot == 2 || fromSlot == 3) {
				if (!this.mergeItemStack(current, 4, 40, true))
					return null;
				
				slot.onSlotChange(current, previous);
			} else if (fromSlot != 0 && fromSlot != 1) {
				
				if (FurnaceRecipes.instance().getSmeltingResult(current) != null) {
					
					ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(current);
					
					if (this.inventorySlots.get(3) != null && this.inventorySlots.get(3).getHasStack()) {
						
						ItemStack stack1 = this.inventorySlots.get(3).getStack();
						
						if (stack.getItem() == stack1.getItem()) {
							if (!this.mergeItemStack(current, 0, 1, false))
								return null;
						}
					} else if (this.inventorySlots.get(4) != null && this.inventorySlots.get(4).getHasStack()) {
						
						ItemStack stack1 = this.inventorySlots.get(4).getStack();
						
						if (stack.getItem() == stack1.getItem()) {
							if (!this.mergeItemStack(current, 1, 2, false))
								return null;
						}
					} else if (!this.mergeItemStack(current, 0, 2, false)) {
						return null;
					}
				}
			} else if (!this.mergeItemStack(current, 4, 40, false)) {
				return null;
			}
			
			if (current.getCount() == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			
			if (current.getCount() == previous.getCount()) {
				return null;
			}
			slot.onTake(playerIn, current);
		}
		return previous;
	}
}