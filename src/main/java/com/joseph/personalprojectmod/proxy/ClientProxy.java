package com.joseph.personalprojectmod.proxy;

import com.joseph.personalprojectmod.client.render.BlockRenderRegister;
import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.init.ModItems;

public class ClientProxy extends CommonProxy {
	@Override
	public void init() {
		ModItems.registerRenders();
		ModBlocks.registerRenders();
	}

	@Override
	public void preInit() {
		BlockRenderRegister.preInit();
	}
}