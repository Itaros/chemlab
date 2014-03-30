package ru.itaros.chemlab.loader.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class DebugRecipes {
	public static RecipesCollection biogrinderRecipes;
	public static void load(){
		//FCR 1 (LOGS -> WOODCHIPS)
		String name = "LOGS -> WOODCHIPS";
		ItemStack[] incoming = new ItemStack[]{new ItemStack(Block.getBlockFromName("log2"))};
		ItemStack[] outcoming = new ItemStack[]{new ItemStack(ItemLoader.woodchips)};
		FixedConversionRecipe fcr1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);
		//FCR 2 (WOODCHIPS -> WOODCHIPCLUMP)
		name = "WOODCHIPS -> WOODCHIPCLUMP";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.woodchips)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.woodchipclump)};
		FixedConversionRecipe fcr2 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		
		
		//Collection
		biogrinderRecipes = new RecipesCollection(fcr1,fcr2);
		
	}
}
