package com.joseph.personalprojectmod.tileentity;

import com.joseph.personalprojectmod.blocks.BlockTEElectricGenerator;
import com.joseph.personalprojectmod.energy.EnergyStorage;
import com.joseph.personalprojectmod.energy.prefab.IEnergyProvider;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.MinecraftForge;

public class TileEntityElectricGenerator extends TileEntity implements ITickable, IInventory, IEnergyProvider {
	private NonNullList<ItemStack> stacks = NonNullList.withSize(1, ItemStack.EMPTY);
	private EnergyStorage stroage;
	private String customName;
	
	// Power Generation stuff
	private int brunTime;
	private int maxBurnTime;
	// End Power Generation
	
	public TileEntityElectricGenerator() {
		this.stroage = new EnergyStorage(8000, 20);
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
			if (this.isBruning()) {
				--this.brunTime;
			}
			
			if (this.isBruning() || !this.stacks.get(0).isEmpty()) {
				if (!this.isBruning() && isItemFuel(this.stacks.get(0)) && !this.isBufferFull()) {
					this.maxBurnTime = this.brunTime = getItemBurnTime(this.stacks.get(0));
					if (this.isBruning()) {
						isDirty = true;
						if (!this.stacks.get(0).isEmpty()) {
							this.stacks.get(0).shrink(1);
							if (this.stacks.get(0).getCount() == 0) {
								this.stacks.set(0, this.stacks.get(0).getItem().getContainerItem(this.stacks.get(0))); // FOR FLUIDS
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
		for (ItemStack iStack : stacks) {
			if (!iStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int getSizeInventory() {
		return 1;
	}
	
	@Override
	public ItemStack getStackInSlot(int index) {
		return (ItemStack) this.stacks.get(index);
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
		return true;
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0: return this.brunTime;
			case 1: return this.maxBurnTime;
			case 2: return this.stroage.getEnergyStored();
			case 3: return this.stroage.getMaxEnergyStored();
			default: return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0: this.brunTime = value; break;
			case 1: this.maxBurnTime = value; break;
			case 2: this.stroage.setEnergyStored(value); break;
			case 3: this.stroage.setCapacity(value); break;
			
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
		
		this.stroage.writeToNBT(nbt);
		
		nbt.setInteger("BurnTime", this.brunTime);
		nbt.setInteger("MaxBurnTime", this.maxBurnTime);
		
		ItemStackHelper.saveAllItems(nbt, stacks);
		
		if (this.hasCustomName()) {
			nbt.setString("CustomeName", this.getCustomName());
		}
		return nbt;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.stroage.readFromNBT(nbt);
		
		this.brunTime = nbt.getInteger("BurnTime");
		this.maxBurnTime = nbt.getInteger("MaxBurnTime");
		
		this.stacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, stacks);
		
		if (nbt.hasKey("CustomName", 8)) {
			this.setCustomName(nbt.getString("CustomeName"));
		}
	}
	
	@Override
	public int getEnergyStored(EnumFacing side) {
		return this.stroage.getEnergyStored();
	}
	
	@Override
	public int getMaxEnergyStorable(EnumFacing side) {
		return this.stroage.getMaxEnergyStored();
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing side) {
		if (!((this.world.getBlockState(this.pos)).getBlock() instanceof BlockTEElectricGenerator)) return false;
		EnumFacing direction = this.world.getBlockState(this.pos).getValue(BlockTEElectricGenerator.FACING);
		if (side == direction) {
			return false;
		}
		return true;
	}
	
	@Override
	public int extractEnergy(EnumFacing side, int maxExtract, boolean simulate) {
		if (canConnectEnergy(side)) {
			return this.stroage.extractEnergy(maxExtract, simulate);
		}
		return 0;
	}
	
	public int addEnergy(int amount) {
		stroage.modifyEnergyStored(amount);
		return amount;
	}
	
	private boolean isBruning() {
		return this.brunTime > 0;
	}
	
	private boolean isBufferFull() {
		return this.stroage.isFull();
	}
	
	// STATIC METHODS
	
	/**
	 * @param stack - Item to be bruned
	 * @return The amount of ticks the generator will burn the item
	 */
	public static int getItemBurnTime(ItemStack stack) {
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
	
	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}
}