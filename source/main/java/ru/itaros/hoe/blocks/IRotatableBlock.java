package ru.itaros.hoe.blocks;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatableBlock {

	
	public ForgeDirection getDirection(World w, int x, int y, int z);

	
	public void rotate(World w, int x, int y, int z);
	
	public ForgeDirection[] getRotationChain();


	public int getRealSide(int side, int blockMetadata);
	
}
