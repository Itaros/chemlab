package ru.itaros.toolkit.hoe.facilities.fluid;

import ru.itaros.hoe.registries.HOEFluidRegistry;

public abstract class HOEFluid {

	public HOEFluid(){
		detectCommonName();
	}
	
	private void detectCommonName() {
		String d = getUnlocalizedName();
		int index = d.indexOf(":");
		d = d.substring(index+1, d.length());
		commonName=d;
	}

	private String commonName;
	/*
	 * Retrieves name without mod prefix.
	 * Used by autoitems. Like chemlab HiVolumeLiquidCell
	 */
	public final String getCommonName(){
		return commonName;
	}
	
	/*
	 * Gets unlocalized name
	 * e.g. chemlab:extractives:cellulosal:high
	 */
	public abstract String getUnlocalizedName();
	
	
	/*
	 * Hexadecimal number.
	 * e.g. return 0xFFFFFF;//white
	 */
	public abstract int getColor();

	
	
	
	//TODO: HOE-style exception
	public static HOEFluidRegistry getFluidRegistry(){
		try{
		return (HOEFluidRegistry) Class.forName("ru.itaros.hoe.registries.HOEFluidRegistry").getMethod("getInstance").invoke(null);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}