package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class StackUtility {

	/*
	 * This method doesn't check for item equality and nulls
	 */
	public static void mergeInto(ItemStack target, ItemStack source){
		int max = target.getMaxStackSize();
		int diff = max-target.stackSize;
		int operative;
		if(source.stackSize>diff){
			operative=diff;
		}else{
			operative=source.stackSize;
		}
		source.stackSize-=operative;
		target.stackSize+=operative;
	}

	/*
	 * Convenience method for nullifying empty stacks
	 */
	public static ItemStack verify(ItemStack source) {
		if(source==null){return null;}//Really, lol
		if(source.stackSize<=0){return null;}else{
			return source;
		}
	}
	
	
	/*
	 * Server->Child sync helper
	 */
	public static ItemStack syncItemStacks(ItemStack target, ItemStack source){
		
		if(source==null){
			target=null;
			return target;
		}
		
		if(target==null){
			if(source!=null){
				target=source.copy();
				return target;
			}
			//}else{
			//	target=null;
			//	return target;
			//}
		}
		
		if(target.isItemEqual(source)){
			target.stackSize=source.stackSize;
			return target;
		}else{
			target=source.copy();
			return target;
		}
		
		
	}
	
	
	
	public static void writeItemStackToNBT(ItemStack stack, NBTTagCompound nbt, String tag){
		if(stack!=null){
			NBTTagCompound nbt2 = new NBTTagCompound();
			stack.writeToNBT(nbt2);
			nbt.setTag(tag, nbt2);
		}
	}
	
	public static ItemStack readItemStackFromNBT(NBTTagCompound nbt, String tag){
		if(nbt.hasKey(tag)){
			NBTTagCompound nbt2 = nbt.getCompoundTag(tag);
			ItemStack stack = ItemStack.loadItemStackFromNBT(nbt2);
			return stack;
		}else{
			return null;
		}
	}
	
	
	
	
	public static ItemStack tryToPutIn(StackTransferTuple tuple){
		//ItemStack tuple.stack1, ItemStack tuple.stack2
		if(tuple.stack2==null){return null;}
		if(tuple.stack1==null){
			tuple.stack1=tuple.stack2.copy();
			return null;
		}else{
			if(tuple.stack1.isItemEqual(tuple.stack2)){
				StackUtility.mergeInto(tuple.stack1, tuple.stack2);
				return StackUtility.verify(tuple.stack2);
			}else{
				return tuple.stack2;
			}
		}		
	}
	
	public static ItemStack tryToGetOut(StackTransferTuple tuple){
		//ItemStack tuple.stack1, ItemStack tuple.stack2
		if(tuple.stack1==null){
			if(tuple.stack2==null){
				return null;
			}
			ItemStack tmp = tuple.stack2.copy();
			//tuple.stack2.stackSize=0;
			tuple.stack2=null;
			//tuple.stack2=verify(tuple.stack2);
			return verify(tmp);
		}else{
			if(tuple.stack2==null){
				return tuple.stack1;
			}
			if(tuple.stack1.isItemEqual(tuple.stack2)){
				StackUtility.mergeInto(tuple.stack1, tuple.stack2);
				
				return tuple.stack1;
			}else{
				return tuple.stack1;
			}
		}
	}
	
	
	
	
	
}
