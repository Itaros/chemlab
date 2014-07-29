package ru.itaros.hoe.utils;

import net.minecraft.item.ItemStack;


//Usage Examples: 
//
//StackTransferTuple transferTuple = new StackTransferTuple();
//
//public ItemStack tryToPutIn(ItemStack source){
//	transferTuple.fill(exemplar_cell_in, source);
//	source=StackUtility.tryToPutIn(transferTuple);
//	exemplar_cell_in=transferTuple.retr1();
//	return source;
//}
//public ItemStack tryToGetOut(ItemStack target){
//	transferTuple.fill(target, exemplar_cell_out);
//	target = StackUtility.tryToGetOut(transferTuple);
//	exemplar_cell_out=transferTuple.retr2();
//	return target;
//}
 

public class StackTransferTuple {

	ItemStack stack1, stack2;
	
	
	public void fill(ItemStack stack1, ItemStack stack2){
		this.stack1=stack1;
		this.stack2=stack2;
	}
	
	public ItemStack retr1(){
		return stack1;
	}
	public ItemStack retr2(){
		return stack2;
	}	
	
}
