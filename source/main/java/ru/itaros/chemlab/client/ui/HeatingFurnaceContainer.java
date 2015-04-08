package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.hoe.gui.HOESlotType;
import ru.itaros.hoe.gui.UniversalSlot;
import ru.itaros.hoe.tiles.IUniversalInventory;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class HeatingFurnaceContainer extends HOEContainer {
	
	public HeatingFurnaceContainer(InventoryPlayer playerInv, MachineTileEntity tile) {
		super(playerInv, tile);
	}

	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIHeatingFurnace.class;
	}	
	
	@Override
	public void bindSlots() {
		super.bindSlots();
		
		IInventory inv = (IInventory)tile;
		
		Slot slot_in = new Slot(inv, 0, xOffset+16, 17);
		
		addSlotToContainer(slot_in);
		
		IUniversalInventory iui = (IUniversalInventory)tile;
		
		//HOE Slots
		addHOESlotToContainer(new UniversalSlot(iui,-1,xOffset+48,36).setType(HOESlotType.INPUT));//INBOUND 1

		
	}
}
