package ru.itaros.hoe.toolkit.worldgen;


import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class WorldGenOverlayRange extends WorldGenOverlay {

	protected float upperthreshold;
	
	public WorldGenOverlayRange(String overlayName, Block overwrite,
			Block target, float lowerthreshold, float upperthreshold, float downsample, long seed) {
		super(overlayName, overwrite, target, lowerthreshold, downsample, seed);
		this.upperthreshold=upperthreshold;
	}

	@Override
	protected boolean validatePlacement(World world, Chunk chunk,
			ExtendedBlockStorage[] stor, double noiseFactor, int localx,
			int localy) {
		return (noiseFactor>threshold)&&(noiseFactor<upperthreshold);
	}
	
	
	

}
