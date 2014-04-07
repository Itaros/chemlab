package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.blocks.machines.*;

public class BlockLoader {
	
	public static Biogrinder biogrinder;
	public static CentrifugalExtractor centriextractor;
	public static Washer washer;
	public static Impregnator impregnator;
	public static Press press;
	public static SteamBoiler steamboiler;
	public static SteamExplosionUnit steamexplosionunit;
	
	
	public static void loadBlocks(){
		biogrinder = new Biogrinder();
		GameRegistry.registerBlock(biogrinder ,biogrinder.getUnlocalizedNameRaw());
		
		centriextractor = new CentrifugalExtractor();
		GameRegistry.registerBlock(centriextractor ,centriextractor.getUnlocalizedNameRaw());
		
		washer = new Washer();
		GameRegistry.registerBlock(washer ,washer.getUnlocalizedNameRaw());
		
		impregnator = new Impregnator();
		GameRegistry.registerBlock(impregnator ,impregnator.getUnlocalizedNameRaw());
			
		press = new Press();
		GameRegistry.registerBlock(press ,press.getUnlocalizedNameRaw());
			
		steamboiler = new SteamBoiler();
		GameRegistry.registerBlock(steamboiler,steamboiler.getUnlocalizedNameRaw());
		
		steamexplosionunit = new SteamExplosionUnit();
		GameRegistry.registerBlock(steamexplosionunit,steamexplosionunit.getUnlocalizedNameRaw());
		
		
	}
}
