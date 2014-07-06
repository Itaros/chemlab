package ru.itaros.hoe.tiers;

import java.util.ArrayList;

import net.minecraftforge.common.config.Configuration;

public class TierRegistry {

	private static final String TIERREGCATEGORY = "TIER_MANAGER";
	private ArrayList<TierManager> tiers = new ArrayList<TierManager>();
	
	public void add(TierManager tier){
		tiers.add(tier);
	}
	
	
	public void readConfigs(Configuration cfg){
		
		
		cfg.addCustomCategoryComment(TIERREGCATEGORY, "Local HOETierRegistry Configuration");
		
		for(TierManager tm:tiers){
			String tiername = tm.getName();
			boolean isEnabled = cfg.get(TIERREGCATEGORY, "T-"+tiername.toUpperCase(), true).getBoolean(true);
			if(!isEnabled){
				tm.reverse();
			}
		}
		
		
	}
	
}
