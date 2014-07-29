package ru.itaros.hoe.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

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
		
		target.setItemDamage(source.getItemDamage());
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
	public static ItemStack[] syncItemStacks(ItemStack[] target, ItemStack[] source){
		//nullchecks
		if(source==null){target=null;return target;}
		//size aligning
		if(target==null || source.length!=target.length){
			target = new ItemStack[source.length];
		}
		//normal sync
		int size = target.length;
		for(int i = 0 ; i < size ; i++){
			target[i]=syncItemStacks(target[i], source[i]);
		}
		
		return target;
	}
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
	
	
	
	public static void writeItemStacksToNBT(ItemStack[] stack, NBTTagCompound nbt, String tag){
		if(stack!=null){
			NBTTagCompound nbt2 = new NBTTagCompound();
			NBTTagList nbts = new NBTTagList();
			for(int i = 0 ; i < stack.length ; i++){
				ItemStack st = stack[i];
				NBTTagCompound it = new NBTTagCompound();
				if(st!=null){
					st.writeToNBT(it);
				}
				nbts.appendTag(it);
			}
			nbt2.setTag("array", nbts);	
			nbt.setTag(tag, nbt2);			
		}
	}
	public static void writeItemStackToNBT(ItemStack stack, NBTTagCompound nbt, String tag){
		if(stack!=null){
			NBTTagCompound nbt2 = new NBTTagCompound();
			stack.writeToNBT(nbt2);
			nbt.setTag(tag, nbt2);
		}
	}
	
	public static ItemStack[] readItemStacksFromNBT(ItemStack[] array,NBTTagCompound nbt, String tag){
		if(nbt.hasKey(tag)){
			NBTTagCompound nbt2 = nbt.getCompoundTag(tag);
			NBTTagList nbts = nbt2.getTagList("array", Constants.NBT.TAG_COMPOUND);
			for(int i = 0 ; i < nbts.tagCount() ; i++){
				NBTTagCompound it = nbts.getCompoundTagAt(i);
				array[i]=ItemStack.loadItemStackFromNBT(it);
			}
			return array;
		}else{
			return array;
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
	
	
	
	
	public static ItemStack tryToPutIn(StackTransferTuple tuple, boolean ignoreMetadata, ItemStack filter){
		//ItemStack tuple.stack1, ItemStack tuple.stack2
		if(tuple.stack2==null){return null;}
		if(filter!=null && !tuple.stack2.isItemEqual(filter)){return tuple.stack2;}
		if(tuple.stack1==null){
			tuple.stack1=tuple.stack2.copy();
			return null;
		}else{
			if(isItemEqualStackSizeProtection(tuple.stack1, tuple.stack2, ignoreMetadata)){
				StackUtility.mergeInto(tuple.stack1, tuple.stack2);
				return StackUtility.verify(tuple.stack2);
			}else{
				return tuple.stack2;
			}
		}		
	}
	
	public static boolean isItemEqual(ItemStack stack1, ItemStack stack2, boolean ignoreMetadata){
		if(ignoreMetadata){
			return stack1.getItem()==stack2.getItem();
		}else{
			return (stack1.getItem()==stack2.getItem())&&(stack1.getItemDamage()==stack2.getItemDamage());
		}
	}	
	public static boolean isItemEqualStackSizeProtection(ItemStack stack1, ItemStack stack2, boolean ignoreMetadata){
		if(ignoreMetadata){
			boolean metadiffs = stack1.getItemDamage()!=stack2.getItemDamage();
			if(!metadiffs){
				return stack1.getItem()==stack2.getItem();
			}else{
				if(stack1.stackSize==0){
					return stack1.getItem()==stack2.getItem();
				}else{
					return false;
				}
			}
		}else{
			return (stack1.getItem()==stack2.getItem())&&(stack1.getItemDamage()==stack2.getItemDamage());
		}
	}
	
	public static ItemStack tryToGetOut(StackTransferTuple tuple, ItemStack filter){
		//ItemStack tuple.stack1, ItemStack tuple.stack2
		if(tuple.stack1==null){
			if(tuple.stack2==null){
				return null;
			}
			if(filter!=null && !tuple.stack2.isItemEqual(filter)){return null;}
			ItemStack tmp = tuple.stack2.copy();
			//tuple.stack2.stackSize=0;
			tuple.stack2=null;
			//tuple.stack2=verify(tuple.stack2);
			return tmp;//verify(tmp);
		}else{
			if(tuple.stack2==null){
				//No items to transfer
				return tuple.stack1;
			}
			if(filter!=null && !tuple.stack2.isItemEqual(filter)){return tuple.stack1;}
			if(tuple.stack1.isItemEqual(tuple.stack2)){
				StackUtility.mergeInto(tuple.stack1, tuple.stack2);
				
				return tuple.stack1;
			}else{
				return tuple.stack1;
			}
		}
	}

	public static ItemStack decrementStack(ItemStack stack, int amount) {
		if(stack==null){return null;}
		stack.stackSize-=amount;
		return verify(stack);
	}
	public static ItemStack incrementStack(ItemStack stack, int amount) {
		if(stack==null){return null;}
		stack.stackSize+=amount;
		return verify(stack);
	}	
	
	
	/*
	 * Gets NBT from Items \o/
	 */
	public static NBTTagCompound getTags(ItemStack i){
		NBTTagCompound tag = i.getTagCompound();
		if(tag==null){
			tag = new NBTTagCompound();
			i.setTagCompound(tag);
		}
		return tag;
	}
	
	
	
	
	
	
	
}
