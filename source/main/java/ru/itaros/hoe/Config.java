package ru.itaros.hoe;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {

	private static final String CAT_THREADING = "HOE MTA";
	private static final String THREADING_KEEPALIVE_COMMENTARY = "This option allows you to engage HOE MTA Monitor. "
			+ "\nIt will keep all machines working even after chunk being unloaded,"
			+ "\n but it still needs to load that chunk at least once to catchup(for now, ^___^)!"
			+ "\nWARNING: HOE KeepAlive may break worlds in SSP! Use only with dedicated server!";
	private static final String THREADING_RETENTION_COMMENTARY = ""
			+ "This option defines retention policy for HOE Threads."
			+ "\nNONE - no retention. Useful to keep performance on NUMA architectures(was unconfigurable default from initial release to PR-2.03i2)"
			+ "\nRETROPREDICTIVE - will return control to OS for a time left from previus cycle."
			+ "\nSTEPPING - will step 10ms continously until hits lower time barrier"
			+ "\nPERCYCLE - will return control to OS only after update cycle for minimal time possible"
			+ "\nRETROPREDICTIVE is default and benificial for small and new server. Congested, full of HOE Machines servers should consider "
			+ "switching to STEPPING(lower power consumption, more stuttery HOE behaviour) or PERCYCLE(best for hi-end dedicated)";




	public boolean threading_keepalive=false;
	public ThreadRetentionPolicy threading_retentionPolicy=ThreadRetentionPolicy.RETROPREDICTIVE;
	
	public enum ThreadRetentionPolicy{
		NONE,
		PERCYCLE,
		RETROPREDICTIVE,
		STEPPING
	}
	
	private Configuration cfg;
	
	public Config loadConfig(FMLPreInitializationEvent e){
		cfg = new Configuration(e.getSuggestedConfigurationFile());
		
		
		threading_keepalive=cfg.get(CAT_THREADING, "keepalive", threading_keepalive, THREADING_KEEPALIVE_COMMENTARY).getBoolean(threading_keepalive);
		
		threading_retentionPolicy=ThreadRetentionPolicy.valueOf(cfg.getString(CAT_THREADING,"threadRetention",threading_retentionPolicy.toString(),THREADING_RETENTION_COMMENTARY));
		
		cfg.save();
		return this;
	}
	
	
}
