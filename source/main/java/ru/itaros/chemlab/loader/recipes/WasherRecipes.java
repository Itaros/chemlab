package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class WasherRecipes {
	public static RecipesCollection recipes;
	
	public static void load(){
		HOEFluid water = HOEFluidLoader.water_natural;
		
		String name;
		ItemStack[] i, o;
		//FCR 4 (WATER+LIGNOCELLULOSE->PURE LIGNOCELLULOSE+EXTRACTIVES(LOW))
		//TODO: Make diluted extractives
		name = "chemlab.washer.[water+lignocellulose->purelignocellulose+lextractives]";
		i = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(water)),new ItemStack(ItemLoader.lignocelluloseflakes)};
		o = new ItemStack[]{new ItemStack(ItemLoader.purelignocelluloseflakes),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.cellulosal_extractives_high))};
		FixedConversionRecipe fcr4 = new FixedConversionRecipe(20,20,i,o,name);		
		fcr4.setUnlocalizedName("washer.lignocellulose");
		//FCR 4-1 (EXPLODED WOOD FIBERS + NaOH SOL -> WASHED LIGNOCELLULOSE + POLYOSE WARET SOLUTION)
		//TODO: Make diluted extractives
		name = "chemlab.washer.[explwoddfibr+naohs->washdlgnclls+polws]";
		i = new ItemStack[]{new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sodiumhydroxide_solution)),new ItemStack(ItemLoader.explodedwoodfibers)};
		o = new ItemStack[]{new ItemStack(ItemLoader.washedlignocellulose),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.polyose_proteined_solution))};
		FixedConversionRecipe fcr4_1 = new FixedConversionRecipe(20,20,i,o,name);		
		fcr4_1.setUnlocalizedName("washer.proteined");
		//FCR 4-2 (Halite washing)
		i = new ItemStack[]{new ItemStack(BlockLoader.oreHalite),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		o = new ItemStack[]{OreDictionary.getOres("dustSand").get(0).copy(),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.nacl_solution))};
		FixedConversionRecipe fcr4_2 = new FixedConversionRecipe(20,20,i,o);		
		fcr4_2.setUnlocalizedName("washer.halite");	
		//Meta-Anthracite washing
		i = new ItemStack[]{OreDictionary.getOres("crushedMetaAnthracite").get(0).copy(),new ItemStack(HiVolumeLiquidCell.getByFluid(water))};
		o = new ItemStack[]{new ItemStack(ItemLoader.amorphousGraphite),new ItemStack(ItemLoader.emptyhvlc,1)};//TODO: Add stone sludge into outcome instead of empty hvlc
		FixedConversionRecipe fcr4_3 = new FixedConversionRecipe(20,20,i,o);		
		fcr4_3.setUnlocalizedName("washer.amorphgraphite");			
		//Collection
		recipes = new RecipesCollection(fcr4,fcr4_1,fcr4_2,fcr4_3);
		recipes.register();			
	}
	
}
