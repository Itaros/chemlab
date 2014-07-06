package ru.itaros.chemlab.loader.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class HiRMixerRecipes {
	public static RecipesCollection recipes;
	
	public static void load(){
		
		ItemStack[] i = {new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurtrioxide_gas)),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.water_natural))};
		ItemStack[] o = {new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphuricacid_solution)),new ItemStack(ItemLoader.emptyhvlc)};
		FixedConversionRecipe fcr = new FixedConversionRecipe(1000, 1000, i, o);
		fcr.setUnlocalizedName("mixer.sulphuricacid");
		
		i = new ItemStack[]{new ItemStack(ItemLoader.pigiron,1),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas))};
		o = new ItemStack[]{OreDictionary.getOres("dustIron").get(0).copy(),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbonmonooxide_gas))};
		FixedConversionRecipe fcr2 = new FixedConversionRecipe(1000, 1000, i, o);
		fcr2.setUnlocalizedName("mixer.decarboxination");
		
		i = new ItemStack[]{new ItemStack(ItemLoader.sulfuricatedpigiron,1),new ItemStack(ItemLoader.magnesium)};
		o = new ItemStack[]{new ItemStack(ItemLoader.pigiron),new ItemStack(ItemLoader.magnesiumsulfide)};
		FixedConversionRecipe fcr3 = new FixedConversionRecipe(1000, 1000, i, o);
		fcr3.setUnlocalizedName("mixer.desulphurication");		
		
		FixedConversionRecipe fcr4=null;
		FixedConversionRecipe fcr5=null;
		if(TierLoader.L0_WroughtIron.isEnabled()){		
			ItemStack stoneInclusions = OreDictionary.getOres("dustStone").get(0).copy();
			stoneInclusions.stackSize=3;
			i = new ItemStack[]{new ItemStack(ItemLoader.wroughtiron,4)};
			o = new ItemStack[]{OreDictionary.getOres("ingotIron").get(0).copy(),stoneInclusions};
			fcr4 = new FixedConversionRecipe(2000, 5000, i, o);
			fcr4.setUnlocalizedName("mixer.wroughtpurify");		
			
	
			i = new ItemStack[]{OreDictionary.getOres("ingotIron").get(0).copy(),stoneInclusions};
			o = new ItemStack[]{new ItemStack(ItemLoader.wroughtiron,4)};
			fcr5 = new FixedConversionRecipe(1000, 5000, i, o);
			fcr5.setUnlocalizedName("mixer.ironaddstone");		
		}	
		
		
		
		
		recipes = new RecipesCollection(fcr,fcr2,fcr3,fcr4,fcr5);
		recipes.register();
		
	}
}
