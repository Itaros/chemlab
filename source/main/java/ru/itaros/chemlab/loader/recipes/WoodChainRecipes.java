package ru.itaros.chemlab.loader.recipes;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class WoodChainRecipes {
	public static RecipesCollection biogrinderRecipes;
	public static RecipesCollection centrifugalExtractorRecipes;
	//public static RecipesCollection washerRecipes;
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
		fcr1.setUnlocalizedName("biogrinder.crushlogs");
		//FCR 2 (WOODCHIPS -> WOODCHIPCLUMP)
		name = "chemlab.biogrinder.[woodchips->woodchipclump]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.woodchips)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.woodchipclump)};
		FixedConversionRecipe fcr2 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		fcr2.setUnlocalizedName("biogrinder.crushchips");
		//Collection
		biogrinderRecipes = new RecipesCollection(fcr1,fcr2);
		biogrinderRecipes.register();
		
		
		//======Centrifugal Extractor======
		//FCR 3 (WATERCELLS+WOODCHIPCLUMP->EXTRACTIVES(HIGH)+LIGNOCELLULOSE)
		name = "chemlab.centrextractor.[water+woodchipclump->hextractives+lignocellulose]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.woodchipclump),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.lignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cellulosal_extractives_high))};
		FixedConversionRecipe fcr3 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		fcr3.setUnlocalizedName("centrext.extractives");
		//FCR 3-1 (WATERCELLS+)
		name = "chemlab.centrextractor.[emptycell+decomplgnclls->wlnh+wclls]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.emptyhvlc),new ItemStack(ItemLoader.decomposedlignocellulose),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.wet_cellulose)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.wet_lignin))};
		FixedConversionRecipe fcr3_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);			
		fcr3_1.setUnlocalizedName("centrext.ligninocellulose");
		//FCR 3-2 (CRUSHED NATIVE GOLD->STONE DUST+GOLD DUST)
		FixedConversionRecipe fcr3_2 = getRecipeForNativeCrushedGold();
		FixedConversionRecipe fcr3_3 = getRecipeForNativeCrushedPyrite();
		//FixedConversionRecipe fcr3_4 = getRecipeForNativeCrushedIron();
		//Collection
		centrifugalExtractorRecipes = new RecipesCollection(fcr3,fcr3_1,fcr3_2,fcr3_3);
		centrifugalExtractorRecipes.register();
		
		
	
		
		//======Impregnator======
		//FCR 5 (WATER+PURE LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE+EMPTY CELL)
		name = "chemlab.impregnator.[purelignocellulose+water->impregnatedlignocellulose+emptycell]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.purelignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocelluloseflakes),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr5 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		fcr5.setUnlocalizedName("impregnator.woodfibers");		
		//FCR 5-1 (WATER+PURE LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE+EMPTY CELL)
		name = "chemlab.impregnator.[wshdlgnclc+naoh->imprglngc+emptycell]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.washedlignocellulose),new ItemStack(HiVolumeLiquidCell.getByFluid(naoh))};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocellulose),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr5_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		fcr5_1.setUnlocalizedName("impregnator.lignocellulose");
		
		//Collection
		impregnatorRecipes = new RecipesCollection(fcr5,fcr5_1);
		impregnatorRecipes.register();		
		
		
		//======Press======
		//FCR 6 (IMPREGNATED LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE PELLETS)
		name = "chemlab.press.[impregnatedlignocellulose->impregnatedlignocellulosepellet]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocelluloseflakes)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedwoodfiberspellet)};
		FixedConversionRecipe fcr6 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		fcr6.setUnlocalizedName("press.fiberpellet");
		//FCR 6-1 (IMPREGNATED LIGNOCELLULOSE->IMPREGNATED LIGNOCELLULOSE PELLETS)
		name = "chemlab.press.[imprglngc->prsdlgncls]";
		incoming = new ItemStack[]{new ItemStack(ItemLoader.impregnatedlignocellulose)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.pressedlignocellulose)};
		FixedConversionRecipe fcr6_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);			
		fcr6_1.setUnlocalizedName("press.pressedlignocelluloset");
		//Collection
		pressRecipes = new RecipesCollection(fcr6,fcr6_1);
		pressRecipes.register();			
		
		//======Steam Boiler======
		//FCR 7 (WATER->PRESSURIZED STEAM)
		name = "chemlab.steamboiler[water->steam-pressurized]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		outcoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(steam_pressurized))};
		FixedConversionRecipe fcr7 = new FixedConversionRecipe(20,20*ChemLabValues.OILPOWER_FACTOR,incoming,outcoming,name);		
		fcr7.setUnlocalizedName("steamboiler.steam");
		//Collection
		steamboilerRecipes = new RecipesCollection(fcr7);
		steamboilerRecipes.register();
		
		//======Steam Explosion Unit======
		//FCR 8 (PRESSURIZED STEAM+IMPREGNATED LIGNOCELLULOSE PELLETS->EXPLODED WOOD FIBERS+EMPTY CELL)
		name ="chemlab.steamexplosionunit[steam-pressurized+impregnatedlignocellulosepellet->explodedwoodfibers+emptycell]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(steam_pressurized)),new ItemStack(ItemLoader.impregnatedwoodfiberspellet)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.explodedwoodfibers),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr8 = new FixedConversionRecipe(20,100,incoming,outcoming,name);		
		fcr8.setUnlocalizedName("steamexplunit.explodedwood");
		//FCR 8-1 (PRESSURIZED STEAM+PRESSED LIGNOCELLULOSE ->DECOMPOSED LIGNOCELLULOSE + EMPTY CELL)
		name ="chemlab.steamexplosionunit[steam-pressurized+prsdlngcl->dcmpslgncls+empty_cell]";
		incoming = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(steam_pressurized)),new ItemStack(ItemLoader.pressedlignocellulose)};
		outcoming = new ItemStack[]{new ItemStack(ItemLoader.decomposedlignocellulose),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr8_1 = new FixedConversionRecipe(20,100,incoming,outcoming,name);				
		fcr8_1.setUnlocalizedName("steamexplunit.decomplignoellulose");
		//Collection
		steamexplosionunitRecipes = new RecipesCollection(fcr8,fcr8_1);
		steamexplosionunitRecipes.register();		
		
		
	}


	private static FixedConversionRecipe getRecipeForNativeCrushedGold() {
		FixedConversionRecipe fcr;
		
		ItemStack goldDust = OreDictionary.getOres("dustGold").get(0).copy();
		ItemStack stoneDust = OreDictionary.getOres("dustStone").get(0).copy();
		
		ItemStack[] i = new ItemStack[]{OreDictionary.getOres("crushedGold").get(0).copy()};
		ItemStack[] o = new ItemStack[]{goldDust,stoneDust};
		
		//TODO: facility to validate FCRs before injection
		
		fcr = new FixedConversionRecipe(500, 500*3, i, o);
		fcr.setUnlocalizedName("centrext.crushedgold");
		
		return fcr;
	}
	private static FixedConversionRecipe getRecipeForNativeCrushedPyrite() {
		FixedConversionRecipe fcr;
		
		ItemStack pyriteDust = OreDictionary.getOres("dustPyrite").get(0).copy();
		ItemStack stoneDust = OreDictionary.getOres("dustStone").get(0).copy();
		
		ItemStack[] i = new ItemStack[]{OreDictionary.getOres("crushedPyrite").get(0).copy()};
		ItemStack[] o = new ItemStack[]{pyriteDust,stoneDust};
		
		//TODO: facility to validate FCRs before injection
		
		fcr = new FixedConversionRecipe(500, 500*3, i, o);
		fcr.setUnlocalizedName("centrext.crushedpyrite");
		
		return fcr;
	}	
	
//	private static FixedConversionRecipe getRecipeForNativeCrushedIron(){
//		
//		FixedConversionRecipe fcr;
//		
//		ItemStack pyriteDust = OreDictionary.getOres("dustIron").get(0).copy();
//		ItemStack stoneDust = OreDictionary.getOres("dustStone").get(0).copy();
//		ItemStack ironNugget = new ItemStack(Items.);
//		
//		ItemStack[] i = new ItemStack[]{OreDictionary.getOres("crushedIron").get(0).copy()};
//		ItemStack[] o = new ItemStack[]{pyriteDust,stoneDust,ironNugget};
//		
//		//TODO: facility to validate FCRs before injection
//		
//		fcr = new FixedConversionRecipe(500, 500*3, i, o);
//		fcr.setUnlocalizedName("centrext.crushediron");
//		
//		return fcr;		
//		
//		
//	}
	
	
	
	
}
