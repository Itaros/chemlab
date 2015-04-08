package ru.itaros.hoe.data.machines;

import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.exceptions.HOEWrongSyncDirection;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;

public abstract class HOEMachineData extends HOEData {

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
	
	
	private HOEDataFingerprint ownerFingerprint;
	public void setOwnerFingerprint(HOEDataFingerprint fingerprint){
		ownerFingerprint=fingerprint;
	}
	public HOEDataFingerprint getOwnerFingerprint(){
		return ownerFingerprint;
	}
	
	protected double power;

	@Deprecated
	public void readNBT(NBTTagCompound nbt){
		//Inventory
		readInventoryNBT(nbt);
		//Configuration
		readConfigurationNBT(nbt);
		//IO recognition Signature
		String ioname = nbt.getString("io_sign");
		if(ioname!=""){
			io =  (HOEMachineIO) HOEIO.getIORegistry().get(ioname);
		}
	}
	
	public final void readNBT(HOENBTManifold manifold) {
		readInventoryNBT(manifold.holdInvetory());
		readConfigurationNBT(manifold.holdConfiguration());
		readSyndicationNBT(manifold.holdSyndication());
		
		String ioname = manifold.holdTypeFactoryData().getString("io_sign");
		if(ioname!=""){
			io =  (HOEMachineIO) HOEIO.getIORegistry().get(ioname);
		}		
	}	
	
	protected void readInventoryNBT(NBTTagCompound nbt){
		power=nbt.getDouble("power");
		ticksAccumulated=nbt.getInteger("ticksAccumulated");
	}
	protected void writeInventoryNBT(NBTTagCompound nbt){
		nbt.setDouble("power", power);
		nbt.setInteger("ticksAccumulated", ticksAccumulated);
	}
	protected void readConfigurationNBT(NBTTagCompound nbt){
		//Power
		maxpower=nbt.getDouble("maxpower");
		//FMLLog.log(Level.INFO, "Maxpower was:"+maxpower);
		//Ticks
		ticksRequared=nbt.getInteger("ticksRequared");
	}
	protected void writeConfigurationNBT(NBTTagCompound nbt){
		//Power
		nbt.setDouble("maxpower", maxpower);	
		//Ticks
		nbt.setInteger("ticksRequared", ticksRequared);	
	}
	
	protected void readSyndicationNBT(NBTTagCompound nbt){
		//Operational state
		isSyndicated=nbt.getBoolean("isSyndicated");
	}
	protected void writeSyndicationNBT(NBTTagCompound nbt){
		//Operational state
		nbt.setBoolean("isSyndicated",isSyndicated);	
	}
	
	@Deprecated
	public void writeNBT(NBTTagCompound nbt){
		//Inventory
		writeInventoryNBT(nbt);
		//Configuration
		writeConfigurationNBT(nbt);
		//IO recognition Signature
		if(io!=null){
			nbt.setString("io_sign", io.getClass().getName());
		}
	}

	public final void writeNBT(HOENBTManifold manifold) {
		writeInventoryNBT(manifold.holdInvetory());
		writeConfigurationNBT(manifold.holdConfiguration());
		writeSyndicationNBT(manifold.holdSyndication());
		if(io!=null){
			manifold.holdTypeFactoryData().setString("io_sign", io.getClass().getName());
		}
	}	
	
	/*
	 * This method initialized data for first use
	 */
	protected void init(){}

	/*
	 * Return true if sync-step is passed
	 */
	public boolean sync(){
		if(child==null){
			//throw new HOEWrongSyncDirection();
			return false;//Sync is halted in that case. No child!
		}
		
		
		HOEMachineData childd=(HOEMachineData) child;
		childd.power=power;
		childd.maxpower=maxpower;
			
		childd.ticksRequared=ticksRequared;
		childd.ticksAccumulated=ticksAccumulated;
		
		return true;
	}
	
	/*
	 * This methods creates initial HARDLINK between child and client
	 */
	protected void bindChildToParent(HOEMachineData parent){
		this.io=parent.io;
		this.isConfigured=parent.isConfigured;
	};

	protected double maxpower;
	public int ticksRequared;

	public double getPower() {
		return power;
	}

	public double getPowerMax() {
		return maxpower;
	}
	
	public double getNeededPower(){
		return maxpower-power;
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

	public static HOEMachineData generateByRefType(String refltype) {
		//HOEMachineData data = new HOEMachineData();
		HOEMachineData data=null;
		if(refltype==""){
			data = null;
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

	/*
	 * This method signs data off as configured
	 * and spawns a child for read access
	 */
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

	public double incrementPower(double i) {
		power+=i;
		if(power>maxpower){
			double overflow = power-maxpower;
			power-=overflow;
			return overflow;
		}
		return 0;
	}

	public void decrementPower(double powerReqPerTick) {
		power-=powerReqPerTick;
		if(power<0){power=0;}
	}	
	
	public HOEMachineData() {
		super();
	}
	
	
	private boolean isSyndicated=false;

	/*
	 * This method provides a way to distinguish blocks which are synced regardless of syndication state
	 */
	public boolean isPerformingBlockUpdates(){
		return !isSyndicated();
	}
	
	public boolean isSyndicated() {
		return isSyndicated;
	}
	public void setSyndicated(boolean isSyndicated) {
		this.isSyndicated = isSyndicated;
	}
	@Override
	public boolean isRunning() {
		return super.isRunning() & isPerformingBlockUpdates();
	}
	public HOEMachineData makeRemote() {
		this.isSided=true;
		return this;
	}

}