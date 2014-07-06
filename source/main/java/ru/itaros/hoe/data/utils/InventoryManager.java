package ru.itaros.hoe.data.utils;

import java.util.ArrayList;

import ru.itaros.chemlab.hoe.data.syndication.SyndicationItemPortData;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationItemPortTileEntity;

public class InventoryManager {

	//private int slots=0;

	
	public InventoryManager() {
	}

	ArrayList<SyndicationItemPortData> slot=new ArrayList<SyndicationItemPortData>();
	
	private int pointer=-1;
	public SyndicationItemPortData peek(){
		incrementPointer();
		if(pointer>slot.size()-1){return null;}//Impossible to peek nothing
		return slot.get(pointer);
	}
	
//	public ItemStack peekAndCompare(ItemStack example){
//		ItemStack rslt = peek();
//		if(rslt==null){return null;}
//		if(rslt.isItemEqual(example)){return rslt;}
//		return null;
//	}
	
	
	//POP and PUSH are not THREAD-SAFE. ONLY ONE READER AND WRITER ARE ALLOWED AT THE SAME TIME
	public SyndicationItemPortData pop(){
		if(pointer>slot.size()-1){return null;}//Impossible to pop nothing
		return slot.get(pointer);
	}
//	public void push(ItemStack stack){
//		slot[pointer]=stack;
//		if(stack!=null){
//			System.out.println("PUSH: "+stack.toString());
//		}
//	}
	
	
	private void incrementPointer() {
		pointer++;
		if(pointer>=slot.size()){pointer=0;}
	}

	public void addItemport(SyndicationItemPortTileEntity utility) {
		slot.add((SyndicationItemPortData) utility.getServerData());
	}
	
}
