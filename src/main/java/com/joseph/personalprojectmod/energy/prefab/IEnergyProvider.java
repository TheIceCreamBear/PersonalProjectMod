package com.joseph.personalprojectmod.energy.prefab;

import net.minecraft.util.EnumFacing;

/**
 * Implement this interface on Tile Entities which should provide energy, 
 * generally storing it in one or more internal {@link IEnergyStorage} objects.
 */
public interface IEnergyProvider extends IEnergyHandler {
	/**
	 * Remove energy from an IEnergyProvider, internal distribution is left entirely to the IEnergyProvider.
	 *
	 * @param from       Orientation the energy is extracted from.
	 * @param maxExtract Maximum amount of energy to extract.
	 * @param simulate   If true, the extraction will only be simulated.
	 * @return Amount of energy that was (or would have been, if simulated) extracted.
	 */
	int extractEnergy(EnumFacing side, int maxExtract, boolean simulate);
}