package ru.itaros.hoe.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

public class WorldGenOverlay {

	public static final float NOISECAP=20F;
	
	private String overlayName;
	
	protected float threshold, downsample;
	private Block overwrite, target;
	
	NoiseGeneratorPerlin noise;
	
	public String getName(){
		return overlayName;
	}
	
	public WorldGenOverlay(String overlayName, Block overwrite, Block target, float threshold, float downsample, long seed){
		this.overlayName=overlayName;
		
		this.threshold=threshold;
		this.downsample=downsample;
		
		this.overwrite=overwrite;
		this.target=target;
		
		noise = new NoiseGeneratorPerlin(new Random(seed),5);
	}
	
	public boolean pass(Random random, int chunkX, int chunkZ, World world){
		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;	
		
		Chunk chunk = world.getChunkFromChunkCoords(chunkX, chunkZ);
		if(disProvider==null){
			return modifyChunk(world, chunk, xi, yi);
		}else{
			ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunk);
			return disProvider.distributeHorizontally(this, world, chunk, stor, chunkX, chunkZ);
		}
	}
		
	
	protected boolean modifyChunk(World world, Chunk chunk, int chunkedx, int chunkedy){
		boolean isSucceded=false;
		
		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunk);
		for(int localx = 0; localx<HelperWorldGen.CHUNKSIZE;localx++){
			for(int localy = 0; localy<HelperWorldGen.CHUNKSIZE;localy++){
				int globalx = localx+chunkedx;
				int globaly = localy+chunkedy;
				double noiseFactor = getNoiseValueFor(globalx, globaly);
				if(attemptToPut(world, chunk, stor, noiseFactor,localx,localy)){
					isSucceded=true;
				}
			}
		}
		
		return isSucceded;

	}
	
	
	protected double getNoiseValueFor(int globalx, int globaly){
		return noise.func_151601_a(globalx*downsample, globaly*downsample);
	}
	
	public double getNoiseValueFor(int globalx, int globaly, float overridenDownsample){
		return noise.func_151601_a(globalx*overridenDownsample, globaly*overridenDownsample);
	}	
	
	
	
	int getHeightDiff(Chunk chunk, int level, float normalizedShift, int globalx, int globaly){
		return (int) ((HelperWorldGen.assumeSafeHeightDifferenceToBedrock(level-1)*normalizedShift)+1);	
	}
	
	/*
	 * This method ensures that initial distribution is correct. 
	 */
	protected boolean attemptToPut(World world, Chunk chunk, ExtendedBlockStorage[] stor, double noiseFactor,int localx, int localy){
		if(validatePlacement(world ,chunk , stor, noiseFactor, localx, localy)){
			if(disProvider==null){
			float normalizedShift =  (((float)noiseFactor-threshold)/(NOISECAP-threshold));
			if(normalizedShift>1F){normalizedShift=1F;}
			
			int level = HelperWorldGen.getUndergroundHeightLevel(chunk, localx, localy);
			int diff = getHeightDiff(chunk, level, normalizedShift, localx, localy)	;
			boolean isSuccesfull=false;
			for(int z = HelperWorldGen.ASSUMED_BEDROCK_LEVEL;z<diff;z++){
				if(cutoff(localx,localy,z)){continue;}
				if(validateBlock(chunk.getBlock(localx, z,localy))){
					setBlock(world,stor,localx,localy,z);
					isSuccesfull=true;
				}
			}
			return isSuccesfull;
			}else{
				return disProvider.distributeVertically(this, world, chunk, stor, noiseFactor, localx, localy);
			}
		}else{
			return false;
		}
	}
	
	protected boolean validatePlacement(World world, Chunk chunk, ExtendedBlockStorage[] stor, double noiseFactor,int localx, int localy){
		return noiseFactor>threshold;
	}

	protected void setBlock(World world, ExtendedBlockStorage[] stor, int localx,int localy,int localz){
		HelperWorldGen.setBlockDirectly(world, stor, localx, localy, localz , target);
	}
	
	
	boolean cutoff(int localx, int localy, int z) {
		if(localx>16-1){return true;}
		if(localy>16-1){return true;}
		if(z>256-1){return true;}
		return false;
	}

	boolean validateBlock(Block block) {
		return block == overwrite;
	}

	
	private GenDistributionProvider disProvider;
	public void setDestributorProvider(GenDistributionProvider disProvider) {
		this.disProvider=disProvider;
	}
	
	
	
	
	
	
}
