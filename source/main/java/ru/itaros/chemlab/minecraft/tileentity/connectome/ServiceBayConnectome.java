package ru.itaros.chemlab.minecraft.tileentity.connectome;

import net.minecraft.tileentity.TileEntity;
import ru.itaros.chemlab.minecraft.tileentity.CatalyticTankTileEntity;
import ru.itaros.chemlab.minecraft.tileentity.DiaphragmalElectrolyzerTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.TileEntityConnectome;

public class ServiceBayConnectome extends TileEntityConnectome {

	@Override
	protected boolean isValidTile(TileEntity inspectable) {
		if(inspectable instanceof CatalyticTankTileEntity){return true;}
		if(inspectable instanceof DiaphragmalElectrolyzerTileEntity){return true;}
		return false;
	}

}
