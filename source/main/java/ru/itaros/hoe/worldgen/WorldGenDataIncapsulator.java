package ru.itaros.hoe.worldgen;

import java.util.Random;

import net.minecraft.world.World;

/*
 * This class takes posession of worldgen data to switch chunks easily
 * , while passing only itself to generators
 */
public class WorldGenDataIncapsulator {

	
	public Random random;
	public int chunkX;
	public int chunkZ;
	public World world;
	
	
	public void set(Random random, int chunkX, int chunkZ, World world){
		this.random=random;
		this.chunkX=chunkX;
		this.chunkZ=chunkZ;
		this.world=world;
	}
	public void set(int chunkX, int chunkZ, World world){
		this.chunkX=chunkX;
		this.chunkZ=chunkZ;
		
		this.world=world;
	}
	
	
}
