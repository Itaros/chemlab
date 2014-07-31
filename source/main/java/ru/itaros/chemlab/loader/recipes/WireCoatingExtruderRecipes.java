package ru.itaros.chemlab.loader.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.recipes.RecipesCollection;
import ru.itaros.hoe.recipes.WireDiameterBasedFixedConversionRecipe;

public class WireCoatingExtruderRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){	
		IUniversalStack[] i_a,o;
		Item source;
		int min,max;
		
		i_a=UniversalStackUtils.convert(new ItemStack[]{new ItemStack(Items.paper,16)});
		o  =UniversalStackUtils.convert(new ItemStack[]{new ItemStack(ItemLoader.powercable,5)});
		source=ItemLoader.rod_swg_iron;
		min=5;
		max=12;
		WireDiameterBasedFixedConversionRecipe fcr1=new WireDiameterBasedFixedConversionRecipe(
				source,min,max,
				i_a,o
				);
		fcr1.setUnlocalizedName("wcextruder.coatwire.iron");
		
		
		
		//Collection
		recipes = new RecipesCollection(fcr1);
		recipes.register();		
	}
	
	
}
