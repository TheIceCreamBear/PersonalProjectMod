package com.joseph.personalprojectmod.refrence;

public enum EnumBlocks {
	BLUE_STONE_ORE("bluStnOre", "BlueStoneOre"),
	TE_ELE_FURNACE("teEleFurnace", "BlockTEElectricFurnace"),
	TE_ELE_GENERATOR("teEleGenerator", "BlockTEElectricGenerator"),
	TE_ORE_CRUSHER("teOreCrusher", "BlockTEOreCrusher"),
	TE_POWER_BOX("tePowerBox", "BlockTEPowerBox"),
	GOLD_BRICK("goldenbrickblock", "GoldInfusedBrickBlock"),
	TODO_FORGE("todoforget", "TodoForgeBlock")
	;
	private String unlocalizedName;
	private String registryName;
	
	EnumBlocks(String unlocalizedName, String registryName) {
		this.unlocalizedName = unlocalizedName.toLowerCase();
		this.registryName = registryName.toLowerCase();
	}
	
	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
	
	public String getRegistryName() {
		return this.registryName;
	}
}