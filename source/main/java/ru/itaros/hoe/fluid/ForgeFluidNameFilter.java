package ru.itaros.hoe.fluid;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import ru.itaros.hoe.fluid.HOEFluid.HOEFluidState;


public class ForgeFluidNameFilter {

	
	public Fluid process(HOEFluid f){
		if(f.getCommonName().equals("fluid.water")){
			return FluidRegistry.WATER;
		}
		//TODO: Railcraft Steam
		
		String name = processName(f.getCommonName());//Fuck TiCo
		
		Fluid fluid = new Fluid(name);
		
		fluid.setGaseous(f.getState()==HOEFluidState.GAS);
		
		
		return fluid;
	}

	/*
	 * This method makes names Forge-compliant
	 */
	private String reverseName(String name) {
		String[] nameparts= name.split("\\.");
		name="";
		for(int i = nameparts.length-1;i>=0;i--){
			name+=nameparts[i]+'.';
		}
		name=name.substring(0, name.length()-1);
		return name;
	}

	private String processName(String commonName) {
		commonName=commonName.replace("fluid.", "");
		
		return commonName;
	}

	private static final CharSequence[] statePostfixes = {
		"gas",
		"solution"
	};
	
	private String cleanFromStatePostfixes(String commonName) {
		for(CharSequence cs:statePostfixes){
			commonName=commonName.replace("."+cs, "");
		}
		return commonName;
	}
	
	
}
