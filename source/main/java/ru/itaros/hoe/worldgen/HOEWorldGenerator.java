package ru.itaros.hoe.worldgen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraft.world.gen.feature.WorldGenMinable;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.hoe.HOE;
import cpw.mods.fml.common.IWorldGenerator;

public class HOEWorldGenerator implements IWorldGenerator {

	


	
	private static final int PYRITECHANCE = 16*5;//ALL
	public static final int PLATINUMCHANCE = 4;//ONLY RIVERS
	private static final int METAANTHRACITECHANCE = 6;//ALL	
	
	
	WorldGenDataIncapsulator incapsulator;
	
	WorldGenRegistry registry;
	
	private int[] allowedDims={0};
	public void setAllowedDims(int[] ii){
		allowedDims=ii;
	}
	
	public HOEWorldGenerator(){
		
		incapsulator = new WorldGenDataIncapsulator();
		
		registry=HOE.getInstance().getRegistryWorldGen();
		
		
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		
		int dim = world.provider.dimensionId;
		boolean isPassable=false;
		for(int i : allowedDims){
			if(i==dim){isPassable=true;break;}
		}
		if(isPassable){
			generateOverworld(random,chunkX,chunkZ,world);
		}
			
	}

	private void generateOverworld(Random random, int chunkX, int chunkZ,
			World world) {
		incapsulator.set(random, chunkX, chunkZ, world);
		
		registry.applyAllOverlays(incapsulator);
		
		//+halitePass(random,chunkX,chunkZ,world);
		//+asbestosPass(random,chunkX,chunkZ,world);
		//?platinumPass(random,chunkX,chunkZ,world);
		//+pyritePass(random,chunkX,chunkZ,world);
		//+MetaAnthracitePass(random,chunkX,chunkZ,world);
		//+LimestonePass(random,chunkX,chunkZ,world);
		//+HematitePass(random,chunkX,chunkZ,world);
		//+PericlasePass(random,chunkX,chunkZ,world);
		
		
		
		
	}

//	
//	private void PericlasePass(Random random, int chunkX, int chunkZ,
//			World world) {
//		
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;		
//		
//		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
//			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
//				int x = xi+xs;
//				int y = yi+ys;	
//				
//				double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_PERICLASE, y*NOISEDOWNSAMPLE_PERICLASE);
//				//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
//				if(noiseFactor>19F){
//					float normalizedShift =  (((float)noiseFactor-19F)/(20F-19F));
//					
//					
//					int level = HelperWorldGen.getUndergroundHeightLevel(world, x, y)-10;
//					int diff = (int) ((HelperWorldGen.assumeSafeHeightDifferenceToBedrock(level-1)*normalizedShift)+1);
//					
//					
//					for(int z = HelperWorldGen.ASSUMED_BEDROCK_LEVEL;z<diff;z++){
//						if(world.getBlock(x, z, y)==Blocks.stone){//TODO: Ask chunk, not world. Or use helper to access stor \o/
//							HelperWorldGen.setBlockDirectly(world, stor, xs, ys, z ,BlockLoader.orePericlase);
//						}
//					}
//					
//					
//				}
//				
//			}
//		}		
//	}
//
//	private void HematitePass(Random random, int chunkX, int chunkZ, World world) {
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;		
//		
//		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
//			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
//				int x = xi+xs;
//				int y = yi+ys;	
//				
//				double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_HEMATITE, y*NOISEDOWNSAMPLE_HEMATITE);
//				//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
//				if(noiseFactor>19F){
//					float normalizedShift =  (((float)noiseFactor-19F)/(20F-19F));
//					
//					
//					int level = HelperWorldGen.getUndergroundHeightLevel(world, x, y)-10;
//					int diff = (int) ((HelperWorldGen.assumeSafeHeightDifferenceToBedrock(level-1)*normalizedShift)+1);
//					
//					
//					for(int z = HelperWorldGen.ASSUMED_BEDROCK_LEVEL;z<diff;z++){
//						if(world.getBlock(x, z, y)==Blocks.stone){//TODO: Ask chunk, not world. Or use helper to access stor \o/
//							HelperWorldGen.setBlockDirectly(world, stor, xs, ys, z ,BlockLoader.oreHematite);
//						}
//					}
//					
//					
//				}
//				
//			}
//		}
//	}
//
//	private void LimestonePass(Random random, int chunkX, int chunkZ,
//			World world) {
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;
//		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
//			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
//				int x = xi+xs;
//				int y = yi+ys;	
//				
//				double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_ASBESTOS, y*NOISEDOWNSAMPLE_ASBESTOS);
//				//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
//				if(noiseFactor>5F & noiseFactor<8F){
//					//float normalizedShift =  (((float)noiseFactor-18F)/(20F-18F));
//					
//					
//					int Zmin = 50;
//					int Zmax = world.getHeightValue(x, y);
//					
//					for(int z = Zmin; z <= Zmax; z++){
//						if(world.getBlock(x, z, y)==Blocks.stone){//TODO: Ask chunk, not world. Or use helper to access stor \o/
//							HelperWorldGen.setBlockDirectly(world, stor, xs, ys, z ,BlockLoader.oreLimestone);
//						}
//					}
//					
//					
//				}
//				
//			}
//		}		
//		
//	}
//
//
//	WorldGenMinable metaAnthracite_generator = new WorldGenMinable(BlockLoader.oreMetaAnthracite, 15);
//	
//	private void MetaAnthracitePass(Random random, int chunkX, int chunkZ,
//			World world) {
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;		
//		for(int i = 0; i < METAANTHRACITECHANCE; i++){
//			int xs = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
//			int ys = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
//			
//			
//			int x = xi+xs;
//			int y = yi+ys;	
//			int z = (int) (random.nextFloat()*(float)HelperWorldGen.assumeSafeHeightDifferenceToBedrock(HelperWorldGen.getUndergroundHeightLevel(world, x, y)));		
//		
//			metaAnthracite_generator.generate(world, random, x, z, y);
//		
//		}
//		
//	}
//
//	private void pyritePass(Random random, int chunkX, int chunkZ, World world) {
//		
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;	
//		
//		for(int i = 0; i < PYRITECHANCE; i++){
//			int xs = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
//			int ys = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
//			
//			int x = xi+xs;
//			int y = yi+ys;	
//			
//			BiomeGenBase b = world.getBiomeGenForCoords(x, y);
//			if(b != BiomeGenBase.river){
//				i++;//Skip density
//			}
//			
//			int maxL = HelperWorldGen.getUndergroundHeightLevel(world, x, y);
//			int deltaL = HelperWorldGen.assumeSafeHeightDifferenceToBedrock(maxL);
//			int cL=maxL-(int)(random.nextFloat()*(float)deltaL);
//			
//			Block previousBlock = world.getBlock(x, cL, y);
//			if(previousBlock==Blocks.stone){
//				HelperWorldGen.setBlockDirectly(world, stor, xs, ys, cL ,BlockLoader.orePyrite);
//			}
//			
//		}
//		
//	}
//
//
//	
//	private void platinumPass(Random random, int chunkX, int chunkZ, World world) {
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;	
//		
//		for(int i = 0; i < PLATINUMCHANCE; i++){
//			int xs = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
//			int ys = (int) (random.nextFloat()*(float)HelperWorldGen.CHUNKSIZE);
//			
//			int x = xi+xs;
//			int y = yi+ys;	
//			
//			BiomeGenBase b = world.getBiomeGenForCoords(x, y);
//			if(b == BiomeGenBase.river){
//				
//				int maxL = HelperWorldGen.getUndergroundHeightLevel(world, x, y);
//				
//				HelperWorldGen.setBlockDirectly(world, stor, xs, ys, maxL ,BlockLoader.orePlatinum);
//				
//			}
//			
//		}
//	}
//
//	private void asbestosPass(Random random, int chunkX, int chunkZ, World world) {
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;		
//		
//		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
//			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
//				int x = xi+xs;
//				int y = yi+ys;	
//				
//				double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_ASBESTOS, y*NOISEDOWNSAMPLE_ASBESTOS);
//				//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
//				if(noiseFactor>18F){
//					float normalizedShift =  (((float)noiseFactor-18F)/(20F-18F));
//					if(normalizedShift>1){normalizedShift=1;}//clamp, temporarily
//					System.out.println("Shift: "+normalizedShift);
//					
//					int level = HelperWorldGen.getUndergroundHeightLevel(world, x, y);
//					int diff = (int) ((HelperWorldGen.assumeSafeHeightDifferenceToBedrock(level-1)*normalizedShift)+1);
//					
//					//TODO: there is only one asbestos, but we need all of them
//					for(int z = HelperWorldGen.ASSUMED_BEDROCK_LEVEL;z<diff;z++){
//						HelperWorldGen.setBlockDirectly(world, stor, xs, ys, z ,BlockLoader.asbestos_serpentite);
//					}
//					
//					
//				}
//				
//			}
//		}
//		
//		
//		
//	}
//
//
//	
//	private void halitePass(Random random, int chunkX, int chunkZ, World world) {
//		long seed = world.getSeed();
//		
//		ExtendedBlockStorage[] stor = HelperWorldGen.getStorageFromChunk(chunkX, chunkZ, world);
//		
//		
//		
//		int xi = chunkX*HelperWorldGen.CHUNKSIZE;
//		int yi = chunkZ*HelperWorldGen.CHUNKSIZE;
//		
//		int z = 5;
//		
//		for(int xs = 0; xs<HelperWorldGen.CHUNKSIZE;xs++){
//			for(int ys = 0; ys<HelperWorldGen.CHUNKSIZE;ys++){
//				int x = xi+xs;
//				int y = yi+ys;
//				
//				BiomeGenBase b = world.getBiomeGenForCoords(x, y);
//				if(b == BiomeGenBase.desert | b == BiomeGenBase.desertHills | b == BiomeGenBase.beach){
//					//if(b instanceof BiomeGenDesert || b.biomeID==BiomeGenBase.desert.biomeID+128){
//					//Getting factor
//					//double noiseFactor = voronoi.noise((float)x, (float)y,NOISEDOWNSAMPLE);
//					double noiseFactor = perlinHalite.func_151601_a(x*NOISEDOWNSAMPLE_HALITE, y*NOISEDOWNSAMPLE_HALITE)/20F;
//					//FMLLog.log(Level.INFO, "Factor:"+noiseFactor);
//					//Clamping
//					if(noiseFactor>0.4F){
//						//int d = 1;
//						//if(noiseFactor<0F){
//						int d = 1+(int) Math.abs((noiseFactor)*(4));
//						//}
//						//world.setBlock(x, z, y, BlockLoader.oreHalite);
//						//int index = getIndex(x,y,z);
//
//						
//						z = world.getHeightValue(x, y)-(4+2);
//						
//						for(int di = 0; di<d;di++){
//							int rz = z + di;
//							Block previousBlock = world.getBlock(x, rz, y);
//							if(previousBlock==Blocks.stone || previousBlock == Blocks.sand || previousBlock == Blocks.sandstone){
//								HelperWorldGen.setBlockDirectly(world, stor, xs, ys, rz,BlockLoader.oreHalite);
//							}
//						}
//					}
//					
//				}
//				
//				
//			}
//		}
//		
//		
//	}
//


	
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
