package ru.itaros.chemlab.client.ui.common;

import ru.itaros.chemlab.client.ui.GUIBiogrinder;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.debug.RecipeSetSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class HOEContainer extends Container {

	public HOEContainer(InventoryPlayer playerInv, MachineTileEntity tile){
		//this(playerInv,(BiogrinderTileEntity)tile);
		
		addSlotToContainer(new Slot(tile,0,17,16));//INBOUND
		addSlotToContainer(new Slot(tile,1,17,54));//OUTBOUND
		
		addSlotToContainer(new ReadonlySlot(tile,-1,48,36));//INBOUND 1
		addSlotToContainer(new ReadonlySlot(tile,-2,112,36));//OUTBOUND 1
		
		addSlotToContainer(new RecipeSetSlot(tile,10,17,42));//OUTBOUND

		bindPlayerInventory(playerInv);		
	}


	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	
	
	
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 9; j++) {
                        addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                                        8 + j * 18, 84 + i * 18));
                }
        }

        for (int i = 0; i < 9; i++) {
                addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }	
    
    
	public static Class<? extends GUIHOEClassicalMachine> getGUIType() {
		return GUIBiogrinder.class;
	}	   
    

}
