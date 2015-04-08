package ru.itaros.hoe.adapter.envirolab;

import ru.itaros.api.hoe.heat.Heat;

public final class ImpostorAdapterEnviroLab {

	private WorldWideHeatContainer wwhc = new WorldWideHeatContainer();
	
	public WorldWideHeatContainer getWorldWideHeatContainer(){
		return wwhc;
	}
	public Heat getEnviromentalHeat(){
		wwhc.updateDistribution();
		return wwhc.getHeat();
	}
	
}
