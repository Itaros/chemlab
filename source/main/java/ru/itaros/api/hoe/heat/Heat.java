package ru.itaros.api.hoe.heat;

import net.minecraft.nbt.NBTTagCompound;

public final class Heat {

	protected Heat(){}
	
	public Heat(long heatCapacity){
		this.heatCapacity=heatCapacity;
	}
	
	protected long energy;// Joules
	
	protected long heatCapacity=1;// Joules/Kelvin
	
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
	
	
}
