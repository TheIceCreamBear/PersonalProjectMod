package com.joseph.personalprojectmod.energy.event;

import com.joseph.personalprojectmod.energy.prefab.IEnergyTile;

public class EnergyTileUnloadEvent extends EnergyTileEvent {
	public EnergyTileUnloadEvent(IEnergyTile tile) {
		super(tile);
	}
}
