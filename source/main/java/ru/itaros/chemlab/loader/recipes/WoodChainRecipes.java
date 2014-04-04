package ru.itaros.chemlab.loader.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class WoodChainRecipes {
	public static RecipesCollection biogrinderRecipes;
	public static RecipesCollection centrifugalExtractorRecipes;
	public static RecipesCollection washerRecipes;
	public static RecipesCollection impregnatorRecipes;
	
	
	public static void load(){
		//PRECONFIG
		HOEFluid water = HOEFluidLoader.water_natural;
		
		//======BIOGRINDER======
		//FCR 1 (LOGS -> WOODCHIPS)
		String name = "chemlab.biogrinder.[logs->woodchips]";
		ItemStack[] incoming = new ItemStack[]{new ItemStack(Block.getBlockFromName("log2"))};
		ItemStack[] outcoming = new ItemStack[]{new ItemStack(ItemLoader.woodchips)};
		FixedConversionRecipe fcr1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);
		//FCR 2 (WOODCHIPS -> WOODCHIPCLUMP)
		name = "chemlab.biogrinder.[woodchips->woodchipclump]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.woodchips)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.woodchipclump)};
		FixedConversionRecipe fcr2 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		biogrinderRecipes = new RecipesCollection(fcr1,fcr2);
		biogrinderRecipes.register();
		
		
		//======Centrifugal Extractor======
		//FCR 3 (WATERCELLS+WOODCHIPCLUMP->EXTRACTIVES(HIGH)+LIGNOCELLULOSE)
		name = "chemlab.centrextractor.[water+woodchipclump->hextractives+lignocellulose]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.woodchipclump),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.lignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cellulosal_extractives_high))};
		FixedConversionRecipe fcr3 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		centrifugalExtractorRecipes = new RecipesCollection(fcr3);
		centrifugalExtractorRecipes.register();
		
		
		//======Washer======
		//FCR 4 (WATER+LIGNOCELLULOSE->PURE LIGNOCELLULOSE+EXTRACTIVES(LOW))
		//TODO: Make diluted extractives
		name = "chemlab.centrextractor.[water+lignocellulose->purelignocellulose+lextractives]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(water)),new ItemStack(ItemLoader.lignocelluloseflakes)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.purelignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cellulosal_extractives_high))};
		FixedConversionRecipe fcr4 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		washerRecipes = new RecipesCollection(fcr4);
		washerRecipes.register();
		
		//======Impregnator======
		//FCR 5 (WATER+PURE LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE+EMPTY CELL)
		name = "chemlab.centrextractor.[purelignocellulose+water->impregnatedlignocellulose+emptycell]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.purelignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocelluloseflakes),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr5 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		impregnatorRecipes = new RecipesCollection(fcr5);
		impregnatorRecipes.register();		
		
		
	}
}
