package ru.itaros.hoe.tiers;

import ru.itaros.chemlab.items.ChemLabItem;
import net.minecraft.item.ItemStack;

/*
 * Tier manager contains data about resource entrypoint and resultant product
 */
public class TierManager {

	protected String name;
	
	public String getName() {
		return name;
	}

	protected ItemStack source,target;
	


	public TierManager(String name) {
		this.name=name;
	}

	public void reverse(){
		isReversed=true;
	}
	
	protected ItemStack[] intermid;
	
	private boolean isReversed=false;
	
	
	public ItemStack getSourceItem(int custom_amount){
		ItemStack n = source.copy();
		n.stackSize=custom_amount;
		return n;
	}	
	public ItemStack getSourceItem(){
		return source;
	}
	public ItemStack getTargetItem(){
		if(isReversed){
			return source;
		}else{
			return target;
		}
	}
	
	public void setSourceItem(ItemStack stack){
		source=stack;
	}
	public void setTargetItem(ItemStack stack){
		target=stack;
	}
	public void setIntermidItem(ItemStack... stacks){
		intermid=stacks;
	}
	
	
	
	public boolean isReversed(){
		return isReversed;
	}
	public boolean isEnabled(){
		return !isReversed;
	}

	//WRAPPERS
	public void setSourceItem(ChemLabItem cli) {
		setSourceItem(new ItemStack(cli,1));
	}

	public void setTargetItem(ChemLabItem cli) {
		setTargetItem(new ItemStack(cli,1));
	}
	
	
}
