package ru.itaros.chemlab.client.ui.special;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.hoe.gui.HOESlotType;
import ru.itaros.hoe.gui.MachineSlot;
import ru.itaros.hoe.gui.UniversalSlot;
import ru.itaros.hoe.tiles.IUniversalInventory;
import ru.itaros.hoe.tiles.MachineTileEntity;

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
		super.bindSlots();
		
		//INBOUND=new MachineSlot((IInventory) tile,0,17,17,HOESlotType.INPUT);
		//OUTBOUND=new MachineSlot((IInventory) tile,1,17,54,HOESlotType.OUTPUT);
		
		//addSlotToContainer(INBOUND);//INBOUND
		//addSlotToContainer(OUTBOUND);//OUTBOUND
		
		
		this.addHOESlotToContainer(new UniversalSlot((IUniversalInventory) tile,-1,48+32,37));//In 1
		this.addHOESlotToContainer(new UniversalSlot((IUniversalInventory) tile,-2,48+32,60));//In 2
		
		this.addHOESlotToContainer(new UniversalSlot((IUniversalInventory) tile,-11,112,37));//Out 1
		this.addHOESlotToContainer(new UniversalSlot((IUniversalInventory) tile,-12,112,60));//Out 2
		//addSlotToContainer(new ReadonlySlot((IInventory) tile,-1,48+32,37));
		//addSlotToContainer(new ReadonlySlot((IInventory) tile,-2,112,37));//OUTBOUND 1
		
	}		
	
}
