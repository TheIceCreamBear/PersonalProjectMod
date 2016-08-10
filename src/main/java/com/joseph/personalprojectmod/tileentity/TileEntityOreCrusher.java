package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.recipie.OreCrusherRecipes;
import com.joseph.personalprojectmod.util.LogHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;

public class TileEntityOreCrusher extends TileEntity implements ITickable, IInventory /*IEnergySink TODO*/ {

	private ItemStack[] inventory;
	private String customName;
	
	private int powerStored;
	
	private int crushTime;
	private int totalCrushTime;
	
	public TileEntityOreCrusher() {
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
    		if (this.canCrush()) {
    			this.crushTime++;
    			
    			if (this.crushTime == this.totalCrushTime) {
    				this.crushTime = 0;
    				this.totalCrushTime = this.getCrushTime(this.inventory[0]);
    				this.crushItem();
    				isDirty = true;
    			}
    		} else {
    			this.crushTime = 0;
    		}
    	}
    	
    	if (isDirty) {
    		this.markDirty();
    	}
    }
    
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.personalprojectmod:ore_crusher";
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
		return 3;
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
        	this.totalCrushTime = this.getCrushTime(stack);
        	this.crushTime = 0;
        }
        this.markDirty();
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
		return index == 1 ? false : index == 2 ? false : true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return this.crushTime;
			case 1: return this.totalCrushTime;
			default: return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.crushTime = value;
			case 1: this.totalCrushTime = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 2;
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
		
		nbt.setInteger("CrushTIime", this.crushTime);
		nbt.setInteger("TotalCrushTime", this.totalCrushTime);
		
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
		
		this.crushTime = nbt.getInteger("CrushTime");
		this.totalCrushTime = nbt.getInteger("TotalCrushTime");
		
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
	
	public int getCrushTime(ItemStack stack) {
		return 100;
	}
	
	private boolean canCrush() {
		if (this.inventory[0] == null) {
			return false;
		} else{
			ItemStack stack = OreCrusherRecipes.instance().getReslut(this.inventory[0]);
			if (stack == null) return false;
			if (this.inventory[1] == null) return true;
			if (this.inventory[2] == null) return true;
			if ((!this.inventory[1].isItemEqual(stack) || this.inventory[1].getMetadata() != stack.getMetadata()) && !(this.inventory[2].isItemEqual(stack) || this.inventory[2].getMetadata() == stack.getMetadata())) {return false;}
			if (this.inventory[2].isItemEqual(stack) || this.inventory[2].getMetadata() == stack.getMetadata()) {
				if (!this.inventory[2].isItemEqual(stack) || this.inventory[2].getMetadata() != stack.getMetadata()) return false;
				int result = this.inventory[2].stackSize + stack.stackSize;
				return result <= this.getInventoryStackLimit() && result <= this.inventory[2].getMaxStackSize();
			}
			int result = this.inventory[1].stackSize + stack.stackSize;
			return result <= this.getInventoryStackLimit() && result <= this.inventory[1].getMaxStackSize();
		}
	}
	
	public void crushItem() {
		if (this.canCrush()) {
			ItemStack stack = OreCrusherRecipes.instance().getReslut(this.inventory[0]);
			
			if (this.inventory[1] == null) {
				this.inventory[1] = stack.copy();
			} else if (this.inventory[1].getItem() == stack.getItem() && this.inventory[1].getMetadata() == stack.getMetadata() && !(this.inventory[1].stackSize == this.getInventoryStackLimit() || this.inventory[1].getMaxStackSize() == this.inventory[1].stackSize)) {
				this.inventory[1].stackSize += stack.stackSize;
			} else if (this.inventory[2] == null) {
				this.inventory[2] = stack.copy();
			} else if (this.inventory[2].getItem() == stack.getItem() && this.inventory[2].getMetadata() == stack.getMetadata()) {
				this.inventory[2].stackSize += stack.stackSize;
			}
			
			this.inventory[0].stackSize--;
			if (this.inventory[0].stackSize <= 0) {
				this.inventory[0] = null;
			}
		}
	}
}