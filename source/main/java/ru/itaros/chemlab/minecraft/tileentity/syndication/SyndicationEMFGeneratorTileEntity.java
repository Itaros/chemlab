package ru.itaros.chemlab.minecraft.tileentity.syndication;

import net.minecraft.entity.player.EntityPlayer;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationEMFGeneratorData;
import ru.itaros.chemlab.hoe.syndication.SyndicationEMFGeneratorIO;
import ru.itaros.chemlab.hoe.syndication.SyndicationHubIO;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

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
