package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity;

import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineCrafterIO;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public abstract class MachineTileEntity extends TileEntity {

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
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeServerState(nbt);
	}
	public abstract HOELinker getLinker();

	public abstract HOEMachineIO getSuperIO();

	protected HOEMachineData server;
	protected HOEMachineData client;

	public MachineTileEntity() {
		super();
		
		hoeio =  getSuperIO();
		
		if(ContextDetector.getInstance().getContext()!=FMLContext.CLIENT){
			HOEMachines machines = ((HOEMachines)getLinker().getMyMachinesHandler());
			server=acquareData(machines);
			
			
			hoeio.configureData(server);
			
			client=(HOEMachineData) server.getChild();

		}else{
			//TODO: Fix this for INTEGRATED SERVER CONTEXT
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
		if(context != FMLContext.CLIENT){
			return false;
		}else{
			return true;
		}
	}

}