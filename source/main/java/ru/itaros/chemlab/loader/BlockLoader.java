package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.blocks.machines.Biogrinder;
import ru.itaros.chemlab.blocks.machines.CentrifugalExtractor;
import ru.itaros.chemlab.blocks.machines.Impregnator;
import ru.itaros.chemlab.blocks.machines.Washer;

public class BlockLoader {
	
	public static Biogrinder biogrinder;
	public static CentrifugalExtractor centriextractor;
	public static Washer washer;
	public static Impregnator impregnator;
	
	
	public static void loadBlocks(){
		biogrinder = new Biogrinder();
		GameRegistry.registerBlock(biogrinder ,biogrinder.getUnlocalizedNameRaw());
		
		centriextractor = new CentrifugalExtractor();
		GameRegistry.registerBlock(centriextractor ,centriextractor.getUnlocalizedNameRaw());
		
		washer = new Washer();
		GameRegistry.registerBlock(washer ,washer.getUnlocalizedNameRaw());
		
		impregnator = new Impregnator();
		GameRegistry.registerBlock(impregnator ,impregnator.getUnlocalizedNameRaw());
				
		
	}
}
