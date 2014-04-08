package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.fluids.hoe.*;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class HOEFluidLoader {
	
	public static CellulosalExtractives cellulosal_extractives_high;
	public static Water water_natural;
	public static PressurizedSteam steam_pressurized;
	public static PolyoseWaterSolution polyose_proteined_solution;
	public static SodiumHydroxideSolution sodiumhydroxide_solution;
	
	public static WetLignin wet_lignin;
	public static WetCellulose wet_cellulose;
	
	public static NaClSolution nacl_solution;
	public static ChlorineGas cl2_gas;
	public static HydrogenGas h2_gas;
	
	public static Air air;
	public static AirCompressed air_compressed;
	
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
		
		wet_lignin=new WetLignin();
		registry.register(wet_lignin);
		
		wet_cellulose=new WetCellulose();
		registry.register(wet_cellulose);
				
				
		nacl_solution = new NaClSolution();
		registry.register(nacl_solution);
		
		//naoh_solution = new NaOHSolution();
		//registry.register(naoh_solution);
		
		cl2_gas = new ChlorineGas();
		registry.register(cl2_gas);
		
		h2_gas = new HydrogenGas();
		registry.register(h2_gas);
				
		
		air = new Air();
		registry.register(air);
		air_compressed = new AirCompressed();
		registry.register(air_compressed);
		
		
		
	}
}
