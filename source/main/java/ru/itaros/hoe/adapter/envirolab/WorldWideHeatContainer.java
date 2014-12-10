package ru.itaros.hoe.adapter.envirolab;

import ru.itaros.api.hoe.heat.Heat;
import ru.itaros.api.hoe.heat.IHeatContainer;

public final class WorldWideHeatContainer implements IHeatContainer {

	private Heat heat = new Heat(500000000L);//World-wide capacitance lol
	
	public WorldWideHeatContainer(){
		heat.setSplitRatio(2L*24L*10L);
	}
	
	@Override
	public Heat getHeat() {
		return heat;
	}

	@Override
	public void updateDistribution() {
		heat.setEnergy(heat.getCapacity()*299L);
	}

	@Override
	public long getMeltdownPoint() {
		return 0;
	}


	
}
