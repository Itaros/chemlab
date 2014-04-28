package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class MixerRecipes {
	public static RecipesCollection recipes;
	
	public static void load(){
		
		ItemStack[] i = {new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurtrioxide_gas)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.water_natural))};
		ItemStack[] o = {new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphuricacid_solution)),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr = new FixedConversionRecipe(1000, 50, i, o);
		fcr.setUnlocalizedName("mixer.sulphuricacid");
		
		i = new ItemStack[]{new ItemStack(ItemLoader.carbonizediron,1),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas))};
		o = new ItemStack[]{OreDictionary.getOres("dustIron").get(0).copy(),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbonmonooxide_gas))};
		FixedConversionRecipe fcr2 = new FixedConversionRecipe(1000, 50, i, o);
		fcr2.setUnlocalizedName("mixer.decarboxination");
		
		i = new ItemStack[]{new ItemStack(ItemLoader.carbonizedsulfuricatediron,1),new ItemStack(ItemLoader.magnesium)};
		o = new ItemStack[]{new ItemStack(ItemLoader.carbonizediron),new ItemStack(ItemLoader.magnesiumsulfide)};
		FixedConversionRecipe fcr3 = new FixedConversionRecipe(1000, 50, i, o);
		fcr3.setUnlocalizedName("mixer.desulphurication");		
		
		recipes = new RecipesCollection(fcr,fcr2,fcr3);
		recipes.register();
		
	}
}
