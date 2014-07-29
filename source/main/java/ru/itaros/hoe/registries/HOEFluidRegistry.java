package ru.itaros.hoe.registries;

import java.util.Hashtable;

import ru.itaros.hoe.fluid.HOEFluid;

public class HOEFluidRegistry {

	private static HOEFluidRegistry instance;
	public static HOEFluidRegistry getInstance(){
		return instance;
	}
	public HOEFluidRegistry(){
		instance = this;
	}
	
	public void register(HOEFluid fluid){
		fluids.put(fluid.getUnlocalizedName(), fluid);
	}
	public HOEFluid pop(String key){
		return fluids.get(key);
	}
	public HOEFluid[] all(){
		HOEFluid[] arr = new HOEFluid[fluids.values().size()];
		return  fluids.values().toArray(arr);
	}
	
	private Hashtable<String,HOEFluid> fluids = new Hashtable<String,HOEFluid>();
	
	
	
	
	
}
