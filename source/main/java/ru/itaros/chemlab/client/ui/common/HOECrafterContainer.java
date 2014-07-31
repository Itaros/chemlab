package ru.itaros.chemlab.client.ui.common;

import java.util.Iterator;
import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.gui.HOESlotType;
import ru.itaros.hoe.gui.MachineSlot;
import ru.itaros.hoe.gui.ProgrammerSlot;
import ru.itaros.hoe.gui.ReadonlySlot;
import ru.itaros.hoe.gui.UniversalSlot;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class HOECrafterContainer extends HOEContainer {

	
	public HOECrafterContainer(InventoryPlayer playerInv, MachineTileEntity tile) {
		super(playerInv, tile);
	}

	@Override
	public void bindSlots() {	
		super.bindSlots();
		
		MachineCrafterTileEntity crafter = (MachineCrafterTileEntity)tile;
		
		INBOUND=new MachineSlot(crafter,0,16,17,HOESlotType.INPUT);
		OUTBOUND=new MachineSlot(crafter,1,16,55,HOESlotType.OUTPUT);
		
		addSlotToContainer(INBOUND);//INBOUND
		addSlotToContainer(OUTBOUND);//OUTBOUND
		
		//Adding inbounds slots
		int stepping = 19;
		int y_start = 36-stepping;
		for(int i = 0 ; i < 3 ; i++){
			addHOESlotToContainer(new UniversalSlot(crafter,-1-i,48,y_start+(stepping*i)).setType(HOESlotType.INPUT));//INBOUND 1
		}
		//Adding outbound slots
		for(int i = 0 ; i < 3 ; i++){		
			addHOESlotToContainer(new UniversalSlot(crafter,-11-i,112,y_start+(stepping*i)).setType(HOESlotType.OUTPUT));//OUTBOUND 1
		}
		
		psio = new ProgrammerSlot(crafter,16,29);
		addSlotToContainer(psio);//PROGRAMMER IO

	}

	


	@Override
	public void detectAndSendChanges() {
		//Polling Data ISynchroport dirty state...
		HOEMachineData data = tile.getServerData();
		if(data!=null && data instanceof ISynchroportItems){
			boolean wasDirty = ((ISynchroportItems)data).pollDirty();
			if(wasDirty){
				tile.markDirty();
			}
		}
		//Default operation
		super.detectAndSendChanges();
	}


}
