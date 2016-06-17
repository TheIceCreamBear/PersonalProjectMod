package com.joseph.personalprojectmod;

import com.joseph.personalprojectmod.client.render.BlockRenderRegister;
import com.joseph.personalprojectmod.client.render.ItemRenderRegister;
import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.init.ModCrafting;
import com.joseph.personalprojectmod.init.ModItems;
import com.joseph.personalprojectmod.proxy.IProxy;
import com.joseph.personalprojectmod.refrence.Refrence;
import com.joseph.personalprojectmod.util.LogHelper;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 
 * @author Joseph Terribile
 * @version 1.8.9-1.0.0
 */
@Mod(modid = Refrence.MOD_ID, name = Refrence.MOD_NAME, version = Refrence.VERSION)
public class PersonalProject {
	
	@Mod.Instance
	public static PersonalProject instance;
	
	@SidedProxy(clientSide = Refrence.CLIENT_PROXY, serverSide = Refrence.SERVER_PROXY)
	public static IProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModItems.createItems();
		ModBlocks.createBlocks();
		
		LogHelper.info("Pre Init Complete");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ItemRenderRegister.registerItemRender();
		BlockRenderRegister.registerBlockRender();
		
		ModCrafting.initCrafting();
		
		LogHelper.info("Init Complete");
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		LogHelper.info("Post Init Complete");
	}
}