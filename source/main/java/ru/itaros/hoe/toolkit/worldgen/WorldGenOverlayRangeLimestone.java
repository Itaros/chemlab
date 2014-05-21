package ru.itaros.hoe.toolkit.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.chunk.Chunk;

public class WorldGenOverlayRangeLimestone extends WorldGenOverlayRange {

	public WorldGenOverlayRangeLimestone(String overlayName, Block overwrite,
			Block target, float lowerthreshold, float upperthreshold,
			float downsample, long seed) {
		super(overlayName, overwrite, target, lowerthreshold, upperthreshold,
				downsample, seed);
	}

	@Override
	int getHeightDiff(Chunk chunk, int level, float normalizedShift, int globalx, int globaly) {
		return chunk.getHeightValue(globalx, globaly);
	}
	
	
	
	

}
