package com.joseph.personalprojectmod.world;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class PPMExplosion extends Explosion {
	/** The type of Flan's Mod weapon that caused this explosion */
	private double x, y, z;
	private float radius;
	private World world;
	private Entity explosive;
	private EntityPlayer detonator;
	private List<BlockPos> affectedBlockPositions;
	private final Vec3d position;
	/** whether or not the explosion sets fire to blocks around it */
	private final boolean isFlaming;
	/** whether or not this explosion spawns smoke particles */
	private final boolean isSmoking;
	private final Random explosionRNG;
	private final Map playerMap;
	private boolean breaksBlocks;
	private PrintWriter pw;
	
	public PPMExplosion(World world, Entity entity, EntityPlayer detonator, double x, double y, double z, float radius, boolean flaming, boolean smoking, boolean breaksBlocks) {
		super(world, entity, x, y, z, radius, flaming, smoking);
		this.explosionRNG = new Random();
		this.affectedBlockPositions = Lists.newArrayList();
		this.playerMap = Maps.newHashMap();
		this.x = x;
		this.y = y;
		this.z = z;
		this.radius = radius;
		this.world = world;
		this.explosive = entity;
		this.detonator = detonator;
		this.affectedBlockPositions = Lists.newArrayList();
		this.isFlaming = flaming;
		this.isSmoking = true;
		this.breaksBlocks = breaksBlocks;
		this.position = new Vec3d(x, y, z);
		try {
			pw = new PrintWriter(new FileWriter(new File("C:/Users/Joseph/Desktop/txt.txt")), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, this)) {
			this.doExplosionA();
			for (BlockPos object : affectedBlockPositions) {
//				pw.println(object);
			}
			this.doExplosionB(true);
		}
	}
	
	/** First part of the explosion. Damage blocks and entities */
	@Override
	public void doExplosionA() {
		HashSet hashset = Sets.newHashSet();
		boolean flag = true;
		
		float r = radius * radius;		
		int i = (int) r + 1;
		
		for (int j = -i; j < i; ++j) {
			for (int k = -i; k < i; ++k) {
				for (int l = -i; l < i; ++l) {
					int d = j * j + k * k + l * l;
					// inside the sphere?
					if (d <= r) {
						BlockPos blockpos = new BlockPos(j, k, l).add(x, y, z);
						// no air blocks
						if (world.isAirBlock(blockpos)) {
							continue;
						}
						
						// explosion "strength" at the current position
						float f = this.radius * (1f - d / (r));
						IBlockState iblockstate = this.world.getBlockState(blockpos);
						
						float f2 = this.explosive != null ? this.explosive.getExplosionResistance(this, this.world, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(world, blockpos, (Entity) null, this);
						f -= (f2 + 0.3F) * 0.3F;
						
						if (f > 0.0F && (this.explosive == null || this.explosive.verifyExplosion(this, this.world, blockpos, iblockstate, f))) {
							hashset.add(blockpos);
						}
					}
				}
			}
		}
		
		this.affectedBlockPositions.addAll(hashset);
		float f3 = this.radius * 2.0F;
		int j = MathHelper.floor(this.x - (double) f3 - 1.0D);
		int k = MathHelper.floor(this.x + (double) f3 + 1.0D);
		int j1 = MathHelper.floor(this.y - (double) f3 - 1.0D);
		int l = MathHelper.floor(this.y + (double) f3 + 1.0D);
		int k1 = MathHelper.floor(this.z - (double) f3 - 1.0D);
		int i1 = MathHelper.floor(this.z + (double) f3 + 1.0D);
		List list = this.world.getEntitiesWithinAABBExcludingEntity(explosive, new AxisAlignedBB((double) j, (double) j1, (double) k1, (double) k, (double) l, (double) i1));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.world, this, list, f3);
		Vec3d vec3 = new Vec3d(x, y, z);
		
		for (int l1 = 0; l1 < list.size(); ++l1) {
			Entity entity = (Entity) list.get(l1);
			
			if (!entity.isImmuneToExplosions()) {
				double d12 = entity.getDistance(x, y, z) / (double) f3;
				
				if (d12 <= 1.0D) {
					double d5 = entity.posX - x;
					double d7 = entity.posY + (double) entity.getEyeHeight() - y;
					double d9 = entity.posZ - z;
					double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
					
					if (d13 != 0.0D) {
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = (double) this.world.getBlockDensity(vec3, entity.getEntityBoundingBox());
						double d10 = (1.0D - d12) * d14;
						entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f3 + 1.0D)));
						double d11 = d10;
						if (entity instanceof EntityLivingBase) {
							d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase) entity, d10);
						}
						entity.motionX += d5 * d11;
						entity.motionY += d7 * d11;
						entity.motionZ += d9 * d11;
						
						if (entity instanceof EntityPlayer) {
							this.playerMap.put((EntityPlayer) entity, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
						}
					}
				}
			}
		}
	}
	
	/** Second part of the explosion (sound, particles, drop spawn) */
	public void doExplosionB(boolean p_77279_1_) {
		this.world.playSound((EntityPlayer)null, this.x, this.y, this.z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
		
		if (this.isSmoking) {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D, new int[0]);
		} else {
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D, new int[0]);
		}
		
		Iterator iterator;
		BlockPos blockpos;
		
		if (isSmoking) {
			iterator = this.affectedBlockPositions.iterator();
			
			while (iterator.hasNext()) {
				blockpos = (BlockPos) iterator.next();
				Block block = this.world.getBlockState(blockpos).getBlock();
				
				if (p_77279_1_) {
					double d0 = (double) ((float) blockpos.getX() + this.world.rand.nextFloat());
					double d1 = (double) ((float) blockpos.getY() + this.world.rand.nextFloat());
					double d2 = (double) ((float) blockpos.getZ() + this.world.rand.nextFloat());
					double d3 = d0 - this.x;
					double d4 = d1 - this.y;
					double d5 = d2 - this.z;
					double d6 = (double) MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
					d3 /= d6;
					d4 /= d6;
					d5 /= d6;
					double d7 = 0.5D / (d6 / (double) this.radius + 0.1D);
					d7 *= (double) (this.world.rand.nextFloat() * this.world.rand.nextFloat() + 0.3F);
					d3 *= d7;
					d4 *= d7;
					d5 *= d7;
					this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + this.x * 1.0D) / 2.0D, (d1 + this.y * 1.0D) / 2.0D, (d2 + this.z * 1.0D) / 2.0D, d3, d4, d5, new int[0]);
					this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
				}
				
				if (block.getMaterial(null) != Material.AIR) {
//					if (block.canDropFromExplosion(this)) {
//						block.dropBlockAsItemWithChance(this.world, blockpos, this.world.getBlockState(blockpos), 1.0F / this.radius, 0);
//					}
					
					block.onBlockExploded(this.world, blockpos, this);
				}
			}
		}
		
		if (this.isFlaming) {
			iterator = this.affectedBlockPositions.iterator();
			
			while (iterator.hasNext()) {
				blockpos = (BlockPos) iterator.next();
				
				if (this.world.getBlockState(blockpos).getBlock().getMaterial(null) == Material.AIR && this.world.getBlockState(blockpos.down()).getBlock().isFullBlock(null) && this.explosionRNG.nextInt(3) == 0) {
					this.world.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
				}
			}
		}
	}
	
	@Override
	public Map getPlayerKnockbackMap() {
		return this.playerMap;
	}
	
	/**
	 * Returns either the entity that placed the explosive block, the entity
	 * that caused the explosion or null.
	 */
	@Override
	public EntityLivingBase getExplosivePlacedBy() {
		return detonator;
	}
	
	@Override
	public void clearAffectedBlockPositions() {
		this.affectedBlockPositions.clear();
	}
	
	@Override
	public List getAffectedBlockPositions() {
		return this.affectedBlockPositions;
	}
	
	@Override
	public Vec3d getPosition() {
		return this.position;
	}
}