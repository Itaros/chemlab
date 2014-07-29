package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;

public class DiaphragmalElectrolyzerData extends HOEMachineCrafterData {
	/*
	 * Reflection autocaster
	 */
	public DiaphragmalElectrolyzerData(HOEData parent){
		super(parent);
	}
	
	public DiaphragmalElectrolyzerData(){
		super();
	}

	public enum AnodeType{
		graphite,titanium
	}	
	private AnodeType anode;
	private int anodeResource=0;
	private int diaphragmResource=0;
		
	
	
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
		if(diaphragmResource>0 && anodeResource>0){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected void HOEDataUpdateState() {
		diaphragmResource--;
		anodeResource--;
	}

	
	//public ItemStack retrieveAnode(){
	//	ItemStack stack = new ItemStack();
	//}
	private ItemStack retrieveDiaphragm(){
		if(diaphragmResource<=0){return null;}
		ItemStack stack = new ItemStack(ItemLoader.asbestos_diaphragm,1,ItemLoader.asbestos_diaphragm.getMaxDamage()-diaphragmResource);
		return stack;
	}
	private void insertDiaphragm(ItemStack stack){
		diaphragmResource=stack.getMaxDamage()-stack.getItemDamage();
	}
	public ItemStack exchangeDiaphragm(ItemStack n){
		ItemStack o = retrieveDiaphragm();
		insertDiaphragm(n);
		return o;
	}
	
	private ItemStack retrieveAnode(){
		if(anodeResource<=0){return null;}
		ItemStack stack = new ItemStack(ItemLoader.graphite_anode,1,ItemLoader.graphite_anode.getMaxDamage()-anodeResource);
		return stack;
	}
	private void insertAnode(ItemStack stack){
		anodeResource=stack.getMaxDamage()-stack.getItemDamage();
	}
	public ItemStack exchangeAnode(ItemStack n){
		ItemStack o = retrieveAnode();
		insertAnode(n);
		return o;
	}	
	
	
	
	
}
