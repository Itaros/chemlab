package ru.itaros.hoe.io;

import java.util.Hashtable;

import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.api.hoe.registries.IIORegistry;

public final class IOCollectionHelper {
	private HOEIO[] ios;
	private Hashtable<String,HOEIO> iodict=new Hashtable<String,HOEIO>();
	public IOCollectionHelper(HOEIO... ios){
		this.ios=ios;
		prepareHashes();
	}
	private void prepareHashes() {
		for(HOEIO h:ios){
			String hioname = h.getClass().getSimpleName();
			iodict.put(hioname, h);
		}
		
	}
	public HOEIO getHOEIO(String name){
		return iodict.get(name);
	}
	public void registerInHOE() {
		for(HOEIO i:ios){
			try {
				IIORegistry reg=HOEIO.getIORegistry();
				reg.add(i);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		}
	}
}
