package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ru.itaros.api.hoe.HOEAbstractLinker;
import ru.itaros.hoe.HOE;
import ru.itaros.hoe.proxy.HOEServer;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


public abstract class MachineTileEntity extends TileEntity implements IInventory{

	
	
	
	@Override
	public Packet getDescriptionPacket() {
		//this.xCoord, this.yCoord, this.zCoord, 1, nbtTag
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		S35PacketUpdateTileEntity pkt = new S35PacketUpdateTileEntity(this.xCoord,this.yCoord,this.zCoord,1,nbt);
		return pkt;
	}
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		super.onDataPacket(net,pkt);
		this.readFromNBT(pkt.func_148857_g());//Meh. This gets NBT
	}



	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		readServerState(nbt);
	}



	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		writeServerState(nbt);
	}
	
	private void readServerState(NBTTagCompound host) {
		NBTTagCompound nbt = host.getCompoundTag("hoemdata");
		
		//DUMMY: ASSUMING THIS IS A CLIENT
		client=HOEMachineData.generateFromNBT(nbt);
		

	}	

	private void writeServerState(NBTTagCompound host) {
		NBTTagCompound nbt = new NBTTagCompound();
		if(server!=null){
			//HOE is alive and this is a server
			server.writeNBT(nbt);
		}else if(client!=null){
			//HOE is dead or this is a client
			client.writeNBT(nbt);
		}else{
			//HOE is dead and this is nothing
		}
		host.setTag("hoemdata", nbt);
	}

	//@SideOnly(Side.SERVER)//Forge design bug!
	public abstract HOEAbstractLinker getLinker();
	
	public abstract HOEMachineIO getSuperIO();
	
	private HOEMachineIO hoeio;
	
	private HOEMachineData server;
	private HOEMachineData client;
	
	public HOEMachineData getClientData(){
		return client;
	}
	
	
	public MachineTileEntity(){
		super();
		hoeio = getSuperIO();
		if(HOE.proxy instanceof HOEServer){
			HOEMachines machines = ((HOEMachines)getLinker().getJob());
			server=machines.generateMachineData();
			
			
			hoeio.configureData(server);
			
			client=(HOEMachineData) server.getChild();

		}else{
			//Client configuration is simplier	
			//It just WAITS for it to be passed over network
		}
	}
	


	//INVENTORY	

	ItemStack inbound_synchro;//0
	ItemStack outbound_synchro;//1
	
	
	ItemStack inbound_ro;
	ItemStack outbound_ro;
	//-1 = client.inbound
	//-2 = client.outbound
	
	private void updateRO(){
		if(client!=null){
			inbound_ro=client.getInboundRO();
			outbound_ro=client.getOutboundRO();
		}
	}
	
	
	@Override
	public int getSizeInventory() {
		return 1+1;//in and out synchrobound! %_%
	}
	@Override
	public ItemStack getStackInSlot(int slot) {
		switch(slot){
		case 0:
			return inbound_synchro;
		case 1:
			return outbound_synchro;
			
		case -1:
			return inbound_ro;
		case -2:
			return outbound_ro;			
			
		}
		return null;
	}
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		switch(slot){
		case 0:
			inbound_synchro=stack;
			break;
		case 1:
			outbound_synchro=stack;
			break;
		case 10://DEBUG RECIPE SETTER
			Item[] items = new Item[]{stack.getItem()};
			getSuperIO().setRecipe(server,items);
			break;
		}		
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}
	
	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack result = null;
		switch(slot){
		case 0:
			inbound_synchro.stackSize-=amount;
			result=inbound_synchro.copy();
			break;
		case 1:
			outbound_synchro.stackSize-=amount;
			result=outbound_synchro.copy();
			break;
		}	
		return result;
	}
	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;//Do not drops
	}
	@Override
	public String getInventoryName() {
		return "HOEMachineInventory";
	}
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}
	@Override
	public void openInventory() {
	}
	@Override
	public void closeInventory() {
	}
	
	
	
	
	//Sync
	@Override
	public void updateEntity() {
		if(client!=null){
			HOEMachineIO io = client.getIO();
			if(io!=null){
				//CLIENTSIDE TICK in MTA
				io.tick(client);
				updateRO();
			}
		}		

	}
	@Override
	public boolean canUpdate() {
		return true;//SHOULD BE FALSE ON SERVER	
	}
	public void pullFromHOE() {
		if(server!=null){

			outbound_synchro = server.pullProduct(outbound_synchro);

		}
		
	}
	public void pushToHOE() {
		if(server!=null){
			if(inbound_synchro!=null){
				ItemStack temp = inbound_synchro;
				inbound_synchro=null;
				if(!server.pushResource(temp)){
					//FAILED!
				}
			}
		}
		
	}
	public void sync() {
		if(server!=null){
			server.sync();
			worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
		}
		
	}
	
	
	
	
	
	
	
}
