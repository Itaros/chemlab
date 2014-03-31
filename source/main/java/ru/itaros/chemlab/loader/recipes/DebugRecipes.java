package ru.itaros.chemlab.loader.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class DebugRecipes {
	public static RecipesCollection biogrinderRecipes;
	public static RecipesCollection centrifugalExtractorRecipes;
	public static void load(){
		//PRECONFIG
		HOEFluid water = HOEFluidLoader.water_natural;
		
		//======BIOGRINDER======
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
		
		
		//======Centrifugal Extractor======
		//FCR 3 (WATERCELLS+WOODCHIPCLUMP->EXTRACTIVES(HIGH)+LIGNOCELLULOSE)
		name = "WATER+WOODCHIPCLUMP -> EXTRACTIVES(HIGH)+LIGNOCELLULOSE";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.woodchipclump),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.lignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cellulosal_extractives_high))};
		FixedConversionRecipe fcr3 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		centrifugalExtractorRecipes = new RecipesCollection(fcr3);
		
	}
}
