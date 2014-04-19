package ru.itaros.api.hoe;

import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;

public interface IHOEContextDetector {

	public enum FMLContext{
		INTEGRATED,
		DEDICATED,
		UPRS//MPCP+,Bukkit
	}
	
	
	public FMLContext getContext();
	public void requestContextData(FMLServerAboutToStartEvent event);
	
}
