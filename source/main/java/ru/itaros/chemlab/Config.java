package ru.itaros.chemlab;

import net.minecraftforge.common.config.Configuration;
import ru.itaros.hoe.data.machines.HOEMachineData;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	public boolean gfx_AdvancedParticleInjectorHack;
	public boolean gfx_gasChimneyFX;	
	
	private static final String CATEGORY_GFX="GFX";
	private static final String CATEGORY_WORLDGEN="WORLDGEN";
	
	private Configuration cfg;
	
	public Config loadConfig(FMLPreInitializationEvent e){
		cfg = new Configuration(e.getSuggestedConfigurationFile());
		
		gfx_gasChimneyFX=cfg.get(CATEGORY_GFX, "gasChimneyFX", true).getBoolean(true);
		gfx_AdvancedParticleInjectorHack=cfg.get(CATEGORY_GFX, "useAdvancedParticleInjectorHack", true).getBoolean(true);
		if(!gfx_gasChimneyFX && gfx_AdvancedParticleInjectorHack){
			System.out.println("It is not possible to have gfx_AdvancedParticleInjectorHack enabled with gfx_gasChimneyFX. Disabling both.");
			gfx_AdvancedParticleInjectorHack=false;
		}
		
		ChemLab.getInstance().getTierRegistry().readConfigs(cfg);
		
		cfg.save();
		return this;
	}

	
}
