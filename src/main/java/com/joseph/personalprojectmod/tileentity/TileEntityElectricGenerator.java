package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.blocks.BlockTEElectricGenerator;

//import ic2.api.energy.EnergyNet;
//import ic2.api.energy.event.EnergyTileLoadEvent;
//import ic2.api.energy.event.EnergyTileUnloadEvent;
//import ic2.api.energy.tile.IEnergyAcceptor;
//import ic2.api.energy.tile.IEnergySource;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityElectricGenerator extends TileEntity implements ITickable, IInventory/*, IEnergySource */ {
	private ItemStack[] inventory;
	private String customName;
	
	// Power Generation stuff
	private int brunTime;
	private int maxBurnTime;
	// End Power Generation
	
	// Energy Stuff
	private boolean addedToENet;
	
	private double energyStored;
	private double capacity;
	private double power;
	
	private int tier = 2;
	// End Energy Stuff
	
	public TileEntityElectricGenerator() {
		this.inventory = new ItemStack[this.getSizeInventory()];
		this.capacity = 8000;
//		this.power = EnergyNet.instance.getPowerFromTier(this.tier);
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
//			LogHelper.info(this.getField(0));
//			LogHelper.info(this.getField(2) + "/" + this.getField(3));
			if (!this.addedToENet) {
//				MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
				
				this.addedToENet = true;
			}
			
			if (this.isBruning()) {
				--this.brunTime;
				
			}
			
			if (this.isBruning() || this.inventory[0] != null) {
				if (!this.isBruning() && isItemFuel(this.inventory[0]) && !this.isBufferFull()) {
					this.maxBurnTime = this.brunTime = getItemBurnTime(this.inventory[0]);
					if (this.isBruning()) {
						isDirty = true;
						
						if (this.inventory[0] != null) {
//							this.inventory[0].stackSize--;
							this.inventory[0].setCount(this.inventory[0].getCount() - 1);
							
							if (this.inventory[0].getCount() == 0) {
								this.inventory[0] = this.inventory[0].getItem().getContainerItem(this.inventory[0]);
							}
						}
					}
				}
			}
			
			if (this.isBruning()) {
				this.addEnergy(20);
			}
			
		}
		
		if (isDirty) {
			this.markDirty();
		}
	}
	
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : "container.personalprojectmod:ele_generator";
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
		return false;
	}
	
	@Override
	public int getSizeInventory() { 
		return 1;
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

			if (this.getStackInSlot(index).getCount() <= count) {
				itemstack = this.getStackInSlot(index);
				this.setInventorySlotContents(index, null);
				this.markDirty();
				return itemstack;
			} else {
				itemstack = this.getStackInSlot(index).splitStack(count);

				if (this.getStackInSlot(index).getCount() <= 0) {
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

	    if (stack != null && stack.getCount() > this.getInventoryStackLimit())
	        stack.setCount(this.getInventoryStackLimit());
	        
	    if (stack != null && stack.getCount() == 0)
	        stack = null;

	    this.inventory[index] = stack;
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
		return true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return this.brunTime;
			case 1: return this.maxBurnTime;
			case 2: return (int) this.energyStored;
			case 3: return (int) this.capacity;
			default: return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.brunTime = value; break;
			case 1: this.maxBurnTime = value; break;
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
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setDouble("EnergyStored", this.energyStored);
		nbt.setDouble("Capacity", this.capacity);
		
		nbt.setInteger("BurnTime", this.brunTime);
		nbt.setInteger("MaxBurnTime", this.maxBurnTime);
		
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
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.energyStored = nbt.getDouble("EnergyStored");
		this.capacity = nbt.getDouble("Capacity");
		
		this.brunTime = nbt.getInteger("BurnTime");
		this.maxBurnTime = nbt.getInteger("MaxBurnTime");
		
		NBTTagList list = nbt.getTagList("Items", 10);
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound stackTag = list.getCompoundTagAt(i);
			int slot = stackTag.getByte("Slot") & 255;
			this.setInventorySlotContents(slot, new ItemStack(stackTag));
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
//			MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
			
			this.addedToENet = false;
		}
	}
	
	// IEnergySource
//	
//	@Override
//	public boolean emitsEnergyTo(IEnergyAcceptor receiver, EnumFacing side) {
//		if (!((this.world.getBlockState(this.pos)).getBlock() instanceof BlockTEElectricGenerator)) return false;
//		EnumFacing direction = this.world.getBlockState(this.pos).getValue(BlockTEElectricGenerator.FACING);
//		if (side == direction) {
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
//	
	// End IEnergySource
	
	public double addEnergy(double amount) {
		if (amount > capacity - energyStored) amount = capacity - energyStored;

		energyStored += amount;

		return amount;
	}
	
	private boolean isBruning() {
		return this.brunTime > 0;
	}
	
	private boolean isBufferFull() {
		return this.energyStored >= this.capacity;
	}
	
	
	// STATIC METHODS
	
	/**
	 * 
	 * @param stack - Item to be bruned
	 * @return The amount of ticks the generator will burn the item
	 */
	public static int getItemBurnTime(ItemStack stack) {
        if (stack == null) {
            return 0;
        }
        else {
            Item item = stack.getItem();

            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) {
                Block block = Block.getBlockFromItem(item);

                if (block == Blocks.WOODEN_SLAB) {
                    return 75;
                }

                if (block.getMaterial(null) == Material.WOOD) {
                    return 75;
                }

                if (block == Blocks.COAL_BLOCK) {
                    return 4000;
                }
            }

            if (item instanceof ItemTool && ((ItemTool)item).getToolMaterialName().equals("WOOD")) return 50;
            if (item instanceof ItemSword && ((ItemSword)item).getToolMaterialName().equals("WOOD")) return 50;
            if (item instanceof ItemHoe && ((ItemHoe)item).getMaterialName().equals("WOOD")) return 50;
            if (item == Items.STICK) return 25;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 25;
            if (item == Items.COAL) return 400;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
        }
    }
	
	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}
}