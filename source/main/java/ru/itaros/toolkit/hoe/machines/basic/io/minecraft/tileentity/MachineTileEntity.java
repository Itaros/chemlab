package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.ISecured;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.TileEntitySecurityTracker;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public abstract class MachineTileEntity extends TileEntity implements IPowerReceptor, ISecured {

	protected HOEMachineIO hoeio;

	public void sync() {
		if(server!=null){
			server.sync();
			worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
		}
		
	}

	public abstract void updateEntity();

	protected abstract HOEMachineData acquareData(HOEMachines machines);

	
	private void readServerState(NBTTagCompound host) {
		NBTTagCompound nbt = host.getCompoundTag("hoemdata");
		
		//DUMMY: ASSUMING THIS IS A CLIENT
		String refltype=nbt.getString("refltype");
		if(server==null){
			server=HOEMachineData.generateFromNBT(refltype,nbt);
		}
		this.getSuperIO().configureData(server);
		server.readNBT(nbt);
		//client = (HOEMachineData) server.getChild();//For some reason this is not working
		if(ContextDetector.getInstance().getContext()==FMLContext.CLIENT){
			client = server;//ONLY IF IT IS CLIENT!
		}else{
			client = (HOEMachineData) server.getChild();
			server.sync();
		}
	}	

	
	private void writeServerState(NBTTagCompound host) {
		NBTTagCompound nbt = new NBTTagCompound();
		if(server!=null){
			//HOE is alive and this is a server
			nbt.setString("refltype", server.getClass().getName());
			server.writeNBT(nbt);
		}else if(client!=null){
			//HOE is dead or this is a client
			client.writeNBT(nbt);
		}else{
			//HOE is dead and this is nothing
		}
		host.setTag("hoemdata", nbt);
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		//System.out.println("Deserializing TE: "+this.getClass().getSimpleName());
		readServerState(nbt);
		MJ.readFromNBT(nbt, "mj");
		security.readFromNBT(nbt, "security");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeServerState(nbt);
		MJ.writeToNBT(nbt, "mj");
		security.writeToNBT(nbt, "security");
	}
	public abstract HOELinker getLinker();

	public abstract HOEMachineIO getSuperIO();

	protected HOEMachineData server;
	protected HOEMachineData client;

	public MachineTileEntity() {
		super();
		
		initMJ();//MJ, MC side
		
		//HOETHR
		hoeio =  getSuperIO();
		
		if(ContextDetector.getInstance().getContext()!=FMLContext.CLIENT){
			HOEMachines machines = ((HOEMachines)getLinker().getMyMachinesHandler());
			server=acquareData(machines);
			
			
			hoeio.configureData(server);
			
			client=(HOEMachineData) server.getChild();
			//server.sync();

		}else{
			//Client configuration is simplier	
			//It just WAITS for it to be passed over network
		}
	}

	public HOEMachineData getClientData() {
		return client;
	}
	public HOEMachineData getServerData() {
		return server;
	}	

	@Override
	public boolean canUpdate() {
		FMLContext context = ContextDetector.getInstance().getContext();
		if(context == FMLContext.CLIENT || context==FMLContext.INTEGRATED){
			return true;
		}else{
			return false;
		}
	}


	//SECURITY
	
	TileEntitySecurityTracker security = new TileEntitySecurityTracker();
	
	public TileEntitySecurityTracker getSecurity(){
		return security;
	}
	
	
	//MJ POWER
	
	/*
	 * Convenience method. Provides a way to get a synched client before actual sync operation
	 */
	private void clientPowerInjection(int power){
		if(ContextDetector.getInstance().getContext()!=FMLContext.DEDICATED){
			HOEMachineData c = getClientData();
			if(c==null){return;}
			c.incrementPower(power);
		}
	}
	
	@Override
	public void doWork(PowerHandler handler) {
		HOEMachineData s = getServerData();
		if(s==null){return;}
		
		double diff = (s.getPowerMax()-s.getPower());
		if(diff>=MJ_BATCH){
			double used = handler.useEnergy(MJ_BATCH, MJ_BATCH, true);
			//TODO: Is this "used" needed?
			s.incrementPower(MJ_BATCH);
			clientPowerInjection(MJ_BATCH);
			//This is save because it is ATOMIC
		}
		
	}

	public static final int MJ_MIN=1;
	public static final int MJ_BATCH=ChemLabValues.ENERGY_FRACTION;	
	public static final int MJ_MAX=MJ_BATCH;
	public static final int MJ_CAPACITY=1000;

	
	private void initMJ(){
		MJ = new PowerHandler(this, PowerHandler.Type.MACHINE);
		MJ.configure(MJ_MIN, MJ_MAX, MJ_BATCH, MJ_CAPACITY);
	}
	
	private PowerHandler MJ;
	@Override
	public PowerReceiver getPowerReceiver(ForgeDirection arg0) {
		return MJ.getPowerReceiver();
	}

	@Override
	public World getWorld() {
		return this.worldObj;
	}	
	
	
	public double getCurrentMJ(){
		return MJ.getEnergyStored();
	}
	public double getMaximumMJ(){
		return MJ.getMaxEnergyStored();
	}
	
	
	//HOE Interop

	public void invalidateHOEData() {
		if(server!=null){
			server.invalidate();
		}
	}

}