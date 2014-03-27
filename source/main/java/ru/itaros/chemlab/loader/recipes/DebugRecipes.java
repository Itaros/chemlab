package ru.itaros.chemlab.loader.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class DebugRecipes {
	public static RecipesCollection biogrinderRecipes;
	public static void load(){
		//FCR 1
		ItemStack[] incoming = new ItemStack[]{new ItemStack(Block.getBlockFromName("log2"))};
		ItemStack[] outcoming = new ItemStack[]{new ItemStack(Block.getBlockFromName("dirt"))};
		FixedConversionRecipe fcr1 = new FixedConversionRecipe(20,100,incoming,outcoming);
		
		
		//Collection
		biogrinderRecipes = new RecipesCollection(fcr1);
		
	}
}
