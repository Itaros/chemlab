package ru.itaros.chemlab.client.ui.common;

import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.debug.RecipeSetSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ProgrammerSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class HOEContainer extends Container {

	ProgrammerSlot psio;
	
	private Slot INBOUND,OUTBOUND;
	
	
	public HOEContainer(InventoryPlayer playerInv, MachineCrafterTileEntity tile){
		//this(playerInv,(BiogrinderTileEntity)tile);
		
		INBOUND=new Slot(tile,0,17,16);
		OUTBOUND=new Slot(tile,1,17,54);
		
		addSlotToContainer(INBOUND);//INBOUND
		addSlotToContainer(OUTBOUND);//OUTBOUND
		
		addSlotToContainer(new ReadonlySlot(tile,-1,48,36));//INBOUND 1
		addSlotToContainer(new ReadonlySlot(tile,-2,112,36));//OUTBOUND 1
		
		psio = new ProgrammerSlot(tile,17,29);
		addSlotToContainer(psio);//PROGRAMMER IO

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


	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotid) {
		int OUTPUT=3;
		
		ItemStack itemstack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotid);
		
		if(slot==null){return null;}
		ItemStack itemstack1 = slot.getStack();
		itemstack = itemstack1.copy();

		if(slot==OUTBOUND){
			if (!this.mergeItemStack(itemstack1, OUTPUT+1, OUTPUT+36+1, true))
			{
			return null;
			}

			slot.onSlotChange(itemstack1, itemstack);
		}else if(slot!=OUTBOUND && slot!=INBOUND && slotid!=-1 && slotid!=-2){
			if (!this.mergeItemStack(itemstack1, INBOUND.slotNumber, INBOUND.slotNumber+1, false))
			{
			return null;
			}
			
			slot.onSlotChange(itemstack1, itemstack);
		}
		
		
		if (itemstack1.stackSize == 0)
		{
		slot.putStack((ItemStack)null);
		}
		else
		{
		slot.onSlotChanged();
		}

		
		return null;
		
	}	
    
    
    
    

    

}
