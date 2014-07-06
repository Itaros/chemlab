package ru.itaros.hoe;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {

	private static final String CAT_THREADING = "HOE MTA";
	private static final String THREADING_KEEPALIVE_COMMENTARY = "This option allows you to engage HOE MTA Monitor. "
			+ "\nIt will keep all machines working even after chunk being unloaded,"
			+ "\n but it still needs to load that chunk at least once to catchup(for now, ^___^)!"
			+ "\nWARNING: HOE KeepAlive may break worlds in SSP! Use only with dedicated server!";


	


	public boolean threading_keepalive=false;
	
	
	private Configuration cfg;
	
	public Config loadConfig(FMLPreInitializationEvent e){
		cfg = new Configuration(e.getSuggestedConfigurationFile());
		
		
		threading_keepalive=cfg.get(CAT_THREADING, "keepalive", threading_keepalive, THREADING_KEEPALIVE_COMMENTARY).getBoolean(threading_keepalive);
		
		cfg.save();
		return this;
	}
	
	
}
