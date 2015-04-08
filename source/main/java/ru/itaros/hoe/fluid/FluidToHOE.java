package ru.itaros.hoe.fluid;

import net.minecraftforge.fluids.Fluid;

/*
 * Automatically instrumented utility class to get\set HOE Fluids in Forge Fluids
 */
public class FluidToHOE {
	
	public static Fluid set(Fluid fluid, HOEFluid hoe){
		//!AUTOMATIC INSTRUMENTATION!
		return fluid;
	}	
	
	public static HOEFluid get(Fluid fluid){
		//!AUTOMATIC INSTRUMENTATION!
		return null;
	}
	
	public static boolean isActive(){
		//!AUTOMATIC INSTRUMENTATION!
		//ATTENTION: TO VERIFY INLINE CONSTANTS FROM JDI USE DYNAMIC EVALUATION!
		return false;
	}
	
}
