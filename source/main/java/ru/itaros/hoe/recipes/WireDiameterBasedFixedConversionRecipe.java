package ru.itaros.hoe.recipes;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class WireDiameterBasedFixedConversionRecipe extends
		FixedConversionRecipe {

	
	protected Item source;
	protected int min,max;
	
	
	public WireDiameterBasedFixedConversionRecipe(Item source, int min,
			int max, ItemStack[] gridInput, ItemStack[] gridOutput) {
		super(10,100,extend(source,gridInput),gridOutput,helperGenerateName(gridInput,gridOutput));
		this.min=min;
		this.max=max;
		this.source=source;
		
	}


	private static ItemStack[] extend(Item source, ItemStack[] gridInput) {
		ItemStack[] ov = new ItemStack[gridInput.length+1];
		int x = 0;
		ov[x]=new ItemStack(source);
		x++;
		for(;x<ov.length;x++){
			ov[x]=gridInput[x-1];
		}
		return ov;
	}
	
	
	//Bypasses SWG differences
	@Override
	public int getSlotIdFor(ItemStack type, boolean ignoreMetadata) {
		if(type!=null && type.getItem()==source){
			//Fast validation of SWG
			return (type.getItemDamage()>=min & type.getItemDamage()<=max)?0:-1;
		}
		//Fallback
		return super.getSlotIdFor(type, true);
	}	
	

}
