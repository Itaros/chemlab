package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.items.CrushedOre;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class CrusherRecipes {
	
	public static RecipesCollection recipes;
	
	public static void load(){
		CrushedOre[] crushedOres=CrushedOre.getAll();
		FixedConversionRecipe[] crushedRecipes = new FixedConversionRecipe[crushedOres.length];
		int a = -1;
		for(CrushedOre crore:crushedOres){
			a++;
			//TODO: Add support for multiple ores in oredict
			ItemStack source = crore.getSourceItem();
			ItemStack in = source.copy();in.stackSize=1;
			ItemStack out = new ItemStack(crore);out.stackSize=1;
			//overriden
			if(crore==OreDictionary.getOres("crushedIron").get(0).getItem()){
				out.stackSize=2;
			}
			FixedConversionRecipe fcr = new FixedConversionRecipe(25,(25*ChemLabValues.OILPOWER_FACTOR),in,out);
			fcr.setUnlocalizedName("crusher."+crore.getDictName());
			crushedRecipes[a]=fcr;
		}
		
		
		recipes = new RecipesCollection(crushedRecipes);
		recipes.register();
		
	}
}
