package ru.itaros.chemlab.loader.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.hoe.*;
import ru.itaros.chemlab.minecraft.tileentity.*;
import ru.itaros.toolkit.hoe.io.IOCollectionHelper;

public class TileEntityLoader {

	public static IOCollectionHelper load(){
		IOCollectionHelper iocollection = new IOCollectionHelper(
    			new BiogrinderIO(),
    			new CentrifugalExtractorIO(),
    			new WasherIO(),
    			new ImpregnatorIO(),
    			new PressIO(),
    			new SteamBoilerIO(),
    			new SteamExplosionUnitIO()
    			);
		iocollection.registerInHOE();
		
		
		
		GameRegistry.registerTileEntity(BiogrinderTileEntity.class, BiogrinderTileEntity.class.getName());
		GameRegistry.registerTileEntity(CentrifugalExtractorTileEntity.class, CentrifugalExtractorTileEntity.class.getName());
		GameRegistry.registerTileEntity(WasherTileEntity.class, WasherTileEntity.class.getName());
		GameRegistry.registerTileEntity(ImpregnatorTileEntity.class, ImpregnatorTileEntity.class.getName());
		GameRegistry.registerTileEntity(PressTileEntity.class, PressTileEntity.class.getName());
		GameRegistry.registerTileEntity(SteamBoilerTileEntity.class, SteamBoilerTileEntity.class.getName());
		GameRegistry.registerTileEntity(SteamExplosionUnitTileEntity.class, SteamExplosionUnitTileEntity.class.getName());
		
		return iocollection;
	}
	
}
