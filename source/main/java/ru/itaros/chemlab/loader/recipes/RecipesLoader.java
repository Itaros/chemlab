package ru.itaros.chemlab.loader.recipes;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesLoader {
	public static void load(){
		forgeOreDictFix();
		
		CrusherRecipes.load();
		FurnaceRecipes.load();
		
		WoodChainRecipes.load();
		DiaphragmalElectrolyzerRecipes.load();
		
		AirCollectorRecipes.load();
		FluidCompressorRecipes.load();
	}

	
	private static final String[] forgeFix = {
		"Iron","Gold"
	};
	
	//TODO: MoveToHOE
	private static void forgeOreDictFix() {
		for(String s : forgeFix){
			if(OreDictionary.getOres("ingot"+s).size()==0){
				String ingotName = s.toLowerCase()+"_ingot";
				Item i = GameRegistry.findItem("minecraft", ingotName);
				if(i==null){throw new RuntimeException("unable to get '"+ingotName+"'");}//TODO: CHEMLAB Exception
				OreDictionary.registerOre("ingot"+s, i);
			}
		}
	}
}
