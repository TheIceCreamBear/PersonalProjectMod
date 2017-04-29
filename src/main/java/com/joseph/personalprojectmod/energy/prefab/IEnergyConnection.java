package com.joseph.personalprojectmod.energy.prefab;

import net.minecraft.util.EnumFacing;

/**
 * Implement this interface on TileEntities which should connect to energy transportation blocks. This is intended for blocks which generate energy but do not
 * accept it; otherwise just use IEnergyHandler.
 *
 */
public interface IEnergyConnection {

	/**
	 * Returns true if the TileEntity can connect on a given side.
	 */
	boolean canConnectEnergy(EnumFacing side);

}