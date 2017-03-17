package com.joseph.personalprojectmod;

import com.joseph.personalprojectmod.handlers.ConfigurationHandler;
import com.joseph.personalprojectmod.handlers.GuiHandler;
import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.init.ModCrafting;
import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.init.ModTileEntities;
import com.joseph.personalprojectmod.proxy.IProxy;
import com.joseph.personalprojectmod.recipie.OreCrusherRecipes;
import com.joseph.personalprojectmod.refrence.Refrence;
import com.joseph.personalprojectmod.util.LogHelper;
import com.joseph.personalprojectmod.util.OreDictLocalReg;
import com.joseph.personalprojectmod.world.BasicWorldGen;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

// TODO s, turets that shoot stuff, entities of some kind, sycle, compressed furnace

/**
 * 
 * @author Joseph Terribile
 * @version 1.11.2-1.0.0
 */
@Mod(modid = Refrence.MOD_ID, name = Refrence.MOD_NAME, version = Refrence.VERSION, guiFactory = Refrence.GUI_FACTORY_CLASS)
public class PersonalProject {
	
	@Mod.Instance
	public static PersonalProject instance;
	
	@SidedProxy(clientSide = Refrence.CLIENT_PROXY, serverSide = Refrence.SERVER_PROXY)
	public static IProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigurationHandler.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
		
		ModItems.init();
		ModItems.register();
		ModBlocks.init();
		ModBlocks.register();
		
		ModTileEntities.createTileEntities();
		
		proxy.preInit();
		
		LogHelper.info("Pre Init Complete");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		proxy.init();
		
		OreDictLocalReg.registerAllOreDict();
		
		ModCrafting.initCrafting();
		OreCrusherRecipes.instance();
		
		GameRegistry.registerWorldGenerator(new BasicWorldGen(), 0);
		
		LogHelper.info("Init Complete");
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		LogHelper.info("Post Init Complete");
	}
}