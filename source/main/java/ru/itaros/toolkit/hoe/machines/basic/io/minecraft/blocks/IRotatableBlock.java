package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public interface IRotatableBlock {

	
	public ForgeDirection getDirection(World w, int x, int y, int z);

	
	public void rotate(World w, int x, int y, int z);
	
	public ForgeDirection[] getRotationChain();
	
}
