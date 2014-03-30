package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.items.WoodchipClump;
import ru.itaros.chemlab.items.Woodchips;

public class ItemLoader {

	public static Woodchips woodchips;
	public static WoodchipClump woodchipclump;
	
	public static void loadItems(){
		woodchips = new Woodchips();
		GameRegistry.registerItem(woodchips, woodchips.getUnlocalizedName());
		
		woodchipclump = new WoodchipClump();
		GameRegistry.registerItem(woodchipclump, woodchipclump.getUnlocalizedName());		
	}
}
