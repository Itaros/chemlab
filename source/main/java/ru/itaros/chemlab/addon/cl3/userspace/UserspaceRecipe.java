package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.registries.IIORegistry;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;
import cpw.mods.fml.common.registry.GameRegistry;

public class UserspaceRecipe {
	
	ArrayList<String> recipeBy = new ArrayList<String>();
	ArrayList<String> in_string = new ArrayList<String>();
	ArrayList<String> out_string = new ArrayList<String>();
	
	ArrayList<IUniversalStack> in_stack = new ArrayList<IUniversalStack>();
	ArrayList<IUniversalStack> out_stack =  new ArrayList<IUniversalStack>();
	
	int time,power;
	
	UserspaceRecipe(){
		
	}

	public void register(IIORegistry registry) {
		ArrayList<HOEMachineCrafterIO> ios = new ArrayList<HOEMachineCrafterIO>();
		for(String s:recipeBy){
			ios.add((HOEMachineCrafterIO)registry.get(s));
		}
		//Items need to be searched

		parseItems(in_string,in_stack);
		parseItems(out_string,out_stack);
		
		//Registering in HOEIO
		for(HOEMachineCrafterIO io:ios){
			RecipesCollection ownedRecipes = io.getRecipesCollection();
			IUniversalStack[] in = new IUniversalStack[in_stack.size()];
			in=in_stack.toArray(in);
			IUniversalStack[] out = new IUniversalStack[out_stack.size()];
			out=out_stack.toArray(out);
			
			FixedConversionRecipe fcr = new FixedConversionRecipe(time,power,in,out);
			ownedRecipes.injectAfter(fcr);
			ownedRecipes.register();
		}
	}
	
	private void parseItems(ArrayList<String> source, ArrayList<IUniversalStack> target){
		for(String s:source){
			IUniversalStack addition = UserspaceParsingAssist.parseLinkString(s);	
			target.add(addition);
		}
	}


	
}
