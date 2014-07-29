package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;

public class MetalFormationMachineRecipes {

	public static RecipesCollection recipes;
	
	
	public static void load(){
		
		FixedConversionRecipe rod_wrought=null;
		FixedConversionRecipe rod_iron=null;
		if(TierLoader.L0_WroughtIron.isEnabled()){
			rod_wrought = new FixedConversionRecipe(250,500,TierLoader.L0_WroughtIron.getTargetItem(),new ItemStack(ItemLoader.rod_wroughtIron));
			rod_wrought.setUnlocalizedName("metformer.rod.wrought");
		}
		rod_iron = new FixedConversionRecipe(250,500,OreDictionary.getOres("ingotIron").get(0).copy(),new ItemStack(ItemLoader.rod_iron));
		rod_iron.setUnlocalizedName("metformer.rod.iron");		
		
		recipes = new RecipesCollection(rod_wrought,rod_iron);
		recipes.register();
		
	}
	
	
}
