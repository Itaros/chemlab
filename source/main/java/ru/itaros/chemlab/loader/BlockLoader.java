package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.blocks.machines.Biogrinder;
import ru.itaros.chemlab.blocks.machines.CentrifugalExtractor;

public class BlockLoader {
	
	public static Biogrinder biogrinder;
	public static CentrifugalExtractor centriextractor;
	
	public static void loadBlocks(){
		biogrinder = new Biogrinder();
		GameRegistry.registerBlock(biogrinder ,biogrinder.getUnlocalizedNameRaw());
		
		centriextractor = new CentrifugalExtractor();
		GameRegistry.registerBlock(centriextractor ,centriextractor.getUnlocalizedNameRaw());
		
	}
}
