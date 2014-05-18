package ru.itaros.chemlab.client.ui.special;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class HVLCFillerContainer extends HOEContainer {

	public HVLCFillerContainer(InventoryPlayer playerInv,
			MachineTileEntity tile) {
		super(playerInv,tile);
	}

	
	
	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIHVLCFiller.class;
	}


	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}


	@Override
	public void bindSlots() {
		INBOUND=new Slot((IInventory) tile,0,17,17);
		OUTBOUND=new Slot((IInventory) tile,1,17,54);
		
		addSlotToContainer(INBOUND);//INBOUND
		addSlotToContainer(OUTBOUND);//OUTBOUND
		
		addSlotToContainer(new ReadonlySlot((IInventory) tile,-1,48+32,37));
		addSlotToContainer(new ReadonlySlot((IInventory) tile,-2,112,37));//OUTBOUND 1
		
	}		
	
}
