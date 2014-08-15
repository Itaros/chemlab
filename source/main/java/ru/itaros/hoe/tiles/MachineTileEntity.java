package ru.itaros.hoe.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.chemlab.tiles.syndication.ISyndicationPiping;
import ru.itaros.chemlab.tiles.syndication.SyndicationControllerDescriptorContainer;
import ru.itaros.chemlab.tiles.syndication.SyndicationHubTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationPipingTileEntity;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.hoe.HOE;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import buildcraft.api.power.IPowerReceptor;
import buildcraft.api.power.PowerHandler;
import buildcraft.api.power.PowerHandler.PowerReceiver;

public abstract class MachineTileEntity extends TileEntity implements IPowerReceptor, ISecured, ISyndicationPiping {

	protected SyndicationControllerDescriptorContainer syndicationDescriptor;
	
	protected HOEMachineIO hoeio;

	public void sync() {
		if(server!=null){
			server.sync();
			worldObj.markBlockForUpdate(xCoord,yCoord,zCoord);
		}
		
	}

	//sync
	private boolean doReal = (ContextDetector.getInstance().getContext()==FMLContext.CLIENT || ContextDetector.getInstance().getContext()==FMLContext.INTEGRATED);
	@Override
	public void updateEntity(){
		if(client!=null){
			HOEMachineIO io = client.getIO();
			if(io!=null){
				//CLIENTSIDE TICK in MTA
				io.tick(client, doReal);
			}
		}	
	}

	
	@Override
	public void setWorldObj(World world) {
		super.setWorldObj(world);
		if(dedicated && HOE.getInstance().getConfig().threading_keepalive){
			HOE.getInstance().getKeepAlive().onLoadTryInject(this);
		}		
	}
	
	protected abstract HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint);

	private boolean dedicated = ContextDetector.getInstance().getContext()!=FMLContext.CLIENT;
	private void readServerState(NBTTagCompound nbt) {
		if(!isDataAlreadyInjected){
			
			//DUMMY: ASSUMING THIS IS A CLIENT
			String refltype=nbt.getString("refltype");
			if(server==null){
				server=HOEMachineData.generateByRefType(refltype);
			}
			this.getSuperIO().configureData(server);
			server.readNBT(nbt);
			//client = (HOEMachineData) server.getChild();//For some reason this is not working
			if(ContextDetector.getInstance().getContext()==FMLContext.CLIENT){
				client = server.makeRemote();//ONLY IF IT IS CLIENT!
			}else{
				client = (HOEMachineData) server.getChild();
				server.sync();
			}
		}else{
			server.sync();
		}
	}	

	private HOENBTManifold writeServerStateAsNBTManifold(){
		HOENBTManifold manifold = new HOENBTManifold();
		HOEMachineData data = getAvailableData();
		NBTTagCompound tfd=manifold.holdTypeFactoryData();
		writeTypeFactoryNBT(tfd,data);
		data.writeNBT(manifold);
		return manifold;
	}
	private void readServerStateAsNBTManifold(HOENBTManifold manifold){
		//Generating HOEData
		if(!isDataAlreadyInjected){//some HOEKA magic
			if(server==null){
				String refltype=manifold.holdTypeFactoryData().getString("refltype");
				server=HOEMachineData.generateByRefType(refltype);
			}
			this.getSuperIO().configureData(server);
			server.readNBT(manifold);
			if(ContextDetector.getInstance().getContext()==FMLContext.CLIENT){
				client = server.makeRemote();//ONLY IF IT IS CLIENT!
			}else{
				client = (HOEMachineData) server.getChild();
				server.sync();
			}
		}	
	}
	
	private void writeTypeFactoryNBT(NBTTagCompound nbt, HOEMachineData data){
		if(data!=null){
			nbt.setString("refltype", data.getClass().getName());
		}else{
			//TODO: set manifold abort flag(context is not ready)
		}
	}
	
	private HOEMachineData getAvailableData(){
		//HOE is alive and this is a server
		if(server!=null){return server;}
		//HOE is dead or this is a client
		if(client!=null){return client;}
		//HOE is dead and this is nothing
		return null;
	}
	
	@Deprecated
	private void writeServerState(NBTTagCompound host) {
		NBTTagCompound nbt = new NBTTagCompound();
		if(server!=null){
			//HOE is alive and this is a server
			nbt.setString("refltype", server.getClass().getName());
			server.writeNBT(nbt);
		}else if(client!=null){
			//HOE is dead or this is a client
			nbt.setString("refltype", client.getClass().getName());
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
		if(waitsForPostload){
			cachedPayload=(NBTTagCompound) nbt.getCompoundTag("hoemdata").copy();
		}else{
			readServerState(nbt.getCompoundTag("hoemdata"));
		}
		MJ.readFromNBT(nbt, "mj");
		security.readFromNBT(nbt, "security");
		
		syndicationDescriptor.readFromNBT(nbt);
		
		if(ContextDetector.getInstance().getContext()==FMLContext.CLIENT){
			this.onPostLoad();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		if(!this.waitsForPostload){
			writeServerState(nbt);//Write
		}else{
			if(cachedPayload!=null){
				nbt.setTag("hoemdata",cachedPayload);//Writeback(unfinished)
			}
		}
		MJ.writeToNBT(nbt, "mj");
		security.writeToNBT(nbt, "security");
		
		syndicationDescriptor.writeToNBT(nbt);
	}
	
	public HOENBTManifold writeBlueprintNBT(){
		//Should not write MJ storage, syndication and security data
		return writeServerStateAsNBTManifold();
	}
	public void readBlueprintNBT(HOENBTManifold manifold){
		//Should not read MJ storage, syndication and security data
		readServerStateAsNBTManifold(manifold);
	}
	
	public abstract HOELinker getLinker();

	public abstract HOEMachineIO getSuperIO();

	protected HOEMachineData server;
	protected HOEMachineData client;

	private HOEDataFingerprint fingerprint;
	protected NBTTagCompound cachedPayload;
	private boolean waitsForPostload=true;
	public void onPostLoad(){
		if(!waitsForPostload){return;}
		waitsForPostload=false;
		isHOEAssumesLoaded=true;
		
		HOE.getInstance().getTESynchroOpManager().register(this);
		
		fingerprint = new HOEDataFingerprint(this);
		
		//HOETHR
		hoeio =  getSuperIO();
		
		if(!this.worldObj.isRemote){//ContextDetector.getInstance().getContext()!=FMLContext.CLIENT
			

			HOEMachines machines = ((HOEMachines)getLinker().getMyMachinesHandler());
			
			HOEMachineData copy = machines.checkForFingerprint(fingerprint);
			if(copy==null){
				server=acquareData(machines,fingerprint);
			}else{
				server=copy;
			}
		
			//}
			
			hoeio.configureData(server);
			
			if(server.getChild()==null){
				client=(HOEMachineData) server.getChild();
			}else{
				this.client=(HOEMachineData) server.getChild();
			}
			//server.sync();

		}else{
			//Client configuration is simplier	
			//It just WAITS for it to be passed over network
		}
		
		//Load data
		if(cachedPayload!=null){
			readServerState(cachedPayload);
		}
		
	}
	
	public MachineTileEntity() {
		super();
		
		syndicationDescriptor = new SyndicationControllerDescriptorContainer(this);
		
		initMJ();//MJ, MC side
		
		HOE.getInstance().getTEPostLoadManager().pushTile(this);
		
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
	public static final int MJ_MAX=1024;
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

	
	
	
	
	
	@Override
	public void onChunkUnload() {
		isHOEAssumesLoaded=false;
		if(this.worldObj!=null && !this.worldObj.isRemote){
			if(HOE.getInstance().getConfig().threading_keepalive){
				HOE.getInstance().getKeepAlive().onUnloadCatch(this);;
			}else{
				this.invalidateHOEData();
			}
		}
		super.onChunkUnload();
	}

	//NBT SYNC
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

	
	/*
	 * This shit overrides ALL HOE LOADING PROCEDURES.
	 * It is used in HOEKeepAlive system.
	 * Be save around trains
	 */
	private boolean isDataAlreadyInjected=false;
	public void injectData(HOEData data) {
		server=(HOEMachineData) data;
		client=(HOEMachineData) server.getChild();
		isDataAlreadyInjected=true;
	}
	
	
	
	
	//ISyndicationPiping
	public ISyndicationPiping setBlockMetadata() {
		//Does nothing in blocks
		return this;
	}
	@Override
	public int getX() {
		return this.xCoord;
	}
	@Override
	public int getY() {
		return this.yCoord;
	}
	@Override
	public int getZ() {
		return this.zCoord;
	}
	@Override
	public PipingMode getMode() {
		return syndicationDescriptor.getMode();
	}
	@Override
	public ISyndicationPiping setMode(PipingMode mode) {
		syndicationDescriptor.setMode(mode);
		return this;
	}
	@Override
	public int getHeat() {
		return syndicationDescriptor.getHeat();
	}
	@Override
	public ISyndicationPiping setHeat(int heat) {
		syndicationDescriptor.setHeat(heat);
		return this;
	}
	@Override
	public ISyndicationPiping setController(SyndicationHubTileEntity controller) {
		syndicationDescriptor.setController(controller);
		return this;
	}
	@Override
	public int getController_x() {
		return syndicationDescriptor.getController_x();
	}
	@Override
	public int getController_y() {
		return syndicationDescriptor.getController_y();
	}
	@Override
	public int getController_z() {
		return syndicationDescriptor.getController_z();
	}
	//TODO: possible derp. Is that world needed? We can acces it from TE
	//...but... it can be in another world. Oh god...
	@Override
	public SyndicationHubTileEntity getController(World world) {
		return syndicationDescriptor.getController(world);
	}
	@Override
	public ISyndicationPiping setClear() {
		syndicationDescriptor.setClear();
		return this;
	}
	@Override
	public ISyndicationPiping setAdHoc() {
		syndicationDescriptor.setAdHoc();
		return this;
	}	
	
	
	@Override
	public boolean askControllerToDie(EntityPlayer player){
		return SyndicationPipingTileEntity.utility_askControllerToDie(player, this);
	}	
	@Override
	public void setControllerOverloaded(){
		SyndicationPipingTileEntity.utility_setControllerOverloaded(this);
	}


	public void enforceBlockUpdate() {
		Block b = this.worldObj.getBlock(xCoord, yCoord, zCoord);
		b.updateTick(worldObj, xCoord, yCoord, zCoord, null);
	}
	
	
	public void onSynchroOperationsUpdate(){
		if(this instanceof IHOEInventorySyncable){
			IHOEInventorySyncable me = (IHOEInventorySyncable)this;
			me.pullFromHOE();
			me.pushToHOE();
			me.sync();
		}	
	}	
	
	
	private boolean isHOEAssumesLoaded=true;
	public boolean isHOEAssumesLoaded(){
		return isHOEAssumesLoaded;
	}
	
	
	private int blockMetaCache;
	public void setBlockMeta(int meta){
		blockMetaCache=meta;
	}
	public int getBlockMeta(){
		return blockMetaCache;
	}
	
}