package ru.itaros.toolkit.hoe.machines.basic;

import ru.itaros.api.hoe.exceptions.HOEWrongSyncDirection;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import net.minecraft.item.Item;

public class HOEMachineData extends HOEData{
	private boolean isSided;//Is it residing in MTA?
	
	private int incoming_slots,outcoming_slots;
	//ALIGNED DATA
	private int[] incoming_depot;
	private int[] outcoming_depot;
	
	private Item[] incoming_stricttype;//HARDBIBNDED
	private Item[] outcoming_stricttype;//HARDBIBNDED
	
	private HOEMachineIO io;
	//END OF ALIGNED DATA
	
	
	public boolean isSided(){
		return isSided;
	}
	
	public HOEMachineData(){
		this.isSided=false;
		
		incoming_slots=0;
		outcoming_slots=0;
		init();
		spawnChild();
	}


	protected HOEMachineData(HOEMachineData parent){
		this.isSided=true;
		bindChildToParent(parent);
	}
	private void bindChildToParent(HOEMachineData parent){
		//Linking strict types. Believed to be Read-Only
		this.incoming_stricttype=parent.incoming_stricttype;
		this.outcoming_stricttype=parent.outcoming_stricttype;
		//Mimicking structure
		this.incoming_slots=parent.incoming_slots;
		this.outcoming_slots=parent.outcoming_slots;
		this.incoming_depot=new int[incoming_slots];
		this.outcoming_depot=new int[outcoming_slots];
		//Initial sync
		parent.sync();
	}
	
	
	private void sync() {
		if(child==null){throw new HOEWrongSyncDirection();}
		HOEMachineData childd=(HOEMachineData) child;
		childd.incoming_depot=incoming_depot;
		childd.outcoming_depot=outcoming_depot;
	}

	private void init(){
		incoming_depot=new int[incoming_slots];
		outcoming_depot=new int[outcoming_slots];
		incoming_stricttype=new Item[incoming_slots];
		outcoming_stricttype=new Item[outcoming_slots];
	}
}
