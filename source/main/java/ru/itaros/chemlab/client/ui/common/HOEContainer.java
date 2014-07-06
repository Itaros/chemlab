package ru.itaros.chemlab.client.ui.common;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import ru.itaros.hoe.vanilla.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.CopierSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ProgrammerSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;

public abstract class HOEContainer extends Container {

	private static HashMap<Class<? extends HOEContainer>,Integer> IDs=new HashMap<Class<? extends HOEContainer>,Integer>();
	public static int getID(Class<? extends HOEContainer> c){
		return IDs.get(c);
	}
	public static void setID(Class<? extends HOEContainer> c,int newid){
		IDs.put(c, newid);
		}
	
	ProgrammerSlot psio;
	
	protected Slot INBOUND,OUTBOUND;
	
	protected MachineTileEntity tile;
	
	public HOEContainer(InventoryPlayer playerInv, MachineTileEntity tile){
		//this(playerInv,(BiogrinderTileEntity)tile);
		
		this.tile = tile;
		
		bindSlots();
		
		bindPlayerInventory(playerInv);		
	}
	
	
	public abstract void bindSlots();


	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	
	
	
    @Override
	public ItemStack slotClick(int slotid, int button, int par3,
			EntityPlayer player) {
    	Slot s = slotid>=0 ? (Slot)this.inventorySlots.get(slotid) : null;
    	if(s instanceof CopierSlot){
    		return modifyCopierSlot(s, button, player);
    	}else{
    		return super.slotClick(slotid, button, par3, player);
    	}	
	}
    
    
	private ItemStack modifyCopierSlot(Slot s, int button, EntityPlayer player) {
		ItemStack heldItem = player.inventory.getItemStack();
		if(s==null){return heldItem;}
		
		ItemStack copy;
		if(heldItem==null){
			copy=null;
		}else{
			copy=heldItem.copy();
			copy.stackSize=s.getSlotStackLimit();
		}
		
		s.putStack(copy);
		
		return heldItem;
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
		
		if(itemstack1==null){
			//itemstack=null;
			return null;
		}else{
			itemstack = itemstack1.copy();
		}

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
