package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FixedConversionRecipe extends Recipe {
	
	public FixedConversionRecipe
	(
			int timeReq,
			int powerReq,
			ItemStack[] gridInput,ItemStack[] gridOutput, String name
	){
		super(name);
		this.gridInput=gridInput;
		this.gridOutput=gridOutput;
		
		this.timeReq=timeReq;
		this.powerReq=powerReq;
	}
	
	protected ItemStack[] gridInput;
	protected ItemStack[] gridOutput;
	
	protected int timeReq;
	protected int powerReq;//TODO: is not used
	
	@Override
	public int getIncomingSlots() {
		return gridInput.length;
	}
	@Override
	public int getOutcomingSlots() {
		return gridOutput.length;
	}
	
	@Override
	public Item[] getIncomingStricttypes() {
		Item[] result = new Item[gridInput.length];
		int x = -1;
		for(ItemStack stack : gridInput){
			x++;
			result[x]=stack.getItem();
		}
		return result;
	}
	@Override
	public Item[] getOutcomingStricttypes() {
		Item[] result = new Item[gridOutput.length];
		int x = -1;
		for(ItemStack stack : gridOutput){
			x++;
			result[x]=stack.getItem();
		}
		return result;
	}
	public int getTicksRequared() {
		return timeReq;
	}
}
