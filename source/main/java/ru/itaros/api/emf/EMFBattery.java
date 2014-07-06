package ru.itaros.api.emf;

import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.minecraft.INBTSerializable;

public class EMFBattery implements INBTSerializable{

	public EMFBattery(int size){
		current=0;
		max=size;
	}
	
	
	protected int current;
	protected int max;
	
	
	public int getStoredAmount(){
		return current;
	}
	
	public int getMaxAmount(){
		return max;
	}
	public int getFreeSpace(){
		return max-current;
	}
	

	public void injectPower(int amount){
		current+=amount;
	}

	public int ejectPower(int amount){
		int ret;
		if(amount>current){
			ret = current;
		}else{
			ret = amount;
		}
		current-=ret;
		return ret;
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		NBTTagCompound md = nbt.getCompoundTag("emf");
		max = md.getInteger("max");
		current = md.getInteger("cur");
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		NBTTagCompound md = new NBTTagCompound();
		md.setInteger("max", max);	
		md.setInteger("cur", current);
		nbt.setTag("emf", md);
	}
	
	
	public void truncatePowerAmountToMax(){
		if(current>max){
			current=max;
		}
	}
	
	
	
	
}
