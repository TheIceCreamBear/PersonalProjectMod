package com.joseph.personalprojectmod.energy.event;

import com.joseph.personalprojectmod.energy.prefab.IEnergyTile;

import net.minecraftforge.fml.common.eventhandler.Event;

public class EnergyTileEvent extends Event {
	private IEnergyTile tile;
	
	public EnergyTileEvent(IEnergyTile tile) {
		this.tile = tile;
	}
}