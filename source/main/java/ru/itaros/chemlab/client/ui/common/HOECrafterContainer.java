package ru.itaros.chemlab.client.ui.common;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ProgrammerSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class HOECrafterContainer extends HOEContainer {

	public HOECrafterContainer(InventoryPlayer playerInv, MachineTileEntity tile) {
		super(playerInv, tile);
	}

	@Override
	public void bindSlots() {
		MachineCrafterTileEntity crafter = (MachineCrafterTileEntity)tile;
		
		INBOUND=new Slot(crafter,0,17,16);
		OUTBOUND=new Slot(crafter,1,17,54);
		
		addSlotToContainer(INBOUND);//INBOUND
		addSlotToContainer(OUTBOUND);//OUTBOUND
		
		addSlotToContainer(new ReadonlySlot(crafter,-1,48,36));//INBOUND 1
		addSlotToContainer(new ReadonlySlot(crafter,-2,112,36));//OUTBOUND 1
		
		psio = new ProgrammerSlot(crafter,17,29);
		addSlotToContainer(psio);//PROGRAMMER IO

	}

}
