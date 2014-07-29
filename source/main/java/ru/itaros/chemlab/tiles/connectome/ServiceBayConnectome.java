package ru.itaros.chemlab.tiles.connectome;

import net.minecraft.tileentity.TileEntity;
import ru.itaros.chemlab.tiles.CatalyticTankTileEntity;
import ru.itaros.chemlab.tiles.DiaphragmalElectrolyzerTileEntity;
import ru.itaros.hoe.tiles.TileEntityConnectome;

public class ServiceBayConnectome extends TileEntityConnectome {

	@Override
	protected boolean isValidTile(TileEntity inspectable) {
		if(inspectable instanceof CatalyticTankTileEntity){return true;}
		if(inspectable instanceof DiaphragmalElectrolyzerTileEntity){return true;}
		return false;
	}

}
