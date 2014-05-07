package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class FluidCompressorContainer extends HOEContainer {
	public FluidCompressorContainer(InventoryPlayer playerInv,
			MachineCrafterTileEntity tile) {
		super(playerInv, tile);
	}

	public static final int ID = 11;
	
	
	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIFluidCompressor.class;
	}	
}