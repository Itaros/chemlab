package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class FurnaceRecipes {

	public static RecipesCollection recipes;
	
	public static final String[] smeltableDusts = {
		"Gold",
		"Iron"
		//"Platinum"
	};
	
	public static void load(){
		
		FixedConversionRecipe[] dusts = new FixedConversionRecipe[smeltableDusts.length];
		int x = -1;
		for(String s:smeltableDusts){
			x++;
			String dustName = "dust"+s;
			String ingotName = "ingot"+s;
			
			ItemStack i = OreDictionary.getOres(dustName).get(0).copy();
			ItemStack o = OreDictionary.getOres(ingotName).get(0).copy();
			
			
			FixedConversionRecipe fcr = new FixedConversionRecipe(100,1000,i,o);
			
			dusts[x]=fcr;
		}
		
		
		recipes = new RecipesCollection(dusts);
		recipes.register();
		
		
	}
	
}
