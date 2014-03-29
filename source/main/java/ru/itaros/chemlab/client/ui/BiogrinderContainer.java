package ru.itaros.chemlab.client.ui;

import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.debug.RecipeSetSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class BiogrinderContainer extends Container {

	public BiogrinderContainer(InventoryPlayer playerInv, BiogrinderTileEntity bte) {
		addSlotToContainer(new Slot(bte,0,17,16));//INBOUND
		addSlotToContainer(new Slot(bte,1,17,54));//OUTBOUND
		
		addSlotToContainer(new ReadonlySlot(bte,-1,48,36));//INBOUND 1
		addSlotToContainer(new ReadonlySlot(bte,-2,112,36));//OUTBOUND 1
		
		addSlotToContainer(new RecipeSetSlot(bte,10,17,42));//OUTBOUND

		bindPlayerInventory(playerInv);
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	
	
	//TODO: EXTRACT
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
	
}
