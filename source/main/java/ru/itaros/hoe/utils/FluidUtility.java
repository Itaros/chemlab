package ru.itaros.hoe.utils;

import ru.itaros.hoe.fluid.HOEFluidStack;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class FluidUtility {

	
	public static void writeFluidStackToNBT(FluidStack stack, NBTTagCompound nbt, String tag){
		if(stack!=null){
			NBTTagCompound nbt2 = new NBTTagCompound();
			stack.writeToNBT(nbt2);
			nbt.setTag(tag, nbt2);
		}
	}
	
	public static FluidStack readFluidStackFromNBT(NBTTagCompound nbt, String tag){
		if(nbt.hasKey(tag)){
			NBTTagCompound nbt2 = nbt.getCompoundTag(tag);
			FluidStack stack = FluidStack.loadFluidStackFromNBT(nbt2);
			return stack;
		}else{
			return null;
		}
	}
	

	
	
	
	
	
	//TODO: Test them
	
	/*
	 * This method doesn't check for item equality and nulls
	 */
	public static void mergeInto(FluidStack target, FluidStack source, int cap){
		int diff = cap-target.amount;
		int operative;
		if(source.amount>diff){
			operative=diff;
		}else{
			operative=source.amount;
		}
		source.amount-=operative;
		target.amount+=operative;
	}	
	
	
	
	public static FluidStack tryToPutIn(FluidStack target, FluidStack source, int cap){
		if(source==null){return null;}
		if(target==null){
			target=source.copy();
			return null;
		}else{
			if(target.isFluidEqual(source)){
				FluidUtility.mergeInto(target, source,cap);
				//return FluidUtility.verify(source);
				return source;
			}else{
				return source;
			}
		}		
	}
	
	
	public static FluidStack tryToGetOut(FluidStack target, FluidStack source, int cap){
		if(target==null){
			if(source==null){
				return null;
			}
			FluidStack tmp = source.copy();
			source.amount=0;
			return tmp;
		}else{
			if(source==null){
				return target;
			}
			if(target.isFluidEqual(source)){
				FluidUtility.mergeInto(target, source,cap);
				return target;
			}else{
				return target;
			}
		}
	}


	
	
	
}
