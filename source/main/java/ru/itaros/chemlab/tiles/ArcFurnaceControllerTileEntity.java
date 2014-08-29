package ru.itaros.chemlab.tiles;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.blocks.multiblock.IMultiblockController;
import ru.itaros.chemlab.blocks.multiblock.MBAssociativeDataPayload;
import ru.itaros.chemlab.blocks.multiblock.MBDefinitionArcFurnace;
import ru.itaros.chemlab.hoe.data.ArcFurnaceControllerData;
import ru.itaros.chemlab.loader.MultiblockLoader;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class ArcFurnaceControllerTileEntity extends MachineTileEntity implements IMultiblockController{

	private static final float MAX_HEATRESIST = 1600F;
	private static final float MAX_VOLRESIST = 1F;

	public ArcFurnaceControllerTileEntity(){
		super();
	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines,
			HOEDataFingerprint fingerprint) {
		ArcFurnaceControllerData sbd= new ArcFurnaceControllerData();
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
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("ArcFurnaceControllerIO");
	}

	private boolean isDecomposed=true;
	
	@Override
	public void notifyDecomposition() {
		isDecomposed=true;
	}
	@Override
	public void notifyComposition() {
		isDecomposed=false;
	}

	public void setValues(MBAssociativeDataPayload payload) {
		//tempresist
		//volresist
		Integer tempresist = (Integer) payload.get("tempresist");
		Integer volresist = (Integer) payload.get("volresist");

		if(tempresist==null){tempresist=0;}
		if(volresist==null){volresist=0;}
		
		//Calculating factor
		float tempResistFactor=(float)tempresist/(float)MBDefinitionArcFurnace.DEFID_INSULATION;
		float volumeResistFactor=(float)volresist/(float)MBDefinitionArcFurnace.DEFID_CHASSIS;
		
		//Enforce postload. It is already populated
		this.onPostLoad();
		
		//Pushing factors to HOE
		ArcFurnaceControllerData data = (ArcFurnaceControllerData) this.getServerData();
		data.setHeatResistance(tempResistFactor*MAX_HEATRESIST/MultiblockLoader.arcFurnace.getInsulationLevels());
		data.setVolumeResistance(volumeResistFactor*MAX_VOLRESIST/MultiblockLoader.arcFurnace.getChassisLevels());
	}

}
