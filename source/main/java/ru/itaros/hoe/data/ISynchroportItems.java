package ru.itaros.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;



public interface ISynchroportItems extends ISynchroport{
	public ItemStack tryToPutItemsIn(ItemStack source);
	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter);
	public ItemStack tryToGetItemsOut(ItemStack target);
	public ItemStack tryToGetItemsOut(ItemStack target, ItemStack filter);	
}


//Implementation example:

//StackTransferTuple transferTuple = new StackTransferTuple();
//
//public IUniversalStack tryToPutIn(IUniversalStack source){
//	transferTuple.fill(exemplar_cell_in, source);
//	source=StackUtility.tryToPutIn(transferTuple);
//	exemplar_cell_in=transferTuple.retr1();
//	this.markDirty();
//	return source;
//}
//public IUniversalStack tryToGetOut(IUniversalStack target){
//	transferTuple.fill(target, exemplar_cell_out);
//	target = StackUtility.tryToGetOut(transferTuple);
//	exemplar_cell_out=transferTuple.retr2();
//	this.markDirty();
//	return target;
//}