package ru.itaros.hoe.toolkit.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class GenDistributionProviderScattered extends GenDistributionProvider {

	protected int perChunk;
	protected long seedShift;
	
	protected int clumpSize;
	
	protected Random determinist=new Random(0L);
	
	
	public GenDistributionProviderScattered(int depthFromHeight,
			int depthFromBedrock, float noiseDownsamplingFactor, int perChunk, long seedShift, int clumpSize) {
		super(depthFromHeight, depthFromBedrock, noiseDownsamplingFactor);
		this.perChunk=perChunk;
		this.seedShift=seedShift;
		this.clumpSize=clumpSize;
		if(clumpSize<=1){
			this.clumpSize=1;
		}
	}

	@Override
	public boolean distributeHorizontally(WorldGenOverlay overlay,
			World world, Chunk chunk, ExtendedBlockStorage[] stor, int chunkx,
			int chunky) {
		boolean isSucceded=false;

			
		
		determinist.setSeed(seedFromChunk(chunk));
		
		
		for(int i = 1; i <= perChunk; i++){
			
			int localx=(int) (determinist.nextDouble()*HelperWorldGen.CHUNKSIZE);
			int localy=(int) (determinist.nextDouble()*HelperWorldGen.CHUNKSIZE);
			
			distributeVertically(overlay, world, chunk, stor, noiseDownsamplingFactor, localx, localy);
		
		}
		
		return isSucceded;		
	}



	private long seedFromChunk(Chunk chunk) {
		int xoff = chunk.xPosition;
		int yoff = chunk.zPosition;
		
		long newseed = (xoff+yoff >> 2) & 0xA62DDE;
		
		return newseed;
	}

	@Override
	public boolean distributeVertically(WorldGenOverlay overlay,
			World world, Chunk chunk, ExtendedBlockStorage[] stor,
			double noiseFactor, int localx, int localy) {
			
		int chunkedx = chunk.xPosition*HelperWorldGen.CHUNKSIZE;
		int chunkedy = chunk.zPosition*HelperWorldGen.CHUNKSIZE;	
		

		int radious=0;
		int thread=-1;
		int sign=1;
		
		int blocksperaxis=0;
		int setblocks=0;
		
		int axis = 0;
		//0 ->
		//1 <-
		//2 ^
		//3 down
		
		boolean isSucceded=false;
		for(int i = 0; i < clumpSize; i++){
			if(sign==1){sign=-1;}else{sign=1;thread++;}
			if(thread*2>radious*8){
				axis=0;
				radious++;
				thread=0;
				setblocks=0;
				blocksperaxis=radious*8/4;
			}
			if(i!=0 & setblocks>=blocksperaxis){
				setblocks=0;
				axis++;
			}
			
			int modx;
			int mody;
			
			setblocks++;
			
			switch(axis){
			case 0:
				modx=radious;
				mody=sign*setblocks;
				break;
			case 1:
				modx=-radious;
				mody=-sign*setblocks;				
				break;
			case 2:
				modx=sign*setblocks;
				mody=-radious;				
				break;
			case 3:
				modx=-sign*setblocks;
				mody=radious;					
				break;
				default:
					return false;
			}
			
			modx+=localx;
			mody+=localy;
			
			
			
			if(modx>=16 || modx<=0){continue;}
			if(mody>=16 || mody<=0){continue;}
			
			
			int level = HelperWorldGen.getUndergroundHeightLevel(chunk, localx, localy);
			int diff = (int) ((level-depthFromBedrock)*Math.abs(overlay.getNoiseValueFor(chunkedx+localx, chunkedy+localy)/NOISECAP));
			int z = depthFromBedrock+diff;			
		
			if(validateBiome(chunk,localx,localy) && overlay.validateBlock(chunk.getBlock(modx, z,mody))){
				overlay.setBlock(world,stor,modx,mody,z);
				isSucceded=true;
			}
		}
		
		return isSucceded;
		
		
	}
	
	
	
	

}
