package ru.itaros.hoe;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {

	private static final String CAT_THREADING = "HOE MTA";
	private static final String THREADING_KEEPALIVE_COMMENTARY = "This option allows you to engage HOE MTA Monitor. "
			+ "\nIt will keep all machines working even after chunk being unloaded,"
			+ "\n but it still needs to load that chunk at least once to catchup(for now, ^___^)!"
			+ "\nWARNING: HOE KeepAlive may break worlds in SSP! Use only with dedicated server!"
			+ " Actually, DO NOT ENABLE IT AT ALL!";
	private static final String THREADING_RETENTION_COMMENTARY = ""
			+ "This option defines retention policy for HOE Threads."
			+ "\nNONE - no retention. Useful to keep performance on NUMA architectures(was unconfigurable default from initial release to PR-2.03i2)"
			+ "\nRETROPREDICTIVE - will return control to OS for a time left from previus cycle."
			+ "\nSTEPPING - will step 10ms continously until hits lower time barrier"
			+ "\nPERCYCLE - will return control to OS only after update cycle for minimal time possible"
			+ "\nRETROPREDICTIVE is default and benificial for small and new server. Congested, full of HOE Machines servers should consider "
			+ "switching to STEPPING(lower power consumption, more stuttery HOE behaviour) or PERCYCLE(best for hi-end dedicated)";

	private static final String CAT_INTEROP = "HOE-Minecraft Interoperability";
	private static final String INTEROP_PUSHPULLTIME_COMMENTARY = "Maximum amount of allowed push-pull HOE ops time "
			+ "in milliseconds. Full MC tick is 50 ms. The more you specify the more CL will take from that pie to "
			+ "control its operations. Take note: if you specify too much it will burden all other MC systems leading "
			+ "to severe lag. Specifying value too low may in turn make CL machines slow to respond to inventory changes.";
	private static final String INTEROP_HOESOPHOMO_COMMENTARY = "Defines strategy for reserving HOE push-pull ops. "
			+ "True means - make sure all time is occupied. This works fine when you expect a lot of CL usage grow in your game or server. "
			+ "That way HOE push-pull will not provide additional burden over time. "
			+ "False ensures CL will take only nessesary amount of time to process HOE push-pull ops without consuming all dedicated time.";
	

	public boolean threading_keepalive=false;
	public ThreadRetentionPolicy threading_retentionPolicy=ThreadRetentionPolicy.RETROPREDICTIVE;
	
	public boolean interop_hoeshomo=true;
	public long interop_pushpulltime=2;
	
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
		
		threading_retentionPolicy=ThreadRetentionPolicy.valueOf(cfg.getString("threadRetention",CAT_THREADING,threading_retentionPolicy.toString(),THREADING_RETENTION_COMMENTARY));
		
		interop_pushpulltime = cfg.get(CAT_INTEROP, "hoepptime", (int)interop_pushpulltime ,INTEROP_PUSHPULLTIME_COMMENTARY).getInt();
		interop_hoeshomo = cfg.getBoolean("hoetimehomogenity", CAT_INTEROP, interop_hoeshomo, INTEROP_HOESOPHOMO_COMMENTARY);
		
		cfg.save();
		return this;
	}
	
	
}
