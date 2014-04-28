package ru.itaros.chemlab.minecraft.tileentity;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class DiaphragmalElectrolyzerTileEntity extends MachineCrafterTileEntity {

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineCrafterIO) ChemLab.getIOCollection().getHOEIO("DiaphragmalElectrolyzerIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines) {
		DiaphragmalElectrolyzerData data = new DiaphragmalElectrolyzerData();
		machines.injectCustomData(data);
		return data;
	}
	
	public ItemStack exchangeDiaphragm(ItemStack n){
		HOEData data = this.server;
		if(data!=null){
			DiaphragmalElectrolyzerData ded = (DiaphragmalElectrolyzerData)data;
			return ded.exchangeDiaphragm(n);
		}else{
			return null;//Derp \o/
		}
	}
	public ItemStack exchangeAnode(ItemStack n){
		HOEData data = this.server;
		if(data!=null){
			DiaphragmalElectrolyzerData ded = (DiaphragmalElectrolyzerData)data;
			return ded.exchangeAnode(n);
		}else{
			return null;//Derp \o/
		}
	}	
	
	
	
	

}
