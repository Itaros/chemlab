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
	public static RecipesCollection pressRecipes;
	public static RecipesCollection steamboilerRecipes;
	public static RecipesCollection steamexplosionunitRecipes;
	
	
	public static void load(){
		//PRECONFIG
		HOEFluid water = HOEFluidLoader.water_natural;
		HOEFluid steam_pressurized = HOEFluidLoader.steam_pressurized;
		HOEFluid naoh = HOEFluidLoader.sodiumhydroxide_solution;

		
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
		//FCR 3-1 (WATERCELLS+)
		name = "chemlab.centrextractor.[emptycell+decomplgnclls->wlnh+wclls]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.emptyhvlc),new ItemStack(ItemLoader.decomposedlignocellulose),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.wet_cellulose)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.wet_lignin))};
		FixedConversionRecipe fcr3_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);			
		//Collection
		centrifugalExtractorRecipes = new RecipesCollection(fcr3,fcr3_1);
		centrifugalExtractorRecipes.register();
		
		
		//======Washer======
		//FCR 4 (WATER+LIGNOCELLULOSE->PURE LIGNOCELLULOSE+EXTRACTIVES(LOW))
		//TODO: Make diluted extractives
		name = "chemlab.washer.[water+lignocellulose->purelignocellulose+lextractives]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(water)),new ItemStack(ItemLoader.lignocelluloseflakes)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.purelignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cellulosal_extractives_high))};
		FixedConversionRecipe fcr4 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//FCR 4-1 (EXPLODED WOOD FIBERS + NaOH SOL -> WASHED LIGNOCELLULOSE + POLYOSE WARET SOLUTION)
		//TODO: Make diluted extractives
		name = "chemlab.washer.[explwoddfibr+naohs->washdlgnclls+polws]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(naoh)),new ItemStack(ItemLoader.explodedwoodfibers)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.washedlignocellulose),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.polyose_proteined_solution))};
		FixedConversionRecipe fcr4_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		washerRecipes = new RecipesCollection(fcr4,fcr4_1);
		washerRecipes.register();		
		
		//======Impregnator======
		//FCR 5 (WATER+PURE LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE+EMPTY CELL)
		name = "chemlab.impregnator.[purelignocellulose+water->impregnatedlignocellulose+emptycell]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.purelignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocelluloseflakes),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr5 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//FCR 5-1 (WATER+PURE LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE+EMPTY CELL)
		name = "chemlab.impregnator.[wshdlgnclc+naoh->imprglngc+emptycell]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.washedlignocellulose),new ItemStack(HiVolumeLiquidCell.getByFluid(naoh))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocellulose),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr5_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
				
		//Collection
		impregnatorRecipes = new RecipesCollection(fcr5,fcr5_1);
		impregnatorRecipes.register();		
		
		
		//======Press======
		//FCR 6 (IMPREGNATED LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE PELLETS)
		name = "chemlab.press.[impregnatedlignocellulose->impregnatedlignocellulosepellet]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocelluloseflakes)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedwoodfiberspellet)};
		FixedConversionRecipe fcr6 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//FCR 6-1 (IMPREGNATED LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE PELLETS)
		name = "chemlab.press.[imprglngc->prsdlgncls]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocellulose)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.pressedlignocellulose)};
		FixedConversionRecipe fcr6_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);			
		//Collection
		pressRecipes = new RecipesCollection(fcr6,fcr6_1);
		pressRecipes.register();			
		
		//======Steam Boiler======
		//FCR 7 (WATER->PRESSURIZED STEAM)
		name = "chemlab.steamboiler[water->steam-pressurized]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(steam_pressurized))};
		FixedConversionRecipe fcr7 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//Collection
		steamboilerRecipes = new RecipesCollection(fcr7);
		steamboilerRecipes.register();
		
		//======Steam Explosion Unit======
		//FCR 8 (PRESSURIZED STEAM+IMPREGNATED LIGNOCELLULOSE PELLETS->EXPLODED WOOD FIBERS+EMPTY CELL)
		name ="chemlab.steamexplosionunit[steam-pressurized+impregnatedlignocellulosepellet->explodedwoodfibers+emptycell]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(steam_pressurized)),new ItemStack(ItemLoader.impregnatedwoodfiberspellet)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.explodedwoodfibers),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr8 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		//FCR 8-1 (PRESSURIZED STEAM+PRESSED LIGNOCELLULOSE ->DECOMPOSED LIGNOCELLULOSE + EMPTY CELL)
		name ="chemlab.steamexplosionunit[steam-pressurized+prsdlngcl->dcmpslgncls+empty_cell]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(steam_pressurized)),new ItemStack(ItemLoader.pressedlignocellulose)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.decomposedlignocellulose),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr8_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);				
		//Collection
		steamexplosionunitRecipes = new RecipesCollection(fcr8,fcr8_1);
		steamexplosionunitRecipes.register();		
		
		
	}
}
