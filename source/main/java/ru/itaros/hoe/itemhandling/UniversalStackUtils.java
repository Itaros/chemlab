package ru.itaros.hoe.itemhandling;

import ru.itaros.api.hoe.exceptions.HOEInvalidUniStackFactory;
import ru.itaros.hoe.fluid.HOEFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class UniversalStackUtils {

	public static IUniversalStack copyUniversalStack(IUniversalStack stack){
		return stack==null?null:stack.copy();
	}

	public static IUniversalStack[] convert(ItemStack[] itemStacks) {
		IUniversalStack[] stack = new IUniversalStack[itemStacks.length];
		for(int i=0;i<itemStacks.length;i++){
			ItemStack s = itemStacks[i];
			UniversalItemStack us = new UniversalItemStack(s);
			stack[i]=us;
		}
		return stack;
	}

	public static IUniversalStack convert(ItemStack itemStack) {
		return new UniversalItemStack(itemStack);
	}

	public static IUniversalStack copyStack(IUniversalStack stack) {
		if(stack==null){return null;}
		return stack.copy();
	}
	
	public static IUniversalStack loadItemStackFromNBT(NBTTagCompound nbt) {
		NBTTagCompound inner = (NBTTagCompound) nbt.getTag("inner");
		if(inner!=null){
			String factory = inner.getString("factory");
			IUniversalStack f;
			if(factory.equals(UniversalItemStack.class.getSimpleName())){
				f = new UniversalItemStack((ItemStack)null);
			}else if(factory.equals(UniversalFluidStack.class.getSimpleName())){
				f = new UniversalFluidStack((HOEFluidStack)null);
			}else if(factory.isEmpty()){
				f = null;//No Stack
			}else{
				throw new HOEInvalidUniStackFactory();
			}
			if(f!=null){
				f.readProxyFromNBT(inner);
			}
			return f;
		}
		return null;
	}

	public static Object getSafeProxy(IUniversalStack stack) {
		return stack==null?null:stack.getProxy();
	}

	public static IUniversalStack setSafeProxy(IUniversalStack stack,
			ItemStack proxy) {
		if(stack==null){
			return new UniversalItemStack(proxy);
		}else{
			return stack.setProxy(proxy);
		}		
	}
	public static IUniversalStack setSafeProxy(IUniversalStack stack,
			FluidStack proxy) {
		HOEFluidStack hfl = new HOEFluidStack(proxy);
		return setSafeProxy(stack,hfl);
	}
	public static IUniversalStack setSafeProxy(IUniversalStack stack,
			HOEFluidStack proxy) {
		if(stack==null){
			return new UniversalFluidStack(proxy);
		}else{
			return stack.setProxy(proxy);
		}		
	}
	/*
	 * Verifies IUS and its proxy
	 */
	public static boolean isNull(IUniversalStack comparable) {
		return comparable==null?true:comparable.getProxy()==null;
	}
	
	
}
