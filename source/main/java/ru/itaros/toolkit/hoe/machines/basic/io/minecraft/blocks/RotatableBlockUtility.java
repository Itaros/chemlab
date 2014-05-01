package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks;

import net.minecraft.world.World;

public class RotatableBlockUtility {

	public static final int SIDEGRID_DIM = 6;
	
	public static final int[] DEFAULTSIDEGRID = {
		0,1,2,3,4,5,
		0,1,4,5,3,2,
		0,1,3,2,5,4,
		0,1,5,4,2,3
	};	
	
	public static int calculateSpinIncrement(World w, int x, int y, int z, int variances) {
		int off = w.getBlockMetadata(x, y, z);
		off++;
		if(off>=variances){
			off=0;//Wrap
		}
		return off;
	}

	public static int getIconIndiceFromSideGrid(int side, int dir,
			int[] sideGrid) {
		int pointer = dir*SIDEGRID_DIM;
		return sideGrid[pointer+side];
	}

}
