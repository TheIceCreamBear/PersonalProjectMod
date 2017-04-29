package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.energy.EnergyStorage;
import com.joseph.personalprojectmod.energy.prefab.IEnergyProvider;
import com.joseph.personalprojectmod.energy.prefab.IEnergyReceiver;

//import ic2.api.energy.EnergyNet;
//import ic2.api.energy.tile.IEnergyAcceptor;
//import ic2.api.energy.tile.IEnergyEmitter;
//import ic2.api.energy.tile.IEnergySink;
//import ic2.api.energy.tile.IEnergySource;
//import ic2.api.info.Info;
//import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityPowerBox extends TileEntity  implements ITickable, IInventory, IEnergyProvider, IEnergyReceiver {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private EnergyStorage storage;
	private String customName;
	
	public TileEntityPowerBox() {
		this.storage = new EnergyStorage(1000000, 200);
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
		if (this.world.isRemote) {
			
		}
		
		// Server
		if (!this.world.isRemote) {
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
		return 2;
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

	    if (stack.getCount() > this.getInventoryStackLimit())
	        stack.setCount(this.getInventoryStackLimit());
	        
	    this.stacks.set(index, stack);
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
		return true; // TODO
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return this.storage.getEnergyStored();
			case 1: return this.storage.getMaxEnergyStored();
			default: return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.storage.setEnergyStored(value); break;
			case 1: this.storage.setCapacity(value); break;
		}
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.storage.writeToNBT(nbt);
		
		ItemStackHelper.saveAllItems(nbt, stacks);
		
		if (this.hasCustomName()) {
			nbt.setString("CustomeName", this.getCustomName());
		}
		
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.storage.readFromNBT(nbt);
		
		this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, stacks);
		
		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomeName"));
		}
	}
	
	@Override
	public int getEnergyStored(EnumFacing side) {
		return this.storage.getEnergyStored();
	}
	
	@Override
	public int getMaxEnergyStorable(EnumFacing side) {
		return this.storage.getMaxEnergyStored();
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing side) {
		return true;
	}
	
	@Override
	public int reciveEnergy(EnumFacing side, int maxReceive, boolean simulate) {
		// TODO Side Based Stuff
		return this.storage.receiveEnergy(maxReceive, simulate);
	}
	
	@Override
	public int extractEnergy(EnumFacing side, int maxExtract, boolean simulate) {
		// TODO Side Based Stuff
		return this.storage.extractEnergy(maxExtract, simulate);
	}
	
	// IEnergySink

//	@Override
//	public boolean acceptsEnergyFrom(IEnergyEmitter emitter, EnumFacing side) {
//		if (!((this.world.getBlockState(this.pos)).getBlock() instanceof BlockTEPowerBox)) return false;
//		EnumFacing direction = this.world.getBlockState(this.pos).getValue(BlockTEPowerBox.FACING);
//		if (side == direction) {
//			return false;
//		}
//		return true;
//	}
//	// IEnergySource
//	@Override
//	public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
//		if (!((this.world.getBlockState(this.pos)).getBlock() instanceof BlockTEPowerBox)) return false;
//		EnumFacing direction = this.world.getBlockState(this.pos).getValue(BlockTEPowerBox.FACING);
//		if (side != direction) {
//			return false;
//		}
//		return true;
//	}
	
	private boolean charge(ItemStack stack) {
		return false;
	}
	
	private boolean discharge(ItemStack stack, int limit) {
		return false;
	}
}