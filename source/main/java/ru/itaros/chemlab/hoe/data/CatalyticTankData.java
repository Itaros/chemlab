package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.data.IHasReplacableParts;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;

public class CatalyticTankData extends HOEMachineCrafterData implements IHasReplacableParts {
	/*
	 * Reflection autocaster
	 */
	public CatalyticTankData(HOEData parent){
		super(parent);
	}
	
	public CatalyticTankData(){
		super();
	}

	public enum CathalyzerType{
		graphite,titanium
	}	
	private CathalyzerType type;
	private int resource=0;

		
	
	
	@Override
	public void writeNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		super.writeNBT(nbt);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		super.readNBT(nbt);
	}


	
	@Override
	protected boolean HOEDataStateCheck() {
		if(resource>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void HOEDataUpdateState() {
		resource--;
	}

	
	
	private ItemStack retrieveCatalyzer(){
		if(resource<=0){return null;}
		ItemStack stack = new ItemStack(ItemLoader.platinum_catalization_grid,1,ItemLoader.platinum_catalization_grid.getMaxDamage()-resource);
		return stack;
	}
	private void insertCatalyzer(ItemStack stack){
		resource=stack.getMaxDamage()-stack.getItemDamage();
	}
	public ItemStack exchangeCatalyzer(ItemStack n){
		ItemStack o = retrieveCatalyzer();
		insertCatalyzer(n);
		return o;
	}

	
	//IHasReplacableParts
	@Override
	public boolean requiresReplacements() {
		if(resource<=0){return true;}else{return false;}
	}
	//TODO: ItemLoader should move to cached ItemStacks
	private static Item replacable = ItemLoader.platinum_catalization_grid;
	@Override
	public ItemStack getTypeOfReplacableRequired() {
		return new ItemStack(replacable);
	}

	@Override
	public ItemStack exchangeParts(ItemStack part) {
		if(part.getItem()==replacable){
			return exchangeCatalyzer(part);
		}else{
			return null;
		}
		
	}	
}
