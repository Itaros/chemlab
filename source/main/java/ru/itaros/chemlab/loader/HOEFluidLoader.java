package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.fluids.hoe.*;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluidStack;

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
	public static AirLiquid air_liquid;
	
	public static OxygenGas oxygen_gas;
	public static NitrogenGas nitrogen_gas;
	
	public static SulphurDioxideGas sulphurdioxide_gas;
	public static SulphurTrioxideGas sulphurtrioxide_gas;
	
	public static SulphuricAcidSolution sulphuricacid_solution;
	
	public static CarbonDioxideGas carbondioxide_gas;
	public static CarbonMonooxideGas carbonmonooxide_gas;
	
	public static EndothermicGas endothermic_gas;
	public static FormingGas forming_gas;
	
	private static HOEFluidStack[] composition_air;
	
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
		air_liquid = new AirLiquid();
		registry.register(air_liquid);
		
		
		nitrogen_gas = new NitrogenGas();
		registry.register(nitrogen_gas);
		oxygen_gas = new OxygenGas();
		registry.register(oxygen_gas);		
		
		sulphurdioxide_gas = new SulphurDioxideGas();
		registry.register(sulphurdioxide_gas);
		
		sulphurtrioxide_gas = new SulphurTrioxideGas();
		registry.register(sulphurtrioxide_gas);
		
		sulphuricacid_solution = new SulphuricAcidSolution();
		registry.register(sulphuricacid_solution);
		
		carbondioxide_gas = new CarbonDioxideGas();
		registry.register(carbondioxide_gas);
		
		carbonmonooxide_gas = new CarbonMonooxideGas();
		registry.register(carbonmonooxide_gas);
	
		
		endothermic_gas=new EndothermicGas();
		registry.register(endothermic_gas);
		forming_gas=new FormingGas();		
		registry.register(forming_gas);
		
		//compositions
		composition_air = new HOEFluidStack[]{
			new HOEFluidStack(nitrogen_gas,4),
			new HOEFluidStack(oxygen_gas,1)
		};
	
		
	}

	public static HOEFluidStack[] getAirComposition() {
		return composition_air;
	}
}
