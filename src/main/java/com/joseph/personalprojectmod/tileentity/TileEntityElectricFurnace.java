package com.joseph.personalprojectmod.tileentity;

//import ic2.api.energy.event.EnergyTileLoadEvent;
//import ic2.api.energy.event.EnergyTileUnloadEvent;
//import ic2.api.energy.tile.IEnergyEmitter;
//import ic2.api.energy.tile.IEnergySink;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityElectricFurnace extends TileEntity implements ITickable, IInventory/*, IEnergySink*/  {
	private NonNullList<ItemStack> furnactItemStacks = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	private String customName;
	
	private int cookTimeOne;
	private int totalCookTimeOne;
	private int cookTimeTwo;
	private int totalCookTimeTwo;
	
	// Power Stuff
	private boolean addedToENet = false;
	
	private double energyStored;
	private double capacity;
	private int tier = Integer.MAX_VALUE;
	
	// End Power Stuff
	
	public TileEntityElectricFurnace() {
		this.capacity = 24000;
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
			
			if (!this.addedToENet) {
//				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				this.addedToENet = true;
			}
			
			// First slot
			if (this.canSmelt(0)) {
//				if (this.useEnergy(4)) {
					this.cookTimeOne++;
					
					if (this.cookTimeOne == this.totalCookTimeOne) {
						this.cookTimeOne = 0;
						this.totalCookTimeOne = this.getCookTime(this.furnactItemStacks.get(0));
						this.smeltItem(0);
						isDirty = true;
					}
//				}
			} else {
				this.cookTimeOne = 0;
			}
			
			// Second Slot
			if (this.canSmelt(1)) {
//				if (this.useEnergy(4)) {					
					this.cookTimeTwo++;
					
					if (this.cookTimeTwo == this.totalCookTimeTwo) {
						this.cookTimeTwo = 0;
						this.totalCookTimeTwo = this.getCookTime(this.furnactItemStacks.get(1));
						this.smeltItem(1);
						isDirty = true;
					}
//				}
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
	public ITextComponent getDisplayName() {
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}
	
	@Override
	public boolean isEmpty() {
		for (ItemStack iStack : furnactItemStacks) {
			if (!iStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getSizeInventory() {
		return 4;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
//		if (index < 0 || index >= this.getSizeInventory())
//	        return null;
//	    return this.inventory[index];
	    return (ItemStack)this.furnactItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(furnactItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(furnactItemStacks, index);
	}
	
	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemStack = (ItemStack)this.furnactItemStacks.get(index);
		boolean flag = stack != null && stack.isItemEqual(itemStack) && ItemStack.areItemStackTagsEqual(stack, itemStack);
        this.furnactItemStacks.set(index, stack);

        if (stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
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
		return index == 2 ? false : index == 3 ? false : true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return this.cookTimeOne;
			case 1: return this.totalCookTimeOne;
			case 2: return this.cookTimeTwo;
			case 3: return this.totalCookTimeTwo;
			case 4: return (int) this.energyStored;
			case 5: return (int) this.capacity;
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
			case 4: this.energyStored = value; break;
			case 5: this.capacity = value; break;
		}
	}

	@Override
	public int getFieldCount() {
		return 6;
	}

	@Override
	public void clear() {
		this.furnactItemStacks.clear();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setInteger("CookTimeOne", this.cookTimeOne);
		nbt.setInteger("TotalCookTimeOne", this.totalCookTimeOne);
		nbt.setInteger("CookTimeTwo", this.cookTimeTwo);
		nbt.setInteger("TotalCookTimeTwo", this.totalCookTimeTwo);
		
		// Energy
		nbt.setDouble("EnergyStored", this.energyStored);
		nbt.setDouble("Capacity", this.capacity);
		// End Energy

		ItemStackHelper.saveAllItems(nbt, this.furnactItemStacks);
		
		if (this.hasCustomName()) {
			nbt.setString("CustomeName", this.getCustomName());
		}
		
		// TODO Add Other Things to this
		
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.cookTimeOne = nbt.getInteger("CookTimeOne");
		this.totalCookTimeOne = nbt.getInteger("TotalCookTimeOne");
		this.cookTimeTwo = nbt.getInteger("CookTimeTwo");
		this.totalCookTimeTwo = nbt.getInteger("TotalCookTimeTwo");
		
		// Energy
		this.energyStored = nbt.getDouble("EnergyStored");
		this.capacity = nbt.getDouble("Capacity");
		// End Energy
		
		this.furnactItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, furnactItemStacks);
		
		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomeName"));
		}
		
		// TODO Add Other Things to this
	}
	
	public int getCookTime(ItemStack stack) {
		if (stack.isEmpty()) {
			return 0;
		} else {
			Item item = stack.getItem();
			// TODO stuff
		}
		return 100;
	}
	
	private boolean canSmelt(int slot) {
		if (this.furnactItemStacks.get(slot).isEmpty()) {
			return false;
		} else {
			ItemStack stack = FurnaceRecipes.instance().getSmeltingResult(this.furnactItemStacks.get(slot));
			if (stack.isEmpty()) {
				return false;
			} else  {
				ItemStack stack1 = furnactItemStacks.get(slot + 2);
				if (stack1.isEmpty()) return true;
				if (!stack1.isItemEqual(stack)) return false;
				int result = stack1.getCount() + stack.getCount();
				return result <= this.getInventoryStackLimit() && result <= stack1.getMaxStackSize();
			}
		}
	}
	
	public void smeltItem(int slot) {
		if (slot < 0 || slot > 1) return;
		if (this.canSmelt(slot)) {
			ItemStack orig = this.furnactItemStacks.get(slot);
			ItemStack result = FurnaceRecipes.instance().getSmeltingResult(orig);
			ItemStack results = this.furnactItemStacks.get(slot + 2);
			
			if (results.isEmpty()) {
				this.furnactItemStacks.set(slot + 2, result.copy());
			} else if (results.getItem() == result.getItem()) {
				results.grow(result.getCount());
			}
			
			orig.shrink(1);
		}
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
	
	// ENERGY RELATED METHODS
	
//	@Override
//	public int getSinkTier() {
//		return Integer.MAX_VALUE; // ALLOWS ANY TIER
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