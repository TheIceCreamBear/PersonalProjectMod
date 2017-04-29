package com.joseph.personalprojectmod.energy.event;

import com.joseph.personalprojectmod.energy.prefab.IEnergyTile;

public class EnergyTileLoadEvent extends EnergyTileEvent {
	public EnergyTileLoadEvent(IEnergyTile tile) {
		super(tile);
	}
}