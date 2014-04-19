package ru.itaros.hoe;

import org.apache.logging.log4j.Level;

import ru.itaros.api.hoe.IHOEContextDetector;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;

public class ContextDetector implements IHOEContextDetector {

	
	private static ContextDetector instance;
	
	private FMLContext local;
	
	
	public static ContextDetector getInstance(){
		return instance;
	}
	
	public ContextDetector(){
		instance=this;
	}
	
	@Override
	public FMLContext getContext() {
		if(local==null){return FMLContext.INTEGRATED;}else{
			return local;
		}
	}

	@Override
	public void requestContextData(FMLServerAboutToStartEvent event) {
		FMLLog.log(Level.INFO, ">HOE TRIES TO REFLECT ISE...");
    	try {
			Class<?> integrated = (Class<?>) Class.forName("IntegratedServer");
			if(integrated.isInstance(event.getServer())){
				FMLLog.log(Level.INFO, ">...SUCCESS[INTEGRATED]");
				local=FMLContext.INTEGRATED;
	    	}
		} catch (ClassNotFoundException e) {
			FMLLog.log(Level.INFO, ">...FAILURE[DEDICATED]");
			local=FMLContext.DEDICATED;
			//TODO: Detect MCPC+ as UPRS context
		} 

	}

}
