package ru.itaros.hoe.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class GenDistributionProvider {

	
	protected static final float NOISECAP = 21;
	
	protected int depthFromHeight;
	protected int depthFromBedrock;
	
	protected float noiseDownsamplingFactor;
	
	protected BiomeGenBase optional_biome;

	public GenDistributionProvider(int depthFromHeight, int depthFromBedrock,
			float noiseDownsamplingFactor) {
		super();
		this.depthFromHeight = depthFromHeight;
		this.depthFromBedrock = depthFromBedrock;
		this.noiseDownsamplingFactor = noiseDownsamplingFactor;
	}

	public int getDepthFromHeight() {
		return depthFromHeight;
	}

	public int getDepthFromBedrock() {
		return depthFromBedrock;
	}

	public float getNoiseDownsamplingFactor() {
		return noiseDownsamplingFactor;
	}
	
	
	public GenDistributionProvider setOptionalBiomeRestriction(BiomeGenBase optional_biome){
		this.optional_biome=optional_biome;
		return this;
	}
	
	public boolean distributeHorizontally(WorldGenOverlay overlay, World world, Chunk chunk,
			ExtendedBlockStorage[] stor, int chunkx,
			int chunky){
		boolean isSucceded=false;

		int chunkedx = chunkx*HelperWorldGen.CHUNKSIZE;
		int chunkedy = chunky*HelperWorldGen.CHUNKSIZE;		
		
		for(int localx = 0; localx<HelperWorldGen.CHUNKSIZE;localx++){
			for(int localy = 0; localy<HelperWorldGen.CHUNKSIZE;localy++){
				int globalx = localx+chunkedx;
				int globaly = localy+chunkedy;
				double noiseFactor = overlay.getNoiseValueFor(globalx, globaly);
				if(distributeVertically(overlay,world,chunk,stor,noiseFactor,localx,localy)){
					isSucceded=true;
				}
			}
		}
		
		return isSucceded;		
		
		
	}
	
	public boolean distributeVertically(WorldGenOverlay overlay, World world, Chunk chunk,
			ExtendedBlockStorage[] stor, double noiseFactor, int localx,
			int localy) {
		if(validateNoiseThreshold(overlay, world, chunk, stor, noiseFactor, localy, localy)){
			float normalizedShift =  (((float)noiseFactor-overlay.threshold)/(NOISECAP-overlay.threshold));
			if(normalizedShift>1F){normalizedShift=1F;}
			
			int level = HelperWorldGen.getUndergroundHeightLevel(chunk, localx, localy);
			int diff = overlay.getHeightDiff(chunk, level, normalizedShift, localx, localy)	;
			boolean isSuccesfull=false;
			for(int z = HelperWorldGen.ASSUMED_BEDROCK_LEVEL;z<diff;z++){
				if(overlay.cutoff(localx,localy,z)){continue;}
				if(validateBiome(chunk,localx,localy) && overlay.validateBlock(chunk.getBlock(localx, z,localy))){
					overlay.setBlock(world,stor,localx,localy,z);
					isSuccesfull=true;
				}
			}
			return isSuccesfull;
		}else{
			return false;
		}
	}

	
	
	
	protected boolean validateBiome(Chunk chunk, int localx, int localy) {
		if(optional_biome==null){return true;}
		int biomeId = optional_biome.biomeID;
		byte[] bior = chunk.getBiomeArray();
		byte bid = bior[(localy*16)+localx];
		return bid==biomeId;
	}

	private boolean validateNoiseThreshold(WorldGenOverlay overlay, World world, Chunk chunk,
			ExtendedBlockStorage[] stor, double noiseFactor, int localx,
			int localy) {
		return overlay.validatePlacement(world, chunk, stor, noiseFactor, localx, localy);
	}
	
	
	
	
}
