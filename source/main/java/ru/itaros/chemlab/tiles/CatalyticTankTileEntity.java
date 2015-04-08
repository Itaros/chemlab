package ru.itaros.chemlab.tiles;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.CatalyticTankData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;

public class CatalyticTankTileEntity extends MachineCrafterTileEntity {

	public CatalyticTankTileEntity(){
		super();
	}
	
	
	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineCrafterIO) ChemLab.getIOCollection().getHOEIO("CatalyticTankIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		CatalyticTankData data = new CatalyticTankData();
		data.setOwnerFingerprint(fingerprint);
		machines.injectCustomData(data);
		return data;
	}
	
//	public ItemStack exchangeCatalyzer(ItemStack n){
//		HOEData data = this.server;
//		if(data!=null){
//			CatalyticTankData ded = (CatalyticTankData)data;
//			return ded.exchangeCatalyzer(n);
//		}else{
//			return null;//Derp \o/
//		}
//	}

	

}
