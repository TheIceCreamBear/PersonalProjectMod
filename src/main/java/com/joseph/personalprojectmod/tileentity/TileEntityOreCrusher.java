package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.recipie.OreCrusherRecipes;

//import ic2.api.energy.event.EnergyTileLoadEvent;
//import ic2.api.energy.event.EnergyTileUnloadEvent;
//import ic2.api.energy.tile.IEnergyEmitter;
//import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityOreCrusher extends TileEntity implements ITickable, IInventory/*, IEnergySink */ {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(3, ItemStack.EMPTY);
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
    	if (this.world.isRemote) {

    	}
    		
    	// Server Side
    	if (!this.world.isRemote) {
//    		if (!this.addedToENet) {MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this)); this.addedToENet = true;}
			if (this.canCrush()) {
				if (this.useEnergy(4)) {
					this.crushTime++;
					
					if (this.crushTime == this.totalCrushTime) {
						this.crushTime = 0;
						this.totalCrushTime = this.getCrushTime(this.stacks.get(0));
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
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack iStack : stacks) {
			if (!iStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
	    return (ItemStack)this.stacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(stacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(stacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
	        return;
		ItemStack itemstack = this.stacks.get(index);
		boolean flag = stack != null && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);

		if (stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
        
		this.stacks.set(index, stack);
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
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.getPos()) == this && player.getDistanceSq(this.getPos().add(0.5, 0.5, 0.5)) <= 64;
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
		this.stacks.clear();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("CrushTIime", this.crushTime);
		nbt.setInteger("TotalCrushTime", this.totalCrushTime);
		
		// Energy
		nbt.setDouble("EnergyStored", this.energyStored);
		nbt.setDouble("Capacity", this.capacity);
		// End Energy
		
		ItemStackHelper.saveAllItems(nbt, stacks);
		
		if (this.hasCustomName()) {
			nbt.setString("CustomeName", this.getCustomName());
		}
		
		// TODO Add Other Things to this
		
		return nbt;
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
		
		this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, stacks);
		
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
		if (this.stacks.get(0).isEmpty()) {
			return false;
		} else{
			ItemStack stack = OreCrusherRecipes.instance().getReslut(this.stacks.get(0));
			if (stack.isEmpty()) return false;
			if (this.stacks.get(1).isEmpty()) return true;
			if (this.stacks.get(2).isEmpty()) return true;
			if ((!this.stacks.get(1).isItemEqual(stack) || this.stacks.get(1).getMetadata() != stack.getMetadata()) && !(this.stacks.get(2).isItemEqual(stack) || this.stacks.get(2).getMetadata() == stack.getMetadata())) {return false;}
			if (this.stacks.get(2).isItemEqual(stack) || this.stacks.get(2).getMetadata() == stack.getMetadata()) {
				if (!this.stacks.get(2).isItemEqual(stack) || this.stacks.get(2).getMetadata() != stack.getMetadata()) return false;
				int result = this.stacks.get(2).getCount() + stack.getCount();
				return result <= this.getInventoryStackLimit() && result <= this.stacks.get(2).getMaxStackSize();
			}
			int result = this.stacks.get(1).getCount() + stack.getCount();
			return result <= this.getInventoryStackLimit() && result <= this.stacks.get(1).getMaxStackSize();
		}
	}
	
	public void crushItem() {
		if (this.canCrush()) {
			ItemStack stack = OreCrusherRecipes.instance().getReslut(this.stacks.get(0));
			
			if (this.stacks.get(1).isEmpty()) {
				this.stacks.set(1, stack.copy());
			} else if (this.stacks.get(1).getItem() == stack.getItem() && this.stacks.get(1).getMetadata() == stack.getMetadata() && !(this.stacks.get(1).getCount() == this.getInventoryStackLimit() || this.stacks.get(1).getMaxStackSize() == this.stacks.get(1).getCount())) {
				this.stacks.get(1).grow(stack.getCount());
			} else if (this.stacks.get(2).isEmpty()) {
				this.stacks.set(2, stack.copy());
			} else if (this.stacks.get(2).getItem() == stack.getItem() && this.stacks.get(2).getMetadata() == stack.getMetadata()) {
				this.stacks.get(2).grow(stack.getCount());
			}
			
			this.stacks.get(0).shrink(1);
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
//			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			
			this.addedToENet = false;
		}
	}
	
	// Energy related things
	
//	@Override
//	public int getSinkTier() {
//		return Integer.MAX_VALUE; // Allows any tier of power
//	}
//	
//	@Override
//	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing direction) {
//		return true;
//	}
//	
//	@Override
//	public double getDemandedEnergy() {
//		return Math.max(0, this.capacity - this.energyStored);
//	}
//	
//	@Override
//	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
//		energyStored += amount;
//
//		return 0;
//	}
	
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