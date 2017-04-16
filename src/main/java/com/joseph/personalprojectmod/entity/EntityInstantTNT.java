package com.joseph.personalprojectmod.entity;

import javax.annotation.Nullable;

import com.joseph.personalprojectmod.world.PPMExplosion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityInstantTNT extends Entity {
	@Nullable
	private EntityLivingBase tntPlacedBy;
	
	public EntityInstantTNT(World worldIn) {
		super(worldIn);
		this.preventEntitySpawning = true;
		this.setSize(0.98F, 0.98F);
	}
	
	public EntityInstantTNT(World worldIn, double x, double y, double z, EntityLivingBase igniter) {
		this(worldIn);
		this.setPosition(x, y, z);
		float f = (float) (Math.random() * (Math.PI * 2D));
		this.motionX = (double) (-((float) Math.sin((double) f)) * 0.02F);
		this.motionY = 0.20000000298023224D;
		this.motionZ = (double) (-((float) Math.cos((double) f)) * 0.02F);
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.tntPlacedBy = igniter;
	}
	
	protected void entityInit() {
	}
	
	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they
	 * walk on. used for spiders and wolves to prevent them from trampling crops
	 */
	protected boolean canTriggerWalking() {
		return false;
	}
	
	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate() {
		this.setDead();
		if (!this.world.isRemote) {
			this.explode();
		}
	}
	
	private void explode() {
		float f = 32.0f;
//		this.world.createExplosion(this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, f, true);
		new PPMExplosion(world, tntPlacedBy, (EntityPlayer) tntPlacedBy, posX, posY, posZ, f, false, true, true);
	}
	
	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}
	
	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}
	
	/**
	 * returns null or the entityliving it was placed or ignited by
	 */
	@Nullable
	public EntityLivingBase getTntPlacedBy() {
		return this.tntPlacedBy;
	}
	
	public float getEyeHeight() {
		return 0.0F;
	}
}