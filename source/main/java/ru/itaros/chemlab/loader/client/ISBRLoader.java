package ru.itaros.chemlab.loader.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ru.itaros.chemlab.client.isbr.AdvancedComponentBlockISBR;

public class ISBRLoader {
	
	public static AdvancedComponentBlockISBR advancedComponentBlockModel;
	
	public static void load(){
		
		advancedComponentBlockModel = new AdvancedComponentBlockISBR();
		RenderingRegistry.registerBlockHandler(advancedComponentBlockModel);
		
		
	}
	
	
}
