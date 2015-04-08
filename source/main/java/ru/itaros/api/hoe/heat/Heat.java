package ru.itaros.api.hoe.heat;

import net.minecraft.nbt.NBTTagCompound;

public final class Heat {

	protected Heat(){}
	
	public Heat(long heatCapacity){
		this.heatCapacity=heatCapacity;
	}
	
	protected long energy;// Joules
	
	protected long heatCapacity=1;// Joules/Kelvin
	
	public long getEnergy() {
		return energy;
	}	
	
	public int getKelvins(){
		return (int)(energy/heatCapacity);
	}
	
	public void addEnergy(long joules){
		energy+=joules;
	}

	/*
	 * Sync method used in
	 */
	public void syncInto(Heat heat) {
		heat.energy = energy;
		heat.heatCapacity = heatCapacity;
	}

	public long getCapacity() {
		return heatCapacity;
	}
	
	public static Heat readNBT(Heat heat, NBTTagCompound nbt, String string) {
		NBTTagCompound tag = nbt.getCompoundTag(string);
		if(heat==null){
			heat = new Heat();
		}
		heat.energy=tag.getLong("energy");
		heat.heatCapacity=tag.getLong("capacity");
		
		return heat;
	}

	public static void writeNBT(Heat heat, NBTTagCompound nbt, String string) {
		NBTTagCompound tag = new NBTTagCompound();
		nbt.setTag(string, tag);
		
		if(heat!=null){
			tag.setLong("energy", heat.energy);
			tag.setLong("capacity", heat.heatCapacity);
		}
		
	}

	private long splitRatio=2L;
	public long getSplitRatio(){
		return splitRatio;
	}
	/*
	 * Synonym to thermal conductivity resistance but in a form of rate of tick
	 */
	public void setSplitRatio(long ratio){
		splitRatio = ratio;
	}
	
	public void exchange(Heat heat) {
		//Heat hot = heat.energy>energy?heat:this;
		//Heat cold =heat.energy>energy?this:heat; 
		//long hot = heat.energy>energy?heat.energy:energy;
		//long cold = heat.energy>energy?energy:heat.energy;
		//if(hot.energy==cold){return;}//No exchange
		
		
		long TGradient = getKelvins()-heat.getKelvins();
		long halfGrad = TGradient/2L;
		
		long totalResistance = splitRatio + heat.splitRatio;
		
		long energyReqToRampUp = halfGrad*heat.heatCapacity/totalResistance;//Time split
		
		//long diff=(hot.energy-cold.energy)/2L;
		
		energy-=energyReqToRampUp;
		heat.energy+=energyReqToRampUp;
		
		//hot.energy-=diff;
		//cold.energy+=diff;
		
	}

	public void setEnergy(long joules) {
		energy=joules;
	}

	
	
}
