package ru.itaros.hoe.fluid;

import ru.itaros.chemlab.fluids.hoe.Water;
import ru.itaros.hoe.fluid.HOEFluid.HOEFluidState;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;


public class ForgeFluidNameFilter {

	
	public Fluid process(HOEFluid f){
		if(f instanceof Water){
			return FluidRegistry.WATER;
		}
		//TODO: Railcraft Steam
		
		String name = processName(f.getCommonName());
		name = reverseName(name);
		
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
			name+=nameparts[i];
		}
		return name;
	}

	private String processName(String commonName) {
		commonName=commonName.replace('-', '.');//Quick Fix for namederps
		
		commonName=cleanFromStatePostfixes(commonName);
		
		
		
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
