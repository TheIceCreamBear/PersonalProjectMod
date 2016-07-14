package com.joseph.personalprojectmod.world;

import java.util.Random;

import com.joseph.personalprojectmod.init.ModBlocks;
import com.joseph.personalprojectmod.util.LogHelper;

import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BasicWorldGen implements IWorldGenerator {
	private WorldGenMinable genBlueStoneOre; // BlueStoneOre (Overworld)
	
	public BasicWorldGen() {
		this.genBlueStoneOre = new WorldGenMinable(ModBlocks.blueStoneOre.getDefaultState(), 8);
	}
	
	private void runGeneratorForMinable(WorldGenMinable generator, World world, Random rand, int chunkX, int chunkZ, int chancesToSpawn, int minHeight, int maxHeight) {
		if (minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) {
			LogHelper.fatal("Illegal Height Arguments! Fix Required!");
			throw new IllegalArgumentException("Illegal Height Arguments");
		}
		
		int x = chunkX << 4;
		int z = chunkZ << 4;
		
		for(int i = 0; i < chancesToSpawn; i++) {
			int randPosX = x + rand.nextInt(16);
			int randPosY = rand.nextInt(76) + 32;
			int randPosZ = z + rand.nextInt(16);
			generator.generate(world, rand, new BlockPos(randPosX, randPosY, randPosZ));
		}
	}
		
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		switch (world.provider.getDimensionId()) {
		case 0: { // Overworld
			this.runGeneratorForMinable(genBlueStoneOre, world, random, chunkX, chunkZ, 20, 0, 70);
			break;
		}
		case -1: { // Nether
			break;
		}
		case 1: { // The End
			break;
		}
		}
	}
}