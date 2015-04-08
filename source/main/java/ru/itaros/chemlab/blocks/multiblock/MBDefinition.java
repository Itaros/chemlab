package ru.itaros.chemlab.blocks.multiblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

//TODO: Move to HOE

/*
 * This class is designated to perform storage 
 * and checking against multiblock composition
 */
public abstract class MBDefinition {

	protected MultiblockTemplateLayer[] layers = new MultiblockTemplateLayer[getLevels()];
	
	/*
	 * Returns amount of static levels. 
	 * In case of dynamic lookup it equals unique levels count
	 */
	public abstract int getLevels();
	
	public abstract int getXDim();
	public abstract int getZDim();

	private int originLevel;
	private short originBlockDEFID;
	
	private int axisX,axisY;
	
	public final int getOriginLevel() {
		return originLevel;
	}	
	
	public final int getAxisX() {
		return axisX;
	}

	public final int getAxisZ() {
		return axisY;
	}

	protected void makeSearchRoot(int level, short originBlockDEFID){
		this.originLevel=level;
		this.originBlockDEFID=originBlockDEFID;
		
		//Looking for origin axis
		short[] raw = layers[level].getRaw();
		for(int x = 0 ; x < getXDim() ; x++){
			for(int y = 0 ; y < getZDim() ; y++){
				int index = (x*getZDim())+y;
				if(raw[index]==this.originBlockDEFID){
					axisX=x;
					axisY=y;
					return;
				}
			}
		}
	}

	public MultiblockTemplateLayer getLevelSnapshot(int tz) {
		return layers[tz-1];
	}

	public abstract boolean compare(MBAssociativeDataPayload payload, Block block, int meta, TileEntity te, short query);
	
	protected static Integer compareBlock(Block b, int meta, ItemStack[] set){
		int step=-1;
		for(ItemStack is:set){
			step++;
			boolean answer = ((ItemBlock)is.getItem()).field_150939_a==b&&meta==is.getItemDamage();
			if(answer){return step;}
		}
		return -1;
	}

	public boolean isSuatableForTEPointer(short query) {
		return query>0;
	}
	
	private int samples[];
	
	public int getPartCount(int index){
		return samples[index];
	}
	
	protected void prepareSamplesValues(){
		int max = 0;
		//Getting maximal
		for(int i = 1 ; i <= getLevels(); i++){
			MultiblockTemplateLayer snap = getLevelSnapshot(i);
			short[] r = snap.getRaw();
			for(short s:r){
				if(s>max){max=s;}
			}
		}
		//Allocating
		samples = new int[max+1];
		//Counting
		for(int i = 1 ; i <= getLevels(); i++){
			MultiblockTemplateLayer snap = getLevelSnapshot(i);
			short[] r = snap.getRaw();
			for(short s:r){	
				if(s<0){continue;}
				samples[s]++;
			}
		}
		
	}

	public abstract void initialize(MBAssociativeDataPayload payload);
	
}
