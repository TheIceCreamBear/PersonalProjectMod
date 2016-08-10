package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.util.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;

public class TileEntityElectricFurnace extends TileEntity implements ITickable, IInventory {
	private ItemStack[] inventory;
	private String customName;
	
	private int powerStroed;
	
	private int cookTimeOne;
	private int totalCookTimeOne;
	private int cookTimeTwo;
	private int totalCookTimeTwo;
	
	public TileEntityElectricFurnace() {
		this.inventory = new ItemStack[this.getSizeInventory()];
	}
	
	public String getCustomName() {
		return this.customName;
	}
	
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	
	@Override
	public void update() {
		boolean isDirty = false;
		
		if (!this.worldObj.isRemote) {
			// First slot smelting stuffs
			if (this.canSmelt(0)) {
				this.cookTimeOne++;
				
				if (this.cookTimeOne == this.totalCookTimeOne) {
					this.cookTimeOne = 0;
					this.totalCookTimeOne = this.getCookTime(this.inventory[0]);
					this.smeltItem(0);
					isDirty = true;
				}
			} else {
				this.cookTimeOne = 0;
			}
			
			// Second Slot
			if (this.canSmelt(1)) {
				this.cookTimeTwo++;
				
				if (this.cookTimeTwo == this.totalCookTimeTwo) {
					this.cookTimeTwo = 0;
					this.totalCookTimeTwo = this.getCookTime(this.inventory[1]);
					this.smeltItem(1);
					isDirty = true;
				}
			} else {
				this.cookTimeTwo = 0;
			}
		}
		
		if (isDirty) {
			this.markDirty();
		}
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.personalprojectmod:ele_furnace";
	}
	
	@Override
	public boolean hasCustomName() {
		return this.customName != null && !this.customName.equals("");
	}
	
	@Override
	public IChatComponent getDisplayName() {
		return this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatComponentTranslation(this.getName());
	}
	
	@Override
	public int getSizeInventory() {
		return 4;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		if (index < 0 || index >= this.getSizeInventory())
	        return null;
	    return this.inventory[index];
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if (this.getStackInSlot(index) != null) {
			ItemStack itemstack;

			if (this.getStackInSlot(index).stackSize <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).stackSize <= 0) {
					this.setInventorySlotContents(index, null);
				} else {
					// Just to show that changes happened
					this.setInventorySlotContents(index, this.getStackInSlot(index));
				}

				this.markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (this.getStackInSlot(index) != null) {
			ItemStack stack = this.getStackInSlot(index);
			this.setInventorySlotContents(index, null);
			return stack;
		} else {
			return null;
		}
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
	        return;

		boolean flag = stack != null && stack.isItemEqual(this.inventory[index]) && ItemStack.areItemStackTagsEqual(stack, this.inventory[index]);
        this.inventory[index] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        if (index == 0 && !flag) {
            this.totalCookTimeOne = this.getCookTime(stack);
            this.cookTimeOne = 0;
            this.markDirty();
        }
        
        if (index == 1 && !flag) {
            this.totalCookTimeTwo = this.getCookTime(stack);
            this.cookTimeTwo = 0;
            this.markDirty();
        }
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.getPos().add(0.5, 0.5, 0.5)) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
		
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index == 2 ? false : index == 3 ? false : true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return this.cookTimeOne;
			case 1: return this.totalCookTimeOne;
			case 2: return this.cookTimeTwo;
			case 3: return this.totalCookTimeTwo;
			default: return 0;
		}
	}
	
	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.cookTimeOne = value; break;
			case 1: this.totalCookTimeOne = value; break;
			case 2: this.cookTimeTwo = value; break;
			case 3: this.totalCookTimeTwo = value; break;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		for (int i = 0; i < this.getSizeInventory(); i++) {
			this.setInventorySlotContents(i, null);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("CookTimeOne", this.cookTimeOne);
		nbt.setInteger("TotalCookTimeOne", this.totalCookTimeOne);
		nbt.setInteger("CookTimeTwo", this.cookTimeTwo);
		nbt.setInteger("TotalCookTimeTwo", this.totalCookTimeTwo);
		
		NBTTagList list = new NBTTagList();
		for (int i = 0; i < this.getSizeInventory(); i++) {
			if (this.getStackInSlot(i) != null) {
				NBTTagCompound stackTag = new NBTTagCompound();
				stackTag.setByte("Slot", (byte)i);
				this.getStackInSlot(i).writeToNBT(stackTag);
				list.appendTag(stackTag);
			}
		}
		nbt.setTag("Items", list);
		
		if (this.hasCustomName()) {
			nbt.setString("CustomeName", this.getCustomName());
		}
		
		// TODO Add Other Things to this
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.cookTimeOne = nbt.getInteger("CookTimeOne");
		this.totalCookTimeOne = nbt.getInteger("TotalCookTimeOne");
		this.cookTimeTwo = nbt.getInteger("CookTimeTwo");
		this.totalCookTimeTwo = nbt.getInteger("TotalCookTimeTwo");
		
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(stackTag));
		}
		
		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomeName"));
		}
		
		// TODO Add Other Things to this
	}
	
	public int getCookTime(ItemStack stack) {
		return 100;
	}
	
	private boolean canSmelt(int slot) {
		if (this.inventory[slot] == null) {
			return false;
		} else {
			ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[slot]);
			if (stack == null) return false;
			if (this.inventory[slot + 2] == null) return true;
			if (!this.inventory[slot + 2].isItemEqual(stack)) return false;
			int result = this.inventory[slot + 2].stackSize + stack.stackSize;
			return result <= this.getInventoryStackLimit() && result <= this.inventory[slot + 2].getMaxStackSize();
		}
	}
	
	public void smeltItem(int slot) {
		if (slot < 0 || slot > 1) return;
		if (this.canSmelt(slot)) {
			ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(this.inventory[slot]);
			
			if (this.inventory[slot + 2] == null) {
				this.inventory[slot + 2] = stack.copy();
			} else if (this.inventory[slot + 2].getItem() == stack.getItem()) {
				this.inventory[slot + 2].stackSize += stack.stackSize;
			}
			
			this.inventory[slot].stackSize--;
			if (this.inventory[slot].stackSize <= 0) {
				this.inventory[slot] = null;
			}
		}
	}
}