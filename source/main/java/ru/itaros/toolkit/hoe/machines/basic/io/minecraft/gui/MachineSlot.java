package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class MachineSlot extends Slot {

	private HOESlotType type;
	
	public final HOESlotType getType() {
		return type;
	}


	public MachineSlot(IInventory par1iInventory, int par2, int par3, int par4, HOESlotType type) {
		super(par1iInventory, par2, par3, par4);
		this.type=type;
	}

}
