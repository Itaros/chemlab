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
			boolean isValid=true;
			Item[] stricts = r.getIncomingStricttypes();
			//Starting aligned search
			for(Item iC:items){
				boolean isIterationPassed=false;
				for(Item sC:stricts){
					if(iC.getUnlocalizedName().equals(sC.getUnlocalizedName())){
						isIterationPassed=true;
						return r;//THIS IS A TOTAL SHIT I DONT FUCKING KNOW HOW THAT WORKS NOW ANYWAYS
					}
					//if(!stricts[x].getUnlocalizedName().equals(items[x].getUnlocalizedName())){
					//	isValid=false;break;
					//}
				}
				
				if(isIterationPassed==false){
					isValid=false;
					break;
				}
				
			}
			if(isValid){return r;}
		}
		return result;
	}
}
