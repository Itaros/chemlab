package ru.itaros.chemlab.minecraft.tileentity;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.HOEAbstractLinker;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.hoe.data.CatalyticTankData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class CatalyticTankTileEntity extends MachineTileEntity {

	@Override
	public HOEAbstractLinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("CatalyticTankIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines) {
		CatalyticTankData data = new CatalyticTankData();
		machines.injectCustomData(data);
		return data;
	}
	
	public ItemStack exchangeCatalyzer(ItemStack n){
		HOEMachineData data = this.server;
		if(data!=null){
			CatalyticTankData ded = (CatalyticTankData)data;
			return ded.exchangeCatalyzer(n);
		}else{
			return null;//Derp \o/
		}
	}

	

}
