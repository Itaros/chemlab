package ru.itaros.toolkit.hoe.machines.interfaces;

import net.minecraft.item.ItemStack;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackTransferTuple;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;


public interface ISynchroportItems {

	public ItemStack tryToPutIn(ItemStack source);
	public ItemStack tryToGetOut(ItemStack target);
	
}


//Implementation example:

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