package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.fluids.hoe.CellulosalExtractives;
import ru.itaros.chemlab.fluids.hoe.PressurizedSteam;
import ru.itaros.chemlab.fluids.hoe.Water;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class HOEFluidLoader {
	
	public static CellulosalExtractives cellulosal_extractives_high;
	public static Water water_natural;
	public static PressurizedSteam steam_pressurized;
	
	public static void load(){
		//TODO: should be API interface
		HOEFluidRegistry registry = HOEFluid.getFluidRegistry();
		
		cellulosal_extractives_high = new CellulosalExtractives();
		registry.register(cellulosal_extractives_high);
		
		water_natural = new Water();
		registry.register(water_natural);
		
		steam_pressurized = new PressurizedSteam();
		registry.register(steam_pressurized);
	}
}
