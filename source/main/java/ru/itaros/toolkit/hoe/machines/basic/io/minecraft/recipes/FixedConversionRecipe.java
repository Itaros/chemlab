package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import net.minecraft.item.ItemStack;

public class FixedConversionRecipe extends Recipe {
	
	public FixedConversionRecipe
	(
			int timeReq,
			int powerReq,
			ItemStack[] gridInput,ItemStack[] gridOutput
	){
		this.gridInput=gridInput;
		this.gridOutput=gridOutput;
		
		this.timeReq=timeReq;
		this.powerReq=powerReq;
	}
	
	protected ItemStack[] gridInput;
	protected ItemStack[] gridOutput;
	
	protected int timeReq;
	protected int powerReq;
	
	@Override
	public int getIncomingSlots() {
		return gridInput.length;
	}
	@Override
	public int getOutcomingSlots() {
		return gridOutput.length;
	}
}
