package ru.itaros.hoe.data;

import net.minecraft.item.ItemStack;


public interface ISynchroportItems {

	public ItemStack tryToPutIn(ItemStack source);
	public ItemStack tryToPutIn(ItemStack source, ItemStack filter);
	public ItemStack tryToGetOut(ItemStack target);
	public ItemStack tryToGetOut(ItemStack target, ItemStack filter);
	
	public void markDirty();
	public boolean pollDirty();
	
}


//Implementation example:

//StackTransferTuple transferTuple = new StackTransferTuple();
//
//public ItemStack tryToPutIn(ItemStack source){
//	transferTuple.fill(exemplar_cell_in, source);
//	source=StackUtility.tryToPutIn(transferTuple);
//	exemplar_cell_in=transferTuple.retr1();
//	this.markDirty();
//	return source;
//}
//public ItemStack tryToGetOut(ItemStack target){
//	transferTuple.fill(target, exemplar_cell_out);
//	target = StackUtility.tryToGetOut(transferTuple);
//	exemplar_cell_out=transferTuple.retr2();
//	this.markDirty();
//	return target;
//}