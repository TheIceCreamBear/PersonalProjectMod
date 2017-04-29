package com.joseph.personalprojectmod.energy.prefab;

import net.minecraft.util.EnumFacing;

/**
 * Implement this interface on Tile Entities which should handle energy, 
 * generally storing it in one or more internal {@link IEnergyStorage} objects.
 *
 */
public interface IEnergyHandler extends IEnergyConnection {
	/**
	 * Returns the amount of energy currently stored.
	 */
	int getEnergyStored(EnumFacing side);

	/**
	 * Returns the maximum amount of energy that can be stored.
	 */
	int getMaxEnergyStorable(EnumFacing side);
}