package ru.itaros.chemlab.tiles.syndication;

import net.minecraft.entity.player.EntityPlayer;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationEMFGeneratorData;
import ru.itaros.chemlab.hoe.io.syndication.SyndicationEMFGeneratorIO;
import ru.itaros.chemlab.hoe.io.syndication.SyndicationHubIO;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class SyndicationEMFGeneratorTileEntity extends MachineTileEntity {

	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		SyndicationEMFGeneratorData sbd= new SyndicationEMFGeneratorData();
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
		return (SyndicationEMFGeneratorIO) ChemLab.getIOCollection().getHOEIO("SyndicationEMFGeneratorIO");
	}

	@Override
	public boolean askControllerToDie(EntityPlayer player){
		return SyndicationPipingTileEntity.utility_askControllerToDie(player, this);
	}	
	
}
