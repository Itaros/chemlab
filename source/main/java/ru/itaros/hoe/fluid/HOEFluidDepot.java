package ru.itaros.hoe.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class HOEFluidDepot {

	private HOEFluidTank[] tanks=new HOEFluidTank[0];
	private int capacity;
	
	public HOEFluidDepot(int capacity){
		this.capacity=capacity;
	}
	
	public int inject(HOEFluid fluid, int amount, boolean forReal){
		HOEFluidTank tank = getCorrespondingTank(fluid);
		if(tank==null){return 0;}
		
		return tank.inject(amount, forReal);
	}
	
	public int extract(HOEFluid fluid,int amount, boolean forReal){
		HOEFluidTank tank = getCorrespondingTank(fluid);
		if(tank==null){return 0;}
		
		return tank.extract(amount, forReal);
	}
	
	public HOEFluidTank getCorrespondingTank(HOEFluid fluid){
		for(HOEFluidTank t:tanks){
			if(t==null){return null;}
			if(t.getFluid()==fluid){return t;}
		}
		return null;
	}
	
	
	public void configureDepot(HOEFluid... types){
		int i=-1;
		tanks=new HOEFluidTank[types.length];
		for(HOEFluid f:types){
			i++;
			tanks[i]=new HOEFluidTank(f,capacity);
		}
	}
	
	public boolean isDeportReconfigurable(){
		for(HOEFluidTank t:tanks){
			if(t.contains()){return false;}
		}
		return true;
	}

	
	/*
	 * This method extracts fluid only if the required amount is available
	 */
	public boolean extractAtomic(HOEFluid fluid, int amount, boolean forReal) {
		HOEFluidTank tank = getCorrespondingTank(fluid);
		if(tank==null){return false;}
		
		return tank.extractAtomic(amount, forReal);
	}

	
	
	public void writeToNBT(NBTTagCompound nbt, String string) {
		NBTTagCompound deptag = new NBTTagCompound();
		
		NBTTagList tanksnbt = new NBTTagList();
		
		for(HOEFluidTank t:tanks){
			NBTTagCompound tnbt = new NBTTagCompound();
			t.writeToNBT(tnbt);
			tanksnbt.appendTag(tnbt);
		}
		
		deptag.setTag("tanks", tanksnbt);
		nbt.setTag(string, deptag);
		
	}

	public void readFromNBT(NBTTagCompound nbt, String string) {
		NBTTagCompound deptag = nbt.getCompoundTag(string);
		
		NBTTagList tanksnbt = deptag.getTagList("tanks", Constants.NBT.TAG_COMPOUND);
		
		HOEFluidTank[] tanks_tmp = new HOEFluidTank[tanksnbt.tagCount()];
		
		for(int i = 0; i < tanksnbt.tagCount(); i++){
			NBTTagCompound tnbt = tanksnbt.getCompoundTagAt(i);
			HOEFluidTank t = HOEFluidTank.createFromNBT(tnbt);
			tanks_tmp[i]=t;
		}
		tanks = tanks_tmp;
		
	}
	
	
}
