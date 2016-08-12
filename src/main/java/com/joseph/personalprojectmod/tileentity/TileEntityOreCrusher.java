package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.recipie.OreCrusherRecipes;
import com.joseph.personalprojectmod.util.LogHelper;

import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityOreCrusher extends TileEntity implements ITickable, IInventory, IEnergySink {

	private ItemStack[] inventory;
	private String customName;
	
	private int crushTime;
	private int totalCrushTime;
	
	// Power stuff
	private boolean addedToENet = false;
	
	private double energyStored;
	private double capacity;
	private int tier = Integer.MAX_VALUE;
	
	// End Power Stuff
	
	public TileEntityOreCrusher() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.capacity = 10000;
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
    	
    	// Client Side
    	if (this.worldObj.isRemote) {

    	}
    		
    	// Server Side
    	if (!this.worldObj.isRemote) {
//    		LogHelper.info(this.energyStored + "/" + this.capacity);
    		
    		if (!this.addedToENet) {MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this)); this.addedToENet = true;}
    		
    		if (this.canCrush()) {
    			if (this.useEnergy(4)) {    				
    				this.crushTime++;
    				
    				if (this.crushTime == this.totalCrushTime) {
    					this.crushTime = 0;
    					this.totalCrushTime = this.getCrushTime(this.inventory[0]);
    					this.crushItem();
    					isDirty = true;
    				}
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
			case 2: return (int) this.energyStored;
			case 3: return (int) this.capacity;
			default: return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.crushTime = value; break;
			case 1: this.totalCrushTime = value; break;
			case 2: this.energyStored = value; break;
			case 3: this.capacity = value; break;
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
		
		nbt.setInteger("CrushTIime", this.crushTime);
		nbt.setInteger("TotalCrushTime", this.totalCrushTime);
		
		// Energy
		nbt.setDouble("EnergyStored", this.energyStored);
		nbt.setDouble("Capacity", this.capacity);
		// End Energy
		
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
		
		// Energy
		this.energyStored = nbt.getDouble("EnergyStored");
		this.capacity = nbt.getDouble("Capacity");
		// End Energy
		
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
	
	// CRAFTING START
	
	public int getCrushTime(ItemStack stack) {
		return 150;
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
	
	// CRAFTING END
	
	@Override
	public void invalidate() {
		super.invalidate();
		
		this.onChunkUnload();
	}
	
	@Override
	public void onChunkUnload() {
		if (this.addedToENet) {
			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			
			this.addedToENet = false;
		}
	}
	
	// Energy related things
	
	@Override
	public int getSinkTier() {
		return Integer.MAX_VALUE; // Allows any tier of power
	}
	
	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
		return true;
	}
	
	@Override
	public double getDemandedEnergy() {
		return Math.max(0, this.capacity - this.energyStored);
	}
	
	@Override
	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
		energyStored += amount;

		return 0;
	}
	
	private boolean canUseEnergy(double amount) {
		return this.energyStored >= amount;
	}
	
	private boolean useEnergy(double amount) {
		if (this.canUseEnergy(amount)) {
			this.energyStored -= amount;
			
			return true;
		}
		return false;
	}
}