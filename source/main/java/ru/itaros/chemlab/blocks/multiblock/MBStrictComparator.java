package ru.itaros.chemlab.blocks.multiblock;

import net.minecraft.world.World;

//TODO: Move to HOE

/*
 * Sophisticated class to validate multiblock design against definition.
 * This is strict version(MBs without design variations)
 */
public class MBStrictComparator {

	private final MBDefinition def;
	
	//World data and invoker coordinates
	private final World w;
	private final int hostX,hostY,hostZ;
	
	//Current walker coordinates
	private int x,y,z;
	//Current template coordinates
	private int tx,ty,tz;
	//Vector scanbucket coordinates
	private int vx=1,vy=1,vz=1;
	
	public MBStrictComparator(MBDefinition definition, World w, int hostX, int hostY, int hostZ){
		def=definition;
		
		this.w=w;
		this.hostX=hostX;
		this.hostY=hostY;
		this.hostZ=hostZ;
		init();
	}
	private void init() {
		reset();
		
		tx=def.getAxisX();
		ty=def.getAxisY();
		tz=def.getOriginLevel();
	}
	private void reset(){
		x=hostX;
		y=hostY;
		z=hostZ;		
	}
	
	
	
	
}
