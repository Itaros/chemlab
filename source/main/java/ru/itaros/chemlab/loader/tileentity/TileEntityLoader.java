package ru.itaros.chemlab.loader.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.hoe.BiogrinderIO;
import ru.itaros.chemlab.hoe.CentrifugalExtractorIO;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.chemlab.minecraft.tileentity.CentrifugalExtractorTileEntity;
import ru.itaros.toolkit.hoe.io.IOCollectionHelper;

public class TileEntityLoader {

	public static IOCollectionHelper load(){
		IOCollectionHelper iocollection = new IOCollectionHelper(
    			new BiogrinderIO(),
    			new CentrifugalExtractorIO()
    			);
		iocollection.registerInHOE();
		
		
		GameRegistry.registerTileEntity(BiogrinderTileEntity.class, BiogrinderTileEntity.class.getName());
		GameRegistry.registerTileEntity(CentrifugalExtractorTileEntity.class, CentrifugalExtractorTileEntity.class.getName());
		
		
		return iocollection;
	}
	
}
