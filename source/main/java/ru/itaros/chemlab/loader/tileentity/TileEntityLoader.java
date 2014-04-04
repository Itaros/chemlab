package ru.itaros.chemlab.loader.tileentity;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.hoe.BiogrinderIO;
import ru.itaros.chemlab.hoe.CentrifugalExtractorIO;
import ru.itaros.chemlab.hoe.ImpregnatorIO;
import ru.itaros.chemlab.hoe.WasherIO;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.chemlab.minecraft.tileentity.CentrifugalExtractorTileEntity;
import ru.itaros.chemlab.minecraft.tileentity.ImpregnatorTileEntity;
import ru.itaros.chemlab.minecraft.tileentity.WasherTileEntity;
import ru.itaros.toolkit.hoe.io.IOCollectionHelper;

public class TileEntityLoader {

	public static IOCollectionHelper load(){
		IOCollectionHelper iocollection = new IOCollectionHelper(
    			new BiogrinderIO(),
    			new CentrifugalExtractorIO(),
    			new WasherIO(),
    			new ImpregnatorIO()
    			);
		iocollection.registerInHOE();
		
		
		
		GameRegistry.registerTileEntity(BiogrinderTileEntity.class, BiogrinderTileEntity.class.getName());
		GameRegistry.registerTileEntity(CentrifugalExtractorTileEntity.class, CentrifugalExtractorTileEntity.class.getName());
		GameRegistry.registerTileEntity(WasherTileEntity.class, WasherTileEntity.class.getName());
		GameRegistry.registerTileEntity(ImpregnatorTileEntity.class, ImpregnatorTileEntity.class.getName());
		
		return iocollection;
	}
	
}
