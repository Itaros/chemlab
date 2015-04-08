package ru.itaros.hoe.recipes;

import ru.itaros.api.hoe.registries.IHOERecipeRegistry;

public class RecipesCollection {
	
	protected int incomingReq,outcomingReq;
	
	public final int getIncomingReq() {
		return incomingReq;
	}

	public final int getOutcomingReq() {
		return outcomingReq;
	}

	private Recipe[] denullify(Recipe[] recipes){
		int nulls=0;
		for(Recipe r:recipes){
			if(r==null){nulls++;}
		}
		Recipe[] nr;
		if(nulls>0){
			nr = new Recipe[recipes.length-nulls];
			int i=-1;
			for(Recipe r:recipes){
				if(r!=null){
					i++;
					nr[i]=r;
				}
			}			
		}else{
			nr = recipes;
		}
		return nr;
	}
	
	
	public RecipesCollection(Recipe... recipes){
		Recipe[] nr = denullify(recipes);	
		
		this.recipes=nr;
		
		recalcRequirments();
	}
	
	public void register(){
		for(Recipe r : recipes){
			r.makeFinal();
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
		Recipe[] n = denullify(newOnes);	
		
		
		Recipe[] o = recipes;
		//Recipe[] n = newOnes;
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
