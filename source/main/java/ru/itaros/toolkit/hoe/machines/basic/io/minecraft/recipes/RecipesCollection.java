package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import net.minecraft.item.Item;

public class RecipesCollection {
	
	protected int incomingReq,outcomingReq;
	
	public final int getIncomingReq() {
		return incomingReq;
	}

	public final int getOutcomingReq() {
		return outcomingReq;
	}

	public RecipesCollection(Recipe... recipes){
		this.recipes=recipes;
		recalcRequirments();
	}
	
	private void recalcRequirments() {
		for(Recipe r:recipes){
			int curI=r.getIncomingSlots();
			int curO=r.getOutcomingSlots();
			if(curI>incomingReq){incomingReq=curI;}
			if(curO>outcomingReq){outcomingReq=curO;}
		}
		
	}

	protected Recipe[] recipes;

	public Recipe findIncomingPattern(Item[] items) {
		Recipe result=null;
		for(Recipe r : recipes){
			if(r.getIncomingSlots()==items.length){
				Item[] stricts = r.getIncomingStricttypes();
				//Starting aligned search
				boolean isValid=true;
				for(int x = 0; x < stricts.length; x++){
					if(!stricts[x].getUnlocalizedName().equals(items[x].getUnlocalizedName())){
						isValid=false;break;
					}
				}
				if(isValid){return r;}
			}
		}
		return result;
	}
}
