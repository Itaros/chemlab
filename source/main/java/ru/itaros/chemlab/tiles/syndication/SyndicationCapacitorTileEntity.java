package ru.itaros.chemlab.tiles.syndication;

import net.minecraft.entity.player.EntityPlayer;
import ru.itaros.api.chemlab.syndication.utilities.ISyndicationCapacitor;
import ru.itaros.api.chemlab.syndication.utilities.SyndicationUtilityTileEntity;

public class SyndicationCapacitorTileEntity extends
		SyndicationUtilityTileEntity implements ISyndicationCapacitor {

	@Override
	public int storageSize() {
		return 2500;//EMF
	}



}
