package com.joseph.personalprojectmod.handlers;

import java.io.File;

import com.joseph.personalprojectmod.reference.ConfigRef;
import com.joseph.personalprojectmod.reference.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
	public static Configuration configuration;
	
	public static void init(File configFile) {
		if (configuration == null) {
			configuration = new Configuration(configFile);
			loadConfiguration();
		}
	}
	
	@SubscribeEvent
	public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
			loadConfiguration();
		}
	}
	
	private static void loadConfiguration() {
		ConfigRef.doArmorEffect = configuration.getBoolean("doArmorEffect", Configuration.CATEGORY_GENERAL, true, "Weather or not the armor gives potion effects.");
		ConfigRef.explosionDropItem = configuration.getBoolean("explosionsDropItems", Configuration.CATEGORY_GENERAL, false, "Weather or not the explosives drop the items they explode.");
		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
}