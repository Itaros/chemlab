package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class AirCollectorContainer extends HOEContainer {

	public static final int ID = 10;
	
	public AirCollectorContainer(InventoryPlayer playerInv, MachineTileEntity tile) {
		super(playerInv, tile);
	}

	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIAirCollector.class;
	}
}
