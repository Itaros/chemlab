package ru.itaros.chemlab.client.ui.special;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.hoe.gui.HOESlotType;
import ru.itaros.hoe.gui.MachineSlot;
import ru.itaros.hoe.gui.ReadonlySlot;
import ru.itaros.hoe.gui.UniversalSlot;
import ru.itaros.hoe.tiles.IUniversalInventory;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GasChimneyContainer extends HOEContainer {

	public GasChimneyContainer(InventoryPlayer playerInv,
			MachineTileEntity tile) {
		super(playerInv,tile);
	}

	
	
	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIGasChimney.class;
	}


	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}


	@Override
	public void bindSlots() {
		super.bindSlots();
		try{
		INBOUND=new MachineSlot((IInventory) tile,0,17+HOEContainer.xOffset,17,HOESlotType.INPUT);
		OUTBOUND=new MachineSlot((IInventory) tile,1,17+HOEContainer.xOffset,54,HOESlotType.OUTPUT);
		
		addSlotToContainer(INBOUND);//INBOUND
		addSlotToContainer(OUTBOUND);//OUTBOUND
		
		
		this.addHOESlotToContainer(new UniversalSlot((IUniversalInventory) tile,-1,48+HOEContainer.xOffset,37));
		this.addHOESlotToContainer(new UniversalSlot((IUniversalInventory) tile,-2,112+HOEContainer.xOffset,37));
		}catch(ClassCastException e){
			System.err.println("Incorrect tile type. IInventory expected from "+tile.getClass().getName());
		}
	}		
	
}
