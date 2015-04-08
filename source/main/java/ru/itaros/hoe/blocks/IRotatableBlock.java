package ru.itaros.hoe.blocks;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatableBlock extends IRotationSolver{

	
	public ForgeDirection getDirection(World w, int x, int y, int z);

	
	public void rotate(World w, int x, int y, int z);

}
