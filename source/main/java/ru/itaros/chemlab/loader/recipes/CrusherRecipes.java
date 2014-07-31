package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.items.CrushedOre;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
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
			IUniversalStack source = UniversalStackUtils.convert(crore.getSourceItem());
			IUniversalStack in = source.copy();in.setStackSize(1);
			IUniversalStack out = UniversalStackUtils.convert(new ItemStack(crore));out.setStackSize(1);
			//overriden
			if(crore==OreDictionary.getOres("crushedIron").get(0).getItem()){
				out.setStackSize(2);
			}
			FixedConversionRecipe fcr = new FixedConversionRecipe(25,(25*ChemLabValues.OILPOWER_FACTOR),in,out);
			fcr.setUnlocalizedName("crusher."+crore.getDictName());
			crushedRecipes[a]=fcr;
		}
		
		
		recipes = new RecipesCollection(crushedRecipes);
		recipes.register();
		
	}
}
