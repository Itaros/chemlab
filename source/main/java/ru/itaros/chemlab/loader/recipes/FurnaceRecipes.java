package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class FurnaceRecipes {

	public static RecipesCollection recipes;
	
	public static final String[] smeltableDusts = {
		"Gold",
		"Iron"
		//"Platinum"
	};
	
	public static void load(){
		//Dusts
		FixedConversionRecipe[] dusts = new FixedConversionRecipe[smeltableDusts.length];
		int x = -1;
		for(String s:smeltableDusts){
			x++;
			String dustName = "dust"+s;
			String ingotName = "ingot"+s;
			
			ItemStack i = OreDictionary.getOres(dustName).get(0).copy();
			ItemStack o = OreDictionary.getOres(ingotName).get(0).copy();
			
			
			FixedConversionRecipe fcr = new FixedConversionRecipe(100,1000,i,o);
			
			dusts[x]=fcr;
		}
		//Compisitors & decompositors
		ItemStack pyrite_dust = OreDictionary.getOres("dustPyrite").get(0).copy();
		pyrite_dust.stackSize=4;
		ItemStack oxygene = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas),11);
		ItemStack[] po_i = new ItemStack[]{pyrite_dust,oxygene};
		ItemStack sulphurdioxide = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurdioxide_gas),8);
		ItemStack ferricoxide = new ItemStack(ItemLoader.ferricoxide,2);
		ItemStack[] po_o = new ItemStack[]{ferricoxide,sulphurdioxide};
		FixedConversionRecipe pyriteoxygeneation = new FixedConversionRecipe(200,1000,po_i,po_o);
		
		
		recipes = new RecipesCollection(dusts);
		recipes.injectAfter(pyriteoxygeneation);
		recipes.register();
		
		
	}
	
}
