package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.hoe.tiers.TierManager;
import ru.itaros.hoe.tiers.TierRegistry;

public class TierLoader {

	public static TierManager L0_WroughtIron;
	
	
	public static void loadTiers(){
		TierRegistry local = ChemLab.getInstance().getTierRegistry();
		
		L0_WroughtIron = new TierManager("L0:WroughtIron");
		local.add(L0_WroughtIron);
		
		
	}
	
	
}
