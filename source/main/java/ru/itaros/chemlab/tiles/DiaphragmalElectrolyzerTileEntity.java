package ru.itaros.chemlab.tiles;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.DiaphragmalElectrolyzerData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;

public class DiaphragmalElectrolyzerTileEntity extends MachineCrafterTileEntity {

	public DiaphragmalElectrolyzerTileEntity(){
		super();
	}
	
	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineCrafterIO) ChemLab.getIOCollection().getHOEIO("DiaphragmalElectrolyzerIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		DiaphragmalElectrolyzerData data = new DiaphragmalElectrolyzerData();
		data.setOwnerFingerprint(fingerprint);
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
