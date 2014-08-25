package ru.itaros.chemlab.blocks.multiblock;

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
	public abstract int getYDim();

	private int originLevel;
	private short originBlockDEFID;
	
	private int axisX,axisY;
	
	public final int getOriginLevel() {
		return originLevel;
	}	
	
	public final int getAxisX() {
		return axisX;
	}

	public final int getAxisY() {
		return axisY;
	}

	protected void makeSearchRoot(int level, short originBlockDEFID){
		this.originLevel=level;
		this.originBlockDEFID=originBlockDEFID;
		
		//Looking for origin axis
		short[] raw = layers[level].getRaw();
		for(int x = 0 ; x < getXDim() ; x++){
			for(int y = 0 ; y < getYDim() ; y++){
				int index = (x*getYDim())+y;
				if(raw[index]==this.originBlockDEFID){
					axisX=x;
					axisY=y;
					return;
				}
			}
		}
	}
	
}
