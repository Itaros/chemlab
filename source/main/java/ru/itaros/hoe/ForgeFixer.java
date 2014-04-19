package ru.itaros.hoe;

import net.minecraft.item.Item;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class ForgeFixer {
	
	private static final String[] forgeFix = {
		"Iron","Gold"
	};	
	
	
	
	public static void forgeOreDictFix() {
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
