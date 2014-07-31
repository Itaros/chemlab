package ru.itaros.hoe.data;

import ru.itaros.hoe.itemhandling.IUniversalStack;

public interface IHOEMultiInventoryMachine extends IHOEActiveMachine {

	public IUniversalStack get_in(int i);
	public IUniversalStack get_out(int i);
	
	public IUniversalStack[] get_in();
	public IUniversalStack[] get_out();
	public IHOEMultiInventoryMachine set_in(int i, IUniversalStack stack);
	public IHOEMultiInventoryMachine set_out(int i, IUniversalStack stack);	
	
	
}
