package com.joseph.personalprojectmod.refrence;

public enum EnumItems {
	BLUE_STONE_INGOT("blueStoneIngot", "ItemBlueStoneIngot"),
	BLUE_STONE_DUST("blueStoneDust", "ItemBlueStoneDust"),
	BLUE_APPLE("blueApple", "ItemBlueApple"),
	BLUE_STONE_PICKAXE("blueStonePickaxe", "ItemBlueStonePickaxe"),
	BLUE_STONE_AXE("blueStoneAxe", "ItemBlueStoneAxe"),
	BLUE_STONE_HOE("blueStoneHoe", "ItemBlueStoneHoe"),
	BLUE_STONE_SPADE("blueStoneSpade", "ItemBlueStoneSpade"),
	BLUE_STONE_SWORD("blueStoneSword", "ItemBlueStoneSword"),
	BLUE_STONE_HELMET("blueStoneHelmet", "ItemBlueStoneHelmet"),
	BLUE_STONE_CHEST("blueStoneChestplate", "ItemBlueStoneChestplate"),
	BLUE_STONE_LEGS("blueStoneLeggings", "ItemBlueStoneLeggings"),
	BLUE_STONE_BOOTS("blueStoneBoots", "ItemBlueStoneBoots")
	;
	
	private String unlocalizedName;
	private String registryName;
	
	EnumItems(String unlocalizedName, String registryName) {
		this.unlocalizedName = unlocalizedName;
		this.registryName = registryName;
	}
	
	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}
	
	public String getRegistryName() {
		return this.registryName;
	}
}