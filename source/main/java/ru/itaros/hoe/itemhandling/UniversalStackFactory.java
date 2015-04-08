package ru.itaros.hoe.itemhandling;

import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluidStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class UniversalStackFactory {

	private static IUniversalStack wrap(ItemStack stack){
		return new UniversalItemStack(stack);
	}
	private static IUniversalStack wrap(HOEFluidStack stack){
		return new UniversalFluidStack(stack);
	}	
	
	public static IUniversalStack construct(Object stack, int amount){
		if(stack instanceof Item){
			IUniversalStack stacki = new UniversalItemStack((Item)stack);
			stacki.setStackSize(amount);
			return stacki;
		}else if(stack instanceof HOEFluid){
			IUniversalStack stackf = new UniversalFluidStack((HOEFluid)stack);
			stackf.setStackSize(amount);
			return stackf;
		}else{
			if(stack==null){
				return null;
			}
			throw new IllegalArgumentException("Unexpected type: "+stack.getClass().getName());
		}
	}	
	
	public static IUniversalStack wrap(Object stack){
		if(stack instanceof ItemStack){
			return wrap((ItemStack)stack);
		}else if(stack instanceof HOEFluidStack){
			return wrap((HOEFluidStack)stack);
		}else{
			if(stack==null){
				return null;
			}
			throw new IllegalArgumentException("Unexpected type: "+stack.getClass().getName());
		}
	}
}
