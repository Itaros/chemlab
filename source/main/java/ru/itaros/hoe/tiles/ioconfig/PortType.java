package ru.itaros.hoe.tiles.ioconfig;

import net.minecraft.item.ItemStack;

public enum PortType {
	NOIDENTITY,//Technical
	CLOSED,
	NOTHING,
	ITEM,
	FLUID,
	POWER;
	
	private ItemStack relevant;
	
	public void setRelevantItem(ItemStack item){
		relevant=item;
	}
	public ItemStack getRelevantItem(){
		return relevant==null?null:relevant.copy();
	}
}
