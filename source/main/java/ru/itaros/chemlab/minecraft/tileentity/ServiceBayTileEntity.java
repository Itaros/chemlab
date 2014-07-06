package ru.itaros.chemlab.minecraft.tileentity;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.ServiceBayData;
import ru.itaros.chemlab.minecraft.tileentity.connectome.ServiceBayConnectome;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class ServiceBayTileEntity extends MachineTileEntity {


	private ServiceBayConnectome connectome=new ServiceBayConnectome();
	
	public ServiceBayTileEntity(){
		super();
	}

	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub

	}

	public void updateConnectome(){
		connectome.reconfigureHOEBiPolar(this);
	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		ServiceBayData sbd= new ServiceBayData();
		sbd.setOwnerFingerprint(fingerprint);
		machines.injectCustomData(sbd);
		return sbd;
	}

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("ServiceBayIO");
	}

}
