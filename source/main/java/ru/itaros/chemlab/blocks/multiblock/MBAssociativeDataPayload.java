package ru.itaros.chemlab.blocks.multiblock;

import java.util.Hashtable;

//TODO: Move to HOE

/*
 * This class is used as supplementary to encode various MB calc results
 * Especially for different kinds of materials on block inspection phase
 */
public class MBAssociativeDataPayload {

	private Hashtable<String,Object> assoc = new Hashtable<String,Object>();
	
	public void set(String key, Object value){
		assoc.put(key, value);
	}
	public Object get(String key){
		return assoc.get(key);
	}
	
}
