package ru.itaros.toolkit.hoe.machines.basic;

import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.exceptions.HOEWrongSyncDirection;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class HOEMachineData extends HOEData {

	/*
	 * Reflection autocaster
	 */
	public HOEMachineData(HOEData parent){
		this((HOEMachineData)parent);
		//Reflection autocaster
	}
	protected HOEMachineData(HOEMachineData parent){
		super();
		this.isSided=true;
		bindChildToParent(parent);
	}	
	
	protected int power;

	public void readNBT(NBTTagCompound nbt){
		//super.readNBT(nbt);
		//Power
		power=nbt.getInteger("power");
		maxpower=nbt.getInteger("maxpower");
		//FMLLog.log(Level.INFO, "Maxpower was:"+maxpower);
		//Ticks
		ticksRequared=nbt.getInteger("ticksRequared");
		ticksAccumulated=nbt.getInteger("ticksAccumulated");
		//IO recognition Signature
		String ioname = nbt.getString("io_sign");
		if(ioname!=""){
			io =  (HOEMachineIO) HOEIO.getIORegistry().get(ioname);
		}
	}

	public void writeNBT(NBTTagCompound nbt){
		//super.writeNBT(nbt);
		//Power
		nbt.setInteger("power", power);
		nbt.setInteger("maxpower", maxpower);
		//Ticks
		nbt.setInteger("ticksRequared", ticksRequared);
		nbt.setInteger("ticksAccumulated", ticksAccumulated);
		//IO recognition Signature
		if(io!=null){
			nbt.setString("io_sign", io.getClass().getName());
		}
	}
	
	/*
	 * This method initialized data for first use
	 */
	protected void init(){}

	public void sync(){
		if(child==null){throw new HOEWrongSyncDirection();}
	}
	
	/*
	 * This methods creates initial HARDLINK between child and client
	 */
	protected void bindChildToParent(HOEMachineData parent){};

	protected int maxpower;
	public int ticksRequared;

	public int getPower() {
		return power;
	}

	public int getPowerMax() {
		return maxpower;
	}

	protected int ticksAccumulated = 0;
	protected HOEMachineIO io;

	public int getTicks() {
		return ticksAccumulated;
	}

	public void incrementTick() {
		ticksAccumulated++;
	}

	public void voidTicks() {
		ticksAccumulated=0;
	}

	private boolean isConfigured = false;

	public void setMaxPower(int maxpower) {
		this.maxpower=maxpower;
	}

	public void setMachine(HOEMachineIO io) {
		this.io=io;
	}

	public static HOEMachineData generateFromNBT(String refltype, NBTTagCompound nbt) {
		//HOEMachineData data = new HOEMachineData();
		HOEMachineData data=null;
		if(refltype==""){
			data = new HOEMachineData();
		}else{
			try{
				data = (HOEMachineData) Class.forName(refltype).getConstructor().newInstance();
			}catch(Exception e){
				System.err.println("REFL: "+refltype);
				throw new RuntimeException(e);//TODO: Data lookup failure exception
			}
		}
		//data.readNBT(nbt);
		return data;
	}

	public void setConfigured() {
		init();
		child=spawnChild();
		sync();
		isConfigured=true;
	}

	@Override
	public boolean isConfigured() {
		return isConfigured;
	}

	public HOEMachineIO getIO() {
		return io;
	}

	public int incrementPower(int i) {
		power+=i;
		if(power>maxpower){
			int overflow = power-maxpower;
			return overflow;
		}
		return 0;
	}

	public HOEMachineData() {
		super();
	}

}