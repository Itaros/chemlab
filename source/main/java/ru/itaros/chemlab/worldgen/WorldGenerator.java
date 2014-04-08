package ru.itaros.chemlab.worldgen;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;

import org.apache.logging.log4j.Level;

import ru.itaros.chemlab.loader.BlockLoader;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {

	

	private static final float NOISEDOWNSAMPLE_HALITE = 0.5F;
	private static final float NOISEDOWNSAMPLE_ASBESTOS = 0.04F;
	
	//VoronoiGenerator voronoi;
	NoiseGeneratorPerlin perlinHalite;
	NoiseGeneratorPerlin perlinAsbestos;
	
	public WorldGenerator(){
		//TODO: move generator seed initialization to server start or something!
		//voronoi = new VoronoiGenerator(0, DistanceMethod.Minkowski);
		perlinHalite = new NoiseGeneratorPerlin(new Random(456L),5);
		perlinAsbestos = new NoiseGeneratorPerlin(new Random(2456L),1);
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		int dim = world.provider.dimensionId;
		switch(dim){
		case 0:
			generateOverworld(random,chunkX,chunkZ,world);
			break;
		}
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ,
			World world) {
		halitePass(random,chunkX,chunkZ,world);
		asbestosPass(random,chunkX,chunkZ,world);
		platinumPass(random,chunkX,chunkZ,world);
	}

	
	public static final int PLATINUMCHANCE = 5;
	private void platinumPass(Random random, int chunkX, int chunkZ, World world) {
		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;	
		
		for(int i = 0; i < PLATINUMCHANCE; i++){
			int xs = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
			int ys = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
			
			int x = xi+xs;
			int y = yi+ys;	
			
			BiomeGenBase b = world.getBiomeGenForCoords(x, y);
			if(b == BiomeGenBase.river){
				
				int maxL = HelperWorldGen.getUndergroundHeightLevel(world, x, y);
				
				HelperWorldGen.setBlockDirectly(world, stor, xs, ys, maxL ,BlockLoader.orePlatinum);
				
			}
			
		}
	}

	private void asbestosPass(Random random, int chunkX, int chunkZ, World world) {
		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;		
		
		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
				int x = xi+xs;
				int y = yi+ys;	
				
				double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_ASBESTOS, y*NOISEDOWNSAMPLE_ASBESTOS);
				//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
				if(noiseFactor>18F){
					float normalizedShift =  (((float)noiseFactor-18F)/(20F-18F));
					
					
					int level = HelperWorldGen.getUndergroundHeightLevel(world, x, y);
					int diff = (int) ((HelperWorldGen.assumeSafeHeightDifferenceToBedrock(level-1)*normalizedShift)+1);
					FMLLog.log(Level.INFO, "Diff:"+diff+" from "+level);
					
					//TODO: there is only one asbestos, but we need all of them
					for(int z = HelperWorldGen.ASSUMED_BEDROCK_LEVEL;z<diff;z++){
						HelperWorldGen.setBlockDirectly(world, stor, xs, ys, z ,BlockLoader.asbestos_serpentite);
					}
					
					
				}
				
			}
		}
		
		
		
	}


	
	private void halitePass(Random random, int chunkX, int chunkZ, World world) {
		long seed = world.getSeed();
		
		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
		
		
		
		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;
		
		int z = 5;
		
		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
				int x = xi+xs;
				int y = yi+ys;
				
				BiomeGenBase b = world.getBiomeGenForCoords(x, y);
				if(b == BiomeGenBase.desert | b == BiomeGenBase.desertHills | b == BiomeGenBase.beach){
					//if(b instanceof BiomeGenDesert || b.biomeID==BiomeGenBase.desert.biomeID+128){
					//Getting factor
					//double noiseFactor = voronoi.noise((float)x, (float)y,NOISEDOWNSAMPLE);
					double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_HALITE, y*NOISEDOWNSAMPLE_HALITE)/20F;
					//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
					//Clamping
					if(noiseFactor>0.4F){
						//int d = 1;
						//if(noiseFactor<0F){
						int d = 1+(int) Math.abs((noiseFactor)*(4));
						//}
						//world.setBlock(x, z, y, BlockLoader.oreHalite);
						//int index = getIndex(x,y,z);

						
						z = world.getHeightValue(x, y)-(4+2);
						
						for(int di = 0; di<d;di++){
							int rz = z + di;
							
							HelperWorldGen.setBlockDirectly(world, stor, xs, ys, rz,BlockLoader.oreHalite);
						}
					}
					
				}
				
				
			}
		}
		
		
	}



	
//	-Ocean
//	-Plains
//	-Desert
//	-Extreme Hills
//	-Forest
//	-Taiga
//	-Swampland
//	-River
//	-Hell        // This is the biome for the Nether
//	-Sky         // This is the biome for the End
//	-FrozenOcean
//	-FrozenRiver
//	-Ice Plains
//	-Ice Mountains
//	-MushroomIsland
//	-MushroomIslandShore
//	-Beach
//	-DesertHills
//	-ForestHills
//	-TaigaHills
//	-Extreme Hills Edge
//	-Jungle
//	-JungleHills	
	
	
}
