package com.joseph.personalprojectmod.energy;

import com.joseph.personalprojectmod.energy.prefab.IEnergyStorage;

import net.minecraft.nbt.NBTTagCompound;

public class EnergyStorage implements IEnergyStorage {
	protected int energy;
	protected int capacity;
	protected int maxExtract;
	protected int maxReceive;
	
	public EnergyStorage(int capacity) {
		this.capacity = capacity;
	}
	
	public EnergyStorage(int capacity, int maxTransfer) {
		this.capacity = capacity;
		this.maxExtract = maxTransfer;
		this.maxReceive = maxTransfer;
	}
	
	public EnergyStorage(int capacity, int maxExtract, int maxRecieve) {
		this.capacity = capacity;
		this.maxExtract = maxExtract;
		this.maxReceive = maxRecieve;
	}
	
	public EnergyStorage readFromNBT(NBTTagCompound nbt) {
		this.energy = nbt.getInteger("energy");
		
		if (this.energy > this.capacity) {
			this.energy = this.capacity;
		}
		
		return this;
	}
	
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if (this.energy < 0) {
			this.energy = 0;
		}
		nbt.setInteger("energy", this.energy);
		return nbt;
	}
	
	public EnergyStorage setCapacity(int capacity) {
		
		this.capacity = capacity;
		
		if (this.energy > capacity) {
			this.energy = capacity;
		}
		return this;
	}
	
	public EnergyStorage setMaxTransfer(int maxTransfer) {
		
		setMaxReceive(maxTransfer);
		setMaxExtract(maxTransfer);
		return this;
	}
	
	public EnergyStorage setMaxReceive(int maxReceive) {
		
		this.maxReceive = maxReceive;
		return this;
	}
	
	public EnergyStorage setMaxExtract(int maxExtract) {
		
		this.maxExtract = maxExtract;
		return this;
	}
	
	public int getMaxReceive() {
		
		return maxReceive;
	}
	
	public int getMaxExtract() {
		
		return maxExtract;
	}
	
	public void setEnergyStored(int energy) {
		this.energy = energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}

	/**
	 * This function is included to allow the containing tile to directly and efficiently modify the energy contained in the EnergyStorage. Do not rely on this externally, as not all IEnergyHandlers are guaranteed to have it.
	 */
	public void modifyEnergyStored(int energy) {
		this.energy += energy;

		if (this.energy > capacity) {
			this.energy = capacity;
		} else if (this.energy < 0) {
			this.energy = 0;
		}
	}
	
	public boolean isFull() {
		return this.energy >= this.capacity;
	}

	/* IEnergyStorage */
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {

		int energyReceived = Math.min(this.capacity - energy, Math.min(this.maxReceive, maxReceive));

		if (!simulate) {
			this.energy += energyReceived;
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {

		int energyExtracted = Math.min(this.energy, Math.min(this.maxExtract, maxExtract));

		if (!simulate) {
			this.energy -= energyExtracted;
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored() {

		return this.energy;
	}

	@Override
	public int getMaxEnergyStored() {

		return this.capacity;
	}

}