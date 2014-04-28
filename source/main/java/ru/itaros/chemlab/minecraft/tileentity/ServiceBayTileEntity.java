package ru.itaros.chemlab.minecraft.tileentity;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.ServiceBayData;
import ru.itaros.chemlab.minecraft.tileentity.connectome.ServiceBayConnectome;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

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
	protected HOEMachineData acquareData(HOEMachines machines) {
		ServiceBayData sbd= new ServiceBayData();
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
