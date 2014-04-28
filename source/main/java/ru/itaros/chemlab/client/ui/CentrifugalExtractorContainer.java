package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class CentrifugalExtractorContainer extends HOEContainer {

	public CentrifugalExtractorContainer(InventoryPlayer playerInv,
			MachineCrafterTileEntity tile) {
		super(playerInv, tile);
	}

	public static final int ID = 1;
	
	
	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUICentrifugalExtractor.class;
	}	   
	
	
}
