package ru.itaros.chemlab.minecraft.tileentity;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.HOEAbstractLinker;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class DiaphragmalElectrolyzerTileEntity extends MachineTileEntity {

	@Override
	public HOEAbstractLinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("DiaphragmalElectrolyzerIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines) {
		DiaphragmalElectrolyzerData data = new DiaphragmalElectrolyzerData();
		machines.injectCustomData(data);
		return data;
	}
	
	public ItemStack exchangeDiaphragm(ItemStack n){
		HOEMachineData data = this.server;
		if(data!=null){
			DiaphragmalElectrolyzerData ded = (DiaphragmalElectrolyzerData)data;
			return ded.exchangeDiaphragm(n);
		}else{
			return null;//Derp \o/
		}
	}
	public ItemStack exchangeAnode(ItemStack n){
		HOEMachineData data = this.server;
		if(data!=null){
			DiaphragmalElectrolyzerData ded = (DiaphragmalElectrolyzerData)data;
			return ded.exchangeAnode(n);
		}else{
			return null;//Derp \o/
		}
	}	
	
	
	
	

}
