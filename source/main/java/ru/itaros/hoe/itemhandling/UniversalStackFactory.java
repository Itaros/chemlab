package ru.itaros.hoe.itemhandling;

import ru.itaros.hoe.fluid.HOEFluidStack;
import net.minecraft.item.ItemStack;

public class UniversalStackFactory {

	public static IUniversalStack wrap(ItemStack stack){
		return new UniversalItemStack(stack);
	}
	public static IUniversalStack wrap(HOEFluidStack stack){
		return new UniversalFluidStack(stack);
	}	
	
}
