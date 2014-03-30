package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.client.ui.BiogrinderContainer;
import ru.itaros.chemlab.client.ui.CentrifugalExtractorContainer;
import ru.itaros.chemlab.client.ui.common.GUIHandler;

public class GUILoader {
	
	public static void loadGUIs(){
		GUIHandler.registerGUIs(
				BiogrinderContainer.class,
				CentrifugalExtractorContainer.class
				);
	}
	
}
