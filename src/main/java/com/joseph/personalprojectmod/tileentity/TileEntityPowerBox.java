package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.blocks.BlockTEPowerBox;
import com.joseph.personalprojectmod.util.LogHelper;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergyAcceptor;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.info.Info;
import ic2.api.item.ElectricItem;
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

public class TileEntityPowerBox extends TileEntity  implements ITickable, IInventory, IEnergySink, IEnergySource {
	private ItemStack[] inventory;
	private String customName;
	
	// Energy Stuff
	private boolean addedToENet = false;
	
	private double energyStored;
	private double capacity;
	private double power;
	
	private int tier = 2;
	// End Energy
	
	public TileEntityPowerBox() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.power = EnergyNet.instance.getPowerFromTier(this.tier);
		this.capacity = 1000000;
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
		
		// Client
		if (this.worldObj.isRemote) {
			
		}
		
		// Server
		if (!this.worldObj.isRemote) {
//			LogHelper.info(this.energyStored + "/" + this.capacity);
			if (!this.addedToENet) {
				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				
				this.addedToENet = true;
			}
			
			if (this.charge(this.getStackInSlot(0))) {
				
			}
			if (this.discharge(this.getStackInSlot(1), 0)) {
				
			}
		}
		
		if (isDirty) {
			this.markDirty();
		}
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.personalprojectmod:power_box";
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
		return 2;
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
		ItemStack stack = this.getStackInSlot(index);
	    this.setInventorySlotContents(index, null);
	    return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index < 0 || index >= this.getSizeInventory())
	        return;

	    if (stack != null && stack.stackSize > this.getInventoryStackLimit())
	        stack.stackSize = this.getInventoryStackLimit();
	        
	    if (stack != null && stack.stackSize == 0)
	        stack = null;

	    this.inventory[index] = stack;
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
		return true; // TODO
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return (int) this.energyStored;
			case 1: return (int) this.capacity;
			default: return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.energyStored = value; break;
			case 1: this.capacity = value; break;
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
		
		nbt.setDouble("EnergyStored", this.energyStored);
		nbt.setDouble("Capacity", this.capacity);
		
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
		
		this.energyStored = nbt.getDouble("EnergyStored");
		this.capacity = nbt.getDouble("Capacity");
		
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
	
	// IEnergySink

	@Override
	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
		if (!((this.worldObj.getBlockState(this.pos)).getBlock() instanceof BlockTEPowerBox)) return false;
		EnumFacing direction = this.worldObj.getBlockState(this.pos).getValue(BlockTEPowerBox.FACING);
		if (side == direction) {
			return false;
		}
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
	
	@Override
	public int getSinkTier() {
		return Integer.MAX_VALUE;
	}
	
	// End IEnergySink
	
	
	// IEnergySource
	@Override
	public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
		if (!((this.worldObj.getBlockState(this.pos)).getBlock() instanceof BlockTEPowerBox)) return false;
		EnumFacing direction = this.worldObj.getBlockState(this.pos).getValue(BlockTEPowerBox.FACING);
		if (side != direction) {
			return false;
		}
		return true;
	}

	@Override
	public double getOfferedEnergy() {
		return Math.min(this.energyStored, this.power);
	}

	@Override
	public void drawEnergy(double amount) {
		this.energyStored -= amount;
	}

	@Override
	public int getSourceTier() {
		return this.tier;
	}
	
	
	// End IEnergySource
	
	private boolean charge(ItemStack stack) {
		if (stack == null || !Info.isIc2Available()) return false;

		double amount = ElectricItem.manager.charge(stack, energyStored, tier, false, false);

		energyStored -= amount;

		return amount > 0;
	}
	
	private boolean discharge(ItemStack stack, int limit) {
		if (stack == null || !Info.isIc2Available()) return false;

		double amount = capacity - energyStored;
		if (amount <= 0) return false;

		if (limit > 0 && limit < amount) amount = limit;

		amount = ElectricItem.manager.discharge(stack, amount, tier, limit > 0, true, false);

		energyStored += amount;

		return amount > 0;
	}
}