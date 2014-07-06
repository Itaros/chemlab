package ru.itaros.chemlab.loader.client;

import cpw.mods.fml.client.registry.RenderingRegistry;
import ru.itaros.chemlab.client.isbr.SyndicationBusISBR;

public class ISBRLoader {

	public static SyndicationBusISBR syndicationBusModel;
	
	
	public static void load(){
		
		syndicationBusModel = new SyndicationBusISBR();
		RenderingRegistry.registerBlockHandler(syndicationBusModel);
		
		
		
		
		
	}
	
	
}
