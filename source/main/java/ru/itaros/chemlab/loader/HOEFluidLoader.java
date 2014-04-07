package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.fluids.hoe.CellulosalExtractives;
import ru.itaros.chemlab.fluids.hoe.*;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class HOEFluidLoader {
	
	public static CellulosalExtractives cellulosal_extractives_high;
	public static Water water_natural;
	public static PressurizedSteam steam_pressurized;
	public static PolyoseWaterSolution polyose_proteined_solution;
	public static SodiumHydroxideSolution sodiumhydroxide_solution;
	
	
	public static void load(){
		//TODO: should be API interface
		HOEFluidRegistry registry = HOEFluid.getFluidRegistry();
		
		cellulosal_extractives_high = new CellulosalExtractives();
		registry.register(cellulosal_extractives_high);
		
		water_natural = new Water();
		registry.register(water_natural);
		
		steam_pressurized = new PressurizedSteam();
		registry.register(steam_pressurized);
		
		polyose_proteined_solution = new PolyoseWaterSolution();
		registry.register(polyose_proteined_solution);
		
		sodiumhydroxide_solution = new SodiumHydroxideSolution();
		registry.register(sodiumhydroxide_solution);
		
		
		
		
	}
}
