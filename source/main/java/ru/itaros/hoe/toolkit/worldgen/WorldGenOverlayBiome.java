package ru.itaros.hoe.toolkit.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WorldGenOverlayBiome extends WorldGenOverlay {

	protected BiomeGenBase biome;
	
	public WorldGenOverlayBiome(String overlayName, Block overwrite,
			Block target, float threshold, BiomeGenBase filterBiome, float downsample, long seed) {
		super(overlayName, overwrite, target, threshold, downsample, seed);
		this.biome=filterBiome;
	}

	@Override
	protected boolean validatePlacement(World world, Chunk chunk,
			ExtendedBlockStorage[] stor, double noiseFactor, int localx,
			int localy) {
		int xglob = chunk.xPosition*HelperWorldGen.CHUNKSIZE;
		int yglob = chunk.zPosition*HelperWorldGen.CHUNKSIZE;
		BiomeGenBase base = world.getBiomeGenForCoords(xglob+localx, yglob+localy);
		return base==biome && super.validatePlacement(world, chunk, stor, noiseFactor, localx, localy);
	}
	
	
	
	
	
}
