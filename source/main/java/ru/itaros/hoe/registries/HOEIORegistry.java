package ru.itaros.hoe.registries;

import java.util.Hashtable;

import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.api.hoe.registries.IIORegistry;

public final class HOEIORegistry implements IIORegistry {

	private static HOEIORegistry instance;
	public static HOEIORegistry getInstance(){
		return instance;
	}
	public HOEIORegistry(){
		instance=this;
		System.out.println("HOEIOREG is initialized!");
	}
	
	Hashtable<String,HOEIO> dict = new Hashtable<String,HOEIO>();
	
	
	@Override
	public void add(HOEIO io) {
		dict.put(io.getClass().getName(), io);
	}

	@Override
	public HOEIO get(String fullname) {
		return dict.get(fullname);
	}

}
