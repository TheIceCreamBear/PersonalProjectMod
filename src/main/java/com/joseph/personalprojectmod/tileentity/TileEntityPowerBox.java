package com.joseph.personalprojectmod.tileentity;

//import ic2.api.energy.EnergyNet;
//import ic2.api.energy.event.EnergyTileLoadEvent;
//import ic2.api.energy.event.EnergyTileUnloadEvent;
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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityPowerBox extends TileEntity  implements ITickable, IInventory/*, IEnergySink, IEnergySource */ {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
	private String customName;
	
	// Energy Stuff
	private boolean addedToENet = false;
	
	private double energyStored;
	private double capacity;
	private double power;
	
	private int tier = 2;
	// End Energy
	
	public TileEntityPowerBox() {
//		this.power = EnergyNet.instance.getPowerFromTier(this.tier);
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
		if (this.world.isRemote) {
			
		}
		
		// Server
		if (!this.world.isRemote) {
			if (!this.addedToENet) {
//				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
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
		this.stacks.clear();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setDouble("EnergyStored", this.energyStored);
		nbt.setDouble("Capacity", this.capacity);
		
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
		
		this.energyStored = nbt.getDouble("EnergyStored");
		this.capacity = nbt.getDouble("Capacity");
		
		this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, stacks);
		
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
//			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			
			this.addedToENet = false;
		}
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
//	
//	@Override
//	public double getDemandedEnergy() {
//		return Math.max(0, this.capacity - this.energyStored);
//	}
//	
//	@Override
//	public double injectEnergy(EnumFacing directionFrom, double amount, double voltage) {
//		energyStored += amount;
//		return 0;
//	}
//	
//	@Override
//	public int getSinkTier() {
//		return Integer.MAX_VALUE;
//	}
//	
//	// End IEnergySink
//	
//	
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
//
//	@Override
//	public double getOfferedEnergy() {
//		return Math.min(this.energyStored, this.power);
//	}
//
//	@Override
//	public void drawEnergy(double amount) {
//		this.energyStored -= amount;
//	}
//
//	@Override
//	public int getSourceTier() {
//		return this.tier;
//	}
	
	
	// End IEnergySource
	
	private boolean charge(ItemStack stack) {
//		if (stack == null || !Info.isIc2Available()) return false;

//		double amount = ElectricItem.manager.charge(stack, energyStored, tier, false, false);

//		energyStored -= amount;

//		return amount > 0;
		return false;
	}
	
	private boolean discharge(ItemStack stack, int limit) {
//		if (stack == null || !Info.isIc2Available()) return false;

		double amount = capacity - energyStored;
		if (amount <= 0) return false;

		if (limit > 0 && limit < amount) amount = limit;

//		amount = ElectricItem.manager.discharge(stack, amount, tier, limit > 0, true, false);

		energyStored += amount;

		return amount > 0;
	}
}