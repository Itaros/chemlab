package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.debug;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class RecipeSetSlot extends Slot {

	public RecipeSetSlot(IInventory inv, int slot, int x, int y) {
		super(inv, slot, x, y);
		
	}

	@Override
	public void putStack(ItemStack stack) {
		if(stack==null){return;}
		stack=stack.copy();
		stack.stackSize=1;
		super.putStack(stack);
	}

	
	
	
}
