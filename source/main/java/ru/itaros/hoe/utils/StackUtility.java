package ru.itaros.hoe.utils;

import ru.itaros.hoe.fluid.FluidToHOE;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

public class StackUtility {

	/*
	 * This method doesn't check for item equality and nulls
	 */
	public static void mergeInto(IUniversalStack target, IUniversalStack source){
		int max = target.getMaxStackSize();
		int diff = max-target.getStackSize();
		int operative;
		if(source.getStackSize()>diff){
			operative=diff;
		}else{
			operative=source.getStackSize();
		}
		source.decrement(operative);
		target.increment(operative);
		
		target.setItemDamage(source.getItemDamage());
	}
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
	public static void mergeInto(HOEFluidStack stack1, FluidStack source, int max){
		int diff = max-stack1.stackSize;
		int operative;
		if(source.amount>diff){
			operative=diff;
		}else{
			operative=source.amount;
		}
		source.amount-=operative;
		stack1.stackSize+=operative;
		
		//target.setItemDamage(source.getItemDamage());
	}		

	/*
	 * Convenience method for nullifying empty stacks
	 */
	public static IUniversalStack verify(IUniversalStack stack) {
		if(stack==null){return null;}//Really, lol
		if(stack.getStackSize()<=0){return null;}else{
			return stack;
		}
	}
	public static ItemStack verify(ItemStack stack) {
		if(stack==null){return null;}//Really, lol
		if(stack.stackSize<=0){return null;}else{
			return stack;
		}
	}	
	public static FluidStack verify(FluidStack stack) {
		if(stack==null){return null;}//Really, lol
		if(stack.amount<=0){return null;}else{
			return stack;
		}
	}		
	
	/*
	 * Server->Child sync helper
	 */
	public static IUniversalStack[] syncItemStacks(IUniversalStack[] target, IUniversalStack[] source){
		//nullchecks
		if(source==null){target=null;return target;}
		//size aligning
		if(target==null || source.length!=target.length){
			target = new IUniversalStack[source.length];
		}
		//normal sync
		int size = target.length;
		for(int i = 0 ; i < size ; i++){
			target[i]=syncItemStacks(target[i], source[i]);
		}
		
		return target;
	}
	public static IUniversalStack syncItemStacks(IUniversalStack target, IUniversalStack source){
		
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
			target.setStackSize(source.getStackSize());
			return target;
		}else{
			target=source.copy();
			return target;
		}
		
		
	}
	
	
	
	public static void writeItemStacksToNBT(IUniversalStack[] stack, NBTTagCompound nbt, String tag){
		if(stack!=null){
			NBTTagCompound nbt2 = new NBTTagCompound();
			NBTTagList nbts = new NBTTagList();
			for(int i = 0 ; i < stack.length ; i++){
				IUniversalStack st = stack[i];
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
	public static void writeItemStackToNBT(IUniversalStack stack, NBTTagCompound nbt, String tag){
		if(stack!=null){
			NBTTagCompound nbt2 = new NBTTagCompound();
			stack.writeToNBT(nbt2);
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
	
	public static IUniversalStack[] readItemStacksFromNBT(IUniversalStack[] array,NBTTagCompound nbt, String tag){
		if(nbt.hasKey(tag)){
			NBTTagCompound nbt2 = nbt.getCompoundTag(tag);
			NBTTagList nbts = nbt2.getTagList("array", Constants.NBT.TAG_COMPOUND);
			for(int i = 0 ; i < nbts.tagCount() ; i++){
				NBTTagCompound it = nbts.getCompoundTagAt(i);
				array[i]=UniversalStackUtils.loadItemStackFromNBT(it);
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
	public static IUniversalStack readUniversalStackFromNBT(NBTTagCompound nbt, String tag){
		if(nbt.hasKey(tag)){
			NBTTagCompound nbt2 = nbt.getCompoundTag(tag);
			IUniversalStack stack = UniversalStackUtils.loadItemStackFromNBT(nbt2);
			return stack;
		}else{
			return null;
		}
	}	
	
	
	
	
	public static ItemStack tryToPutIn(ItemStackTransferTuple tuple, boolean ignoreMetadata, ItemStack filter){
		//ItemStack tuple.stack1, ItemStack tuple.stack2
		if(tuple.stack2==null){return null;}
		if(filter!=null && !isItemEqual(tuple.stack2,filter,false)){return tuple.stack2;}
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
	public static FluidStack tryToPutIn(FluidStackTransferTuple tuple, FluidStack filter, int max){
		//ItemStack tuple.stack1, ItemStack tuple.stack2
		if(tuple.stack2==null){return null;}
		if(filter!=null && !isItemEqual(tuple.stack2,filter)){return tuple.stack2;}
		if(tuple.stack1==null){
			tuple.stack1=new HOEFluidStack(tuple.stack2.copy());
			return null;
		}else{
			if(isItemEqualStackSizeProtection(tuple.stack1, tuple.stack2)){
				StackUtility.mergeInto(tuple.stack1, tuple.stack2, max);
				return StackUtility.verify(tuple.stack2);
			}else{
				return tuple.stack2;
			}
		}		
	}	
	
	
	public static boolean isItemEqual(IUniversalStack stack1, IUniversalStack stack2, boolean ignoreMetadata){
		if(ignoreMetadata){
			return stack1.getItem()==stack2.getItem();
		}else{
			return (stack1.getItem()==stack2.getItem())&&(stack1.getItemDamage()==stack2.getItemDamage());
		}
	}	
	public static boolean isItemEqual(ItemStack stack1, IUniversalStack stack2,
			boolean ignoreMetadata) {
		if(ignoreMetadata){
			return stack1.getItem()==stack2.getItem();
		}else{
			return (stack1.getItem()==stack2.getItem())&&(stack1.getItemDamage()==stack2.getItemDamage());
		}
	}	
	public static boolean isItemEqual(FluidStack stack1, IUniversalStack stack2) {
		return FluidToHOE.get(stack1.getFluid())==stack2.getItem();
	}		
	public static boolean isItemEqual(IUniversalStack stack1, ItemStack stack2,
			boolean ignoreMetadata) {
		if(ignoreMetadata){
			return stack1.getItem()==stack2.getItem();
		}else{
			return (stack1.getItem()==stack2.getItem())&&(stack1.getItemDamage()==stack2.getItemDamage());
		}
	}	
	public static boolean isItemEqual(ItemStack stack1, ItemStack stack2,
			boolean ignoreMetadata) {
		if(ignoreMetadata){
			return stack1.getItem()==stack2.getItem();
		}else{
			return (stack1.getItem()==stack2.getItem())&&(stack1.getItemDamage()==stack2.getItemDamage());
		}
	}		
	public static boolean isItemEqual(FluidStack stack1, FluidStack stack2) {
		return stack1.getFluid()==stack2.getFluid();
	}		
	public static boolean isItemEqualStackSizeProtection(IUniversalStack stack1, IUniversalStack stack2, boolean ignoreMetadata){
		if(ignoreMetadata){
			boolean metadiffs = stack1.getItemDamage()!=stack2.getItemDamage();
			if(!metadiffs){
				return stack1.getItem()==stack2.getItem();
			}else{
				if(stack1.getStackSize()==0){
					return stack1.getItem()==stack2.getItem();
				}else{
					return false;
				}
			}
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
	public static boolean isItemEqualStackSizeProtection(HOEFluidStack stack1, FluidStack stack2){
		return stack1.getFluid().getForgeFluid()==stack2.getFluid();
	}		
	
	
	public static ItemStack tryToGetOut(ItemStackTransferTuple tuple, ItemStack filter){
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

	public static IUniversalStack decrementStack(IUniversalStack stack, int amount) {
		if(UniversalStackUtils.isNull(stack)){return null;}
		stack.setStackSize(stack.getStackSize()-amount);
		return verify(stack);
	}
	public static IUniversalStack incrementStack(IUniversalStack stack, int amount) {
		if(UniversalStackUtils.isNull(stack)){return null;}
		stack.setStackSize(stack.getStackSize()+amount);
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
