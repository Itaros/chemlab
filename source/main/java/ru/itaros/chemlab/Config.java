package ru.itaros.chemlab;

import net.minecraftforge.common.config.Configuration;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationHubData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	public boolean gfx_AdvancedParticleInjectorHack;
	public boolean gfx_gasChimneyFX;
	
	public Class<? extends HOEMachineData>[] hoesyndic_blacklist;
	
	public String worldgenerator_clid="ru.itaros.chemlab.loader.worldgen.WorldGenLoaderNative";
	public static int[] worldgenerator_allowedDims={0};
	
	
	private static final String CATEGORY_GFX="GFX";
	private static final String CATEGORY_WORLDGEN="WORLDGEN";
	private static final String CATEGORY_HOESYNDICATION="HOESYNDICATION";
	
	private Configuration cfg;
	
	public Config loadConfig(FMLPreInitializationEvent e){
		cfg = new Configuration(e.getSuggestedConfigurationFile());
		
		gfx_gasChimneyFX=cfg.get(CATEGORY_GFX, "gasChimneyFX", true).getBoolean(true);
		gfx_AdvancedParticleInjectorHack=cfg.get(CATEGORY_GFX, "useAdvancedParticleInjectorHack", true).getBoolean(true);
		if(!gfx_gasChimneyFX && gfx_AdvancedParticleInjectorHack){
			System.out.println("It is not possible to have gfx_AdvancedParticleInjectorHack enabled with gfx_gasChimneyFX. Disabling both.");
			gfx_AdvancedParticleInjectorHack=false;
		}
		
		
		worldgenerator_clid=cfg.get(CATEGORY_WORLDGEN, "clid", worldgenerator_clid).getString();
		worldgenerator_allowedDims=cfg.get(CATEGORY_WORLDGEN,"allowedDims",worldgenerator_allowedDims).getIntList();

		loadHOESyndicationBlacklist(cfg);
		
		ChemLab.getInstance().getTierRegistry().readConfigs(cfg);
		
		cfg.save();
		return this;
	}

	private void loadHOESyndicationBlacklist(Configuration cfg) {
		String[] blacklistedClasses = cfg.get(CATEGORY_HOESYNDICATION, "invalidationblacklist", new String[]{SyndicationHubData.class.getName()}).getStringList();
		hoesyndic_blacklist = new Class[blacklistedClasses.length];
		int i = -1;
		for(String s:blacklistedClasses){
			i++;
			Class<? extends HOEMachineData> c;
			try {
				c = (Class<? extends HOEMachineData>) Class.forName(s);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Wrong "+CATEGORY_HOESYNDICATION+" configuration",e);
			}
			hoesyndic_blacklist[i]=c;
		}
		
	}
	
}
