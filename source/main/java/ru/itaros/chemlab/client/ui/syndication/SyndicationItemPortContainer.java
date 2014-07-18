package ru.itaros.chemlab.client.ui.syndication;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationItemPortTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.CopierSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.HOESlotType;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.MachineSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;

public class SyndicationItemPortContainer extends HOEContainer {

	
	public SyndicationItemPortContainer(InventoryPlayer playerInv,
			MachineTileEntity tile) {
		super(playerInv, tile);
	}

	
	
	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUISyndicationItemPort.class;
	}	
	
	public static final int SLOTID_FILTCOMM=-6;
	Slot filterCommiter_slot;
	
	Slot server_filter_slot;
	@Override
	public void bindSlots() {
		SyndicationItemPortTileEntity inventory = (SyndicationItemPortTileEntity)tile;
		
		filterCommiter_slot = new CopierSlot(inventory,SLOTID_FILTCOMM,145,37);
		addSlotToContainer(filterCommiter_slot);
		
		server_filter_slot = new ReadonlySlot(inventory,-5,80,37);
		addSlotToContainer(server_filter_slot);		
		
		

		INBOUND=new MachineSlot((IInventory) tile,0,17,17,HOESlotType.INPUT);
		OUTBOUND=new MachineSlot((IInventory) tile,1,17,54,HOESlotType.OUTPUT);
		
		addSlotToContainer(INBOUND);//INBOUND
		addSlotToContainer(OUTBOUND);//OUTBOUND
		
		addSlotToContainer(new ReadonlySlot((IInventory) tile,-1,48,37));

				
		
		
	}

	
	
	
}
