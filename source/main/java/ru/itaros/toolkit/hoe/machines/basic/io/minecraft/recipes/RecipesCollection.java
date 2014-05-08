package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import ru.itaros.api.hoe.registries.IHOERecipeRegistry;

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
	
	public void register(){
		for(Recipe r : recipes){
			IHOERecipeRegistry reg = Recipe.getRecipeRegistry();
			reg.add(r);
		}
	}
	
	private void recalcRequirments() {
		for(Recipe r:recipes){
			int curI=r.getIncomingSlots();
			int curO=r.getOutcomingSlots();
			if(curI>incomingReq){incomingReq=curI;}
			if(curO>outcomingReq){outcomingReq=curO;}
		}
		
	}

	public void injectAfter(Recipe... newOnes){
		Recipe[] o = recipes;
		Recipe[] n = newOnes;
		recipes = new Recipe[o.length+n.length];
		System.arraycopy(o, 0, recipes, 0, o.length);
		System.arraycopy(n, 0, recipes, o.length, n.length);
	}
	
	protected Recipe[] recipes;

	public Recipe[] getRecipes(){
		return recipes;
	}
	public int getRecipesAmount(){
		return recipes.length;
	}
	
}
