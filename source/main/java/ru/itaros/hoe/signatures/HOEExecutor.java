package ru.itaros.hoe.signatures;

import java.lang.reflect.Constructor;

public class HOEExecutor {
	
	public HOEExecutor(){
		
	}
	
	public void execute(String clid){
		System.out.println("Trying to invoke HOE Invocable Config...");
		try {
			Class<?> cl = Class.forName(clid);
			Constructor<?> nilconstructor = cl.getConstructor();
			IAllowedConfigArgument invocable = (IAllowedConfigArgument)nilconstructor.newInstance();
			invocable.execute();
		} catch (Exception e) {
			throw new RuntimeException("Bad Config",e);
		}
		System.out.println("...Done!");
	}
	
	
}
