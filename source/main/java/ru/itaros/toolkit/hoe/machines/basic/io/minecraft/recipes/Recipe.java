package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import net.minecraft.item.Item;

public abstract class Recipe {

	private String name;
	public String getName(){
		return name;
	}
	
	public Recipe(String name) {
		this.name=name;
	}
	public abstract int getIncomingSlots();
	public abstract int getOutcomingSlots();
	
	public abstract Item[] getIncomingStricttypes();
	public abstract Item[] getOutcomingStricttypes();
}
