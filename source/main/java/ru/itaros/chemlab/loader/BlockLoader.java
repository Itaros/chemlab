package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.blocks.machines.Biogrinder;

public class BlockLoader {
	
	public static Biogrinder biogrinder;
	
	public static void loadBlocks(){
		biogrinder = new Biogrinder();
		GameRegistry.registerBlock(biogrinder ,biogrinder.getUnlocalizedNameRaw());
		
	}
}
