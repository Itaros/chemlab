package ru.itaros.hoe.data;

import net.minecraft.item.ItemStack;

public interface IHasReplacableParts {

	public boolean requiresReplacements();
	
	public ItemStack getTypeOfReplacableRequired();
	
	public ItemStack exchangeParts(ItemStack part);
	
}
