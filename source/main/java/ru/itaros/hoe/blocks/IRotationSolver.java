package ru.itaros.hoe.blocks;

import net.minecraftforge.common.util.ForgeDirection;

/*
 * Used to apply IRotatableBlock solver subset to TileEntities
 */
public interface IRotationSolver {
	
	public ForgeDirection[] getRotationChain();
	public int getRealSide(int side, int blockMetadata);
	
}
