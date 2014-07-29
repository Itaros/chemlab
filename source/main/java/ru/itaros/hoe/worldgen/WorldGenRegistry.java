package ru.itaros.hoe.worldgen;

import java.util.ArrayList;

public class WorldGenRegistry {

	public ArrayList<WorldGenOverlay> overlays = new ArrayList<WorldGenOverlay>();
	
	public void registerOverlay(WorldGenOverlay overlay){
		overlays.add(overlay);
		System.out.println("Registered new overlay: "+overlay.getName());
	}
	
	public void applyAllOverlays(WorldGenDataIncapsulator incapsulator){
		for(WorldGenOverlay over:overlays){

			boolean ok = over.pass(incapsulator.random, incapsulator.chunkX, incapsulator.chunkZ, incapsulator.world);
			//if(ok && over.getName().equals("serpentite")){
			//	System.out.println("HEM:{"+incapsulator.chunkX+","+incapsulator.chunkZ+"}");
			//}			
			
		}
	}
	
}
