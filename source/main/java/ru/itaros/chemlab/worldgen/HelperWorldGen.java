package ru.itaros.chemlab.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

public class HelperWorldGen {

	public static final int ASSUMED_BEDROCK_LEVEL = 5;
	public static final int CHUNKSIZE = 16;
	public static final float CHUNKHEIGHT = 250;
	
	/*
	 * This methods acquares chunk storage
	 */
	public static ExtendedBlockStorage[] getStorageFromChunk(int chunkX, int chunkZ,
			World world) {
		Chunk c = world.getChunkFromChunkCoords(chunkX,chunkZ);
		ExtendedBlockStorage[] stor = c.getBlockStorageArray();
		return stor;
	}

	/*
	 * This method sets block bypassing any validation or light passes
	 */
	public static void setBlockDirectly(World world, ExtendedBlockStorage[] storage,
			int x, int y, int height,Block block) {
		int bitshift = height >> 4;
		if(bitshift>=16){bitshift = 15;}
		ExtendedBlockStorage s = storage[bitshift];
		if(s==null){
			s = storage[bitshift] = new ExtendedBlockStorage(height >> 4 << 4, !world.provider.hasNoSky);
		}							
		s.func_150818_a(x,height & 15,y,block);//SET BLOCK
	}	
	
	
	
	public static int getUndergroundHeightLevel(World world, int x, int y){
		return world.getHeightValue(x, y)-(4+2);
	}
	public static int assumeSafeHeightDifferenceToBedrock(int z){
		return z-ASSUMED_BEDROCK_LEVEL;
	}

	
	
	/*
	 * NOT USED
	 */
	private static int getIndex(int x, int y, int h){
		return (y*16)+(h*16*16)+x;
		//return x << 11 | y << 7 | h;
	}		
	

	
	
	
	
}
