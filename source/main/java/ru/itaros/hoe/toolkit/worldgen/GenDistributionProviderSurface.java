package ru.itaros.hoe.toolkit.worldgen;

import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class GenDistributionProviderSurface extends GenDistributionProvider {

	protected float thickness;
	protected boolean engageInSearch;//Start search up to first possible block
	
	public GenDistributionProviderSurface(int depthFromHeight,
			 float noiseDownsamplingFactor, float thickness, boolean engageInSearch) {
		super(depthFromHeight, 10, noiseDownsamplingFactor);
		this.thickness=thickness;
		this.engageInSearch=engageInSearch;
	}

	@Override
	public boolean distributeVertically(WorldGenOverlay overlay, World world, Chunk chunk,
			ExtendedBlockStorage[] stor, double noiseFactor, int localx,
			int localy) {
		
		float normalizedShift =  (((float)noiseFactor-overlay.threshold)/(NOISECAP-overlay.threshold));
		if(normalizedShift>1F){normalizedShift=1F;}
		
		int diff = (int) ((normalizedShift*thickness)+1);
		int level = chunk.getHeightValue(localx, localy)-depthFromHeight-diff;
		
		boolean isSuccesfull=false;
		if(level-diff<=0){level=diff+1;}//anticrash
		//if(level+diff>=256){diff=
		int search=0;
		for(int z = level;z<=level+diff;z++){
			if(overlay.cutoff(localx,localy,z)){continue;}
			boolean set;
			if(engageInSearch){
				set=true;
				while(!(validateBiome(chunk,localx,localy) && overlay.validateBlock(chunk.getBlock(localx, z-search,localy)))){
					search++;
					if(search>10){set=false;break;}
					if(z-search<0){set=false;break;}
					set=true;
				}
			}else{
				set = validateBiome(chunk,localx,localy) && overlay.validateBlock(chunk.getBlock(localx, z,localy));
			}
			
			if(set){
				overlay.setBlock(world,stor,localx,localy,z-search);
				isSuccesfull=true;
			}
		}
		return isSuccesfull;

	}

}
