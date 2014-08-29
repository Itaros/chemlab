package ru.itaros.chemlab.blocks.multiblock;

import scala.Console;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
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
	
	public void compareAll(MBAssociativeDataPayload payload){
		int errors=0;
		//All Layers
		for(int yl=0; yl < def.getLevels(); yl++){
			//Frame reset
			resetLateralFrame(def.getLevels()-yl);			
			//Getting frame snapshot
			MultiblockTemplateLayer layer = def.getLevelSnapshot(ty);
			//Filling
			errors+=compareAgainstLayer(payload,layer,false);
		}	
		float percentage = getErrorPercentage(errors);
		System.out.println("Error margin: "+percentage);
		errorMargin = percentage;
	}
	
	private float errorMargin;
	public float getErrorMargin(){
		return errorMargin;
	}

	private float getErrorPercentage(int errors) {
		int volume = def.getXDim()*def.getZDim()*def.getLevels();
		return (float)errors/(float)volume;
	}
	public void fillAll(){
		//All Layers
		for(int yl=0; yl < def.getLevels(); yl++){
			//Frame reset
			resetLateralFrame(def.getLevels()-yl);			
			//Getting frame snapshot
			MultiblockTemplateLayer layer = def.getLevelSnapshot(ty);
			//Filling
			fillAgainstLayer(layer,false);
		}	
	}
	
	/*
	 * Make sure world pointer is set correctly!
	 */
	private void fillAgainstLayer(MultiblockTemplateLayer layer, boolean invertQuery) {
		tx=0;
		resetXWorldPointer();
		for(;tx<def.getXDim();tx++){
			tz=0;
			resetZWorldPointer();
			for(;tz<def.getZDim();tz++){
				short query = invertQuery?layer.query(tz,tx):layer.query(tx,tz);
				if(query>0){
					w.setBlock(x, y, z, Blocks.brick_block);
				}
				z++;
			}
			x++;
		}
	}

	private int compareAgainstLayer(MBAssociativeDataPayload payload, MultiblockTemplateLayer layer, boolean invertQuery) {
		int errors = 0;
		
		tx=0;
		resetXWorldPointer();
		for(;tx<def.getXDim();tx++){
			tz=0;
			resetZWorldPointer();
			for(;tz<def.getZDim();tz++){
				short query = invertQuery?layer.query(tz,tx):layer.query(tx,tz);
				
				Block b = w.getBlock(x, y, z);
				int meta = w.getBlockMetadata(x, y, z);
				TileEntity te = w.getTileEntity(x, y, z);//In a case of MB crossecting
				
				boolean responce = def.compare(payload, b,meta,te,query);
				errors+=responce?0:1;
				
				z++;
			}
			x++;
		}	
		
		return errors;
	}
	
	//This method uses previous rotation values from comparator!
	public void fillAllWithTE() {
		//All Layers
		for(int yl=0; yl < def.getLevels(); yl++){
			//Frame reset
			resetLateralFrame(def.getLevels()-yl);			
			//Getting frame snapshot
			MultiblockTemplateLayer layer = def.getLevelSnapshot(ty);
			//Filling
			fillAgainstLayerWithTE(layer,false);
		}
	}
	
	private void fillAgainstLayerWithTE(MultiblockTemplateLayer layer, boolean invertQuery) {
		tx=0;
		resetXWorldPointer();
		for(;tx<def.getXDim();tx++){
			tz=0;
			resetZWorldPointer();
			for(;tz<def.getZDim();tz++){
				short query = invertQuery?layer.query(tz,tx):layer.query(tx,tz);
				if(def.isSuatableForTEPointer(query)){
					w.setTileEntity(x, y, z, new MultiblockPointerTileEntity(hostX,hostY,hostZ));
				}
				z++;
			}
			x++;
		}
	}
	
	
}
