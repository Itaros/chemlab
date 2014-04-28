package ru.itaros.chemlab.client.ui;

import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class BiogrinderContainer extends HOEContainer {

	public static final int ID = 0;
	
	public BiogrinderContainer(InventoryPlayer playerInv, MachineCrafterTileEntity tile) {
		super(playerInv, tile);
	}

	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIBiogrinder.class;
	}	   
	
}
