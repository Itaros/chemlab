package ru.itaros.hoe.toolkit.data.descriptor;

import net.minecraft.item.ItemStack;

public interface IHOEMultiInventoryMachine extends IHOEActiveMachine {

	public ItemStack get_in(int i);
	public ItemStack get_out(int i);
	
	public ItemStack[] get_in();
	public ItemStack[] get_out();
	public IHOEMultiInventoryMachine set_in(int i, ItemStack stack);
	public IHOEMultiInventoryMachine set_out(int i, ItemStack stack);	
	
	
}
