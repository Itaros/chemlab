package ru.itaros.chemlab;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config {
	
	public boolean gfx_AdvancedParticleInjectorHack;
	public boolean gfx_gasChimneyFX;
	
	
	private static final String CATEGORY_GFX="GFX";
	
	
	private Configuration cfg;
	
	public Config loadConfig(FMLPreInitializationEvent e){
		cfg = new Configuration(e.getSuggestedConfigurationFile());
		
		gfx_gasChimneyFX=cfg.get(CATEGORY_GFX, "gasChimneyFX", true).getBoolean(true);
		gfx_AdvancedParticleInjectorHack=cfg.get(CATEGORY_GFX, "useAdvancedParticleInjectorHack", true).getBoolean(true);
		if(!gfx_gasChimneyFX && gfx_AdvancedParticleInjectorHack){
			System.out.println("It is not possible to have gfx_AdvancedParticleInjectorHack enabled with gfx_gasChimneyFX. Disabling both.");
			gfx_AdvancedParticleInjectorHack=false;
		}
		
		
		cfg.save();
		return this;
	}
	
}
