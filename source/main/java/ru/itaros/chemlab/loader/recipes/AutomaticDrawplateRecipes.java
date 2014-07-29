package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.hoe.recipes.DrawplateRecipe;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class AutomaticDrawplateRecipes {

	public static RecipesCollection recipes;
	
	public static void load(){
		
		DrawplateRecipe wires_iron=null,wires_wrought=null;
		FixedConversionRecipe rodtowire_iron=null,rodtowire_wrought=null;
		
		
		wires_iron = new DrawplateRecipe(50,550,new ItemStack(ItemLoader.rod_swg_brittle_iron), new ItemStack(ItemLoader.scraps_iron));
		wires_iron.setUnlocalizedName("adraw.finer.iron");
		rodtowire_iron = new FixedConversionRecipe(100,700,new ItemStack(ItemLoader.rod_iron),new ItemStack(ItemLoader.rod_swg_brittle_iron));
		rodtowire_iron.setUnlocalizedName("adraw.towire.iron");
		if(TierLoader.L0_WroughtIron.isEnabled()){
			wires_wrought = new DrawplateRecipe(50,550,new ItemStack(ItemLoader.rod_swg_brittle_wroughtIron), new ItemStack(ItemLoader.scraps_wrought));
			wires_wrought.setUnlocalizedName("adraw.finer.wrought");
			rodtowire_wrought = new FixedConversionRecipe(100,700,new ItemStack(ItemLoader.rod_wroughtIron),new ItemStack(ItemLoader.rod_swg_brittle_wroughtIron));
			rodtowire_wrought.setUnlocalizedName("adraw.towire.wrought");
		}
		
		recipes = new RecipesCollection(wires_iron,wires_wrought,rodtowire_iron,rodtowire_wrought);
		recipes.register();
		
	}
	
}
