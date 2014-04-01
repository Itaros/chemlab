package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui;

import ru.itaros.chemlab.loader.ItemLoader;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ProgrammerSlot extends Slot {

	public static final int PROGRAMMER_DEFAULT_SLOT = -5;
	
	public ProgrammerSlot(IInventory inv , int x,
			int y) {
		super(inv, PROGRAMMER_DEFAULT_SLOT, x, y);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack.getItem()==ItemLoader.programmer){
			return true;
		}else{
			return false;
		}
	}

	
	
	
	//TODO: should be somehow customized
	
	
	
	
	
}
