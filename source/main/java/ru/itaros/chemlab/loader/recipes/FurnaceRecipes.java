package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.BlockLoader;
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
			fcr.setUnlocalizedName("furnace.dusts."+s.toLowerCase());
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
		
		//Carbothermic reaction
		ItemStack[] i = {new ItemStack(ItemLoader.ferricoxide,2),new ItemStack(ItemLoader.amorphousGraphite,3)};
		ItemStack[] o = {new ItemStack(ItemLoader.carbonizediron,4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbondioxide_gas),3)};
		FixedConversionRecipe carbothermic_ferricoxide = new FixedConversionRecipe(100,1000,i,o);
		carbothermic_ferricoxide.setUnlocalizedName("furnace.carbothermal.ferricoxide");
		
		//Carbothermic reaction for gematite with lime
		ItemStack hematite = OreDictionary.getOres("crushedHematite").get(0).copy();
		hematite.stackSize=2;
		ItemStack limestone = OreDictionary.getOres("crushedLimestone").get(0).copy();
		limestone.stackSize=1;		
		i = new ItemStack[]{hematite,limestone,new ItemStack(ItemLoader.amorphousGraphite,3)};
		o = new ItemStack[]{new ItemStack(ItemLoader.carbonizedsulfuricatediron,4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbondioxide_gas),3),new ItemStack(ItemLoader.slag,1)};
		FixedConversionRecipe carbothermic_ferricoxide_hematite = new FixedConversionRecipe(100,1000,i,o);
		carbothermic_ferricoxide_hematite.setUnlocalizedName("furnace.carbothermal.ferricoxide_hematite");		
		
		i = new ItemStack[]{OreDictionary.getOres("crushedPericlase").get(0).copy(),new ItemStack(ItemLoader.amorphousGraphite)};
		o = new ItemStack[]{new ItemStack(ItemLoader.magnesium),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbonmonooxide_gas))};
		FixedConversionRecipe carbothermic_mgo = new FixedConversionRecipe(100,500,i,o);
		
		
		recipes = new RecipesCollection(dusts);
		recipes.injectAfter(pyriteoxygeneation,carbothermic_ferricoxide,carbothermic_ferricoxide_hematite,carbothermic_mgo);
		recipes.register();
		
		
	}
	
}
