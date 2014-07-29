package ru.itaros.hoe.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;


/*
 * This is basic Slot, but it is treated differently by GUI
 */
public class CopierSlot extends Slot {

	public CopierSlot(IInventory inv, int id, int x, int y) {
		super(inv, id, x, y);
	}

	
	private int slotlimit=1;
	
	public void setSlotStackLimit(int newlimit){
		slotlimit=newlimit;
	}
	
	@Override
	public int getSlotStackLimit() {
		return slotlimit;
	}

	

	
}
