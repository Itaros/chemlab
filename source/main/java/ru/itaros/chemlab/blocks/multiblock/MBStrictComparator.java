package ru.itaros.chemlab.blocks.multiblock;

import net.minecraft.init.Blocks;
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
		ty=def.getOriginLevel();
		tz=def.getAxisZ();
	}
	private void reset(){
		x=hostX;
		y=hostY;
		z=hostZ;		
	}
	
	private void resetLateralFrame(int level){
		//Setting to lower corner
		tx=0;
		tz=0;	
		ty = level;
		
		resetXWorldPointer();
		resetZWorldPointer();
		y=hostY+(level-def.getOriginLevel())-1;
	}
	private void resetXWorldPointer(){
		x=hostX-def.getAxisX();
	}
	private void resetZWorldPointer(){
		z=hostZ-def.getAxisZ();
	}	
	
	public void fillAll(){
		//All Layers
		for(int yl=0; yl < def.getLevels(); yl++){
			//Frame reset
			resetLateralFrame(def.getLevels()-yl);			
			//Getting frame snapshot
			MultiblockTemplateLayer layer = def.getLevelSnapshot(ty);
			//Filling
			fillAgainstLayer(layer);
		}	
	}
	
	/*
	 * Make sure world pointer is set correctly!
	 */
	private void fillAgainstLayer(MultiblockTemplateLayer layer) {
		tx=0;
		resetXWorldPointer();
		for(;tx<def.getXDim();tx++){
			tz=0;
			resetZWorldPointer();
			for(;tz<def.getZDim();tz++){
				short query = layer.query(tx,tz);
				if(query>0){
					w.setBlock(x, y, z, Blocks.brick_block);
				}
				z++;
			}
			x++;
		}
	}
	
	
	
}
