package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.ArrayList;

import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.api.hoe.registries.IIORegistry;



public class SimpleRecipesLoader {
	
	private SRPMode mode;
	
	private UserspaceRecipe current;
	
	private ArrayList<UserspaceRecipe> recipes = new ArrayList<UserspaceRecipe>();
	
	void parse(String[] data) {
		current = new UserspaceRecipe();
		for(int x = 0 ; x < data.length ; x ++){
			String cur = data[x];
			if(cur.equalsIgnoreCase("RECIPE BY:")){
				mode = SRPMode.RECIPEBY;
				continue;
			}
			if(cur.equalsIgnoreCase("TIME:")){
				x++;
				current.time=Integer.parseInt(data[x]);	
				continue;
			}	
			if(cur.equalsIgnoreCase("POWER:")){
				x++;
				current.power=Integer.parseInt(data[x]);	
				continue;
			}				
			if(cur.equalsIgnoreCase("IN:")){
				mode = SRPMode.IN;
				continue;
			}
			if(cur.equalsIgnoreCase("OUT:")){
				mode = SRPMode.OUT;
				continue;
			}		
			if(cur.equalsIgnoreCase("EVAL")){
				recipes.add(current);
				current = new UserspaceRecipe();
				continue;
			}
			
			//#ELSE
			switch(mode){
			case RECIPEBY:
				current.recipeBy.add(cur);
				break;
			case IN:
				current.in_string.add(cur);
				break;
			case OUT:
				current.out_string.add(cur);
				break;
			case LOOKUP:
				throw new RuntimeException("Malformed CL3 Addon dirproc!");
			}
			
		}
		
	}
	
	public void registerRecipes(){
		IIORegistry registry = HOEIO.getIORegistry();
		//Getting IOs
		for(UserspaceRecipe r:recipes){
			r.register(registry);
		}
	}
	
	
	private static enum SRPMode{
		LOOKUP,
		RECIPEBY,
		IN,
		OUT
	}
}
