package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class BloomeryContainer extends HOEContainer {
	
	public BloomeryContainer(InventoryPlayer playerInv, MachineTileEntity tile) {
		super(playerInv, tile);
	}

	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIBloomery.class;
	}	
	
	@Override
	public void bindSlots() {
		super.bindSlots();
		
	}
	
}
