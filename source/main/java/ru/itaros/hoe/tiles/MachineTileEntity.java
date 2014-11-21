package ru.itaros.hoe.tiles;

import java.util.EnumSet;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.tiles.syndication.ISyndicationPiping;
import ru.itaros.chemlab.tiles.syndication.SyndicationControllerDescriptorContainer;
import ru.itaros.chemlab.tiles.syndication.SyndicationHubTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationPipingTileEntity;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.hoe.HOE;
import ru.itaros.hoe.addon.ae.power.IHOEVolatileAEPowerCache;
import ru.itaros.hoe.blocks.IRotationSolver;
import ru.itaros.hoe.blocks.RotatableBlockUtility;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.utils.TileEntityHelper;
import appeng.api.AEApi;
import appeng.api.config.Actionable;
import appeng.api.networking.GridFlags;
import appeng.api.networking.GridNotification;
import appeng.api.networking.IGrid;
import appeng.api.networking.IGridBlock;
import appeng.api.networking.IGridHost;
import appeng.api.networking.IGridNode;
import appeng.api.networking.events.MENetworkEventSubscribe;
import appeng.api.networking.events.MENetworkPowerStatusChange;
import appeng.api.util.AECableType;
import appeng.api.util.AEColor;
import appeng.api.util.DimensionalCoord;
import appeng.util.Platform;

public abstract class MachineTileEntity extends TileEntity implements ISecured, ISyndicationPiping, IRotationSolver, IGridHost, IGridBlock, IHOEVolatileAEPowerCache, IHOEInventorySyncable{

	@Override
	public AECableType getCableConnectionType(ForgeDirection arg0) {
		return AECableType.COVERED;
	}

	
	IGridNode aeNode=null;
	@Override
	public IGridNode getGridNode(ForgeDirection dir) {
		if(aeNode==null && Platform.isServer()){
			aeNode = AEApi.instance().createGridNode(this);
			aeNode.updateState();
			revalidatePowerStatus();
		}
		return aeNode;
	}


	@Override
	public void securityBreak() {
		// TODO Auto-generated method stub
		
	}

	private static EnumSet<ForgeDirection> getcsCache = EnumSet.allOf(ForgeDirection.class);
	private static EnumSet<GridFlags> getflgsCache;
	
	static{
		getflgsCache = EnumSet.noneOf(GridFlags.class);
		getflgsCache.add(GridFlags.CANNOT_CARRY);
	}
	
	
	
	@Override
	public EnumSet<ForgeDirection> getConnectableSides() {
		return getcsCache;
	}

	
	@Override
	public EnumSet<GridFlags> getFlags() {
		return getflgsCache;
	}


	@Override
	public AEColor getGridColor() {
		return AEColor.Transparent;
	}


	@Override
	public double getIdlePowerUsage() {
		return 5;
	}
	
	private long lastTimeStamp=-1;
	private boolean isCharging=false;
	
	@MENetworkEventSubscribe
	public void onPowerStateChange(MENetworkPowerStatusChange event){
		revalidatePowerStatus();
	}
	private void revalidatePowerStatus(){
		if(aeNode!=null){
			if(lastTimeStamp<0){
				//No known universe state in existed timeframe
				lastTimeStamp=this.worldObj.getWorldTime();
				isCharging=aeNode.isActive();
				return;
			}else{
				if(isCharging){
					//Was charging
					long endTime=this.worldObj.getWorldTime();
					long totalTime = endTime - lastTimeStamp;
					if(totalTime<0){
						//Inverted due to sun rotation lol
						totalTime = 24000L+totalTime;//reversal cutoff
					}
					
					double aeSumUp = totalTime*getIdlePowerUsage();
					injectAEPower(aeSumUp, Actionable.MODULATE);
					//System.out.println("Injected: "+this.getAECurrentPower()+"/"+this.getAEMaxPower());
					
					lastTimeStamp=this.worldObj.getWorldTime();
					isCharging=aeNode.isActive();
					
					
				}else{
					//Was doing nothing
					lastTimeStamp=this.worldObj.getWorldTime();
					isCharging=aeNode.isActive();
					return;
				}
			}
			
		}
	}
	

	@Override
	public DimensionalCoord getLocation() {
		return new DimensionalCoord(this);
	}


	@Override
	public IGridHost getMachine() {
		return this;
	}


	@Override
	public ItemStack getMachineRepresentation() {
		return new ItemStack(BlockLoader.casing);
	}


	@Override
	public void gridChanged() {
		revalidatePowerStatus();
		
	}


	@Override
	public boolean isWorldAccessible() {
		return true;
	}


	@Override
	public void onGridNotification(GridNotification arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNetworkStatus(IGrid arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	protected volatile double localAEPower=0;
	protected static double localAEPowerMax = 1000;
	
	@Override
	public double extractAEPower(double amt, Actionable mode) {
		return 0;//Can't extract
	}

	@Override
	public double injectAEPower(double amt, Actionable mode) {
		double d = getAEMaxPower()-getAECurrentPower();
		if(d>0D){
			if(amt<=d){
				if(mode==Actionable.MODULATE){
					localAEPower+=amt;
				}
				return 0;
			}else{
				double leftovers = amt-d;
				if(mode==Actionable.MODULATE){
					localAEPower+=d;
				}
				return leftovers;
			}
		}else{
			return amt;//Can't receive
		}
	}
	
	@Override
	public double getAECurrentPower() {
		return localAEPower;
	}


	@Override
	public double getAEMaxPower() {
		return localAEPowerMax;
	}



	

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
		
		localAEPower=nbt.getDouble("ae");
		
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
		
		nbt.setDouble("ae", localAEPower);
		
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
		
		//AE
		
		getGridNode(ForgeDirection.UNKNOWN);
		
		//HOE
		
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
			
			if(hoeio==null){
				//This is bad. This TE has no HOE identity. PANIC!!!
				System.out.println(this.getClass().getName()+" lost its HOE identity. Invalidating!");
				this.invalidate();
				return;
			}
			
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
	
	
	//AE->HOE POWER
	//CIO
	private int pushToHOECIOSequence=0;
	private int pullFromHOECIOSequence=0;
	@Override
	public void pushToHOE() {
		//CIO		
		if(this instanceof IConfigurableIO){
			IConfigurableIO cio = (IConfigurableIO)this;
			PortInfo[] pis = cio.getPorts();
			if(pushToHOECIOSequence<pis.length){
				PortInfo p = pis[pushToHOECIOSequence];
				if(p!=null){
					if(p.isItemSocket() && p.isInput()){
						p.setStack(TileEntityHelper.HOEItemPush(this, (ItemStack)p.getStack()));
					}
				}
				pushToHOECIOSequence++;
			}else{
				pushToHOECIOSequence=0;
			}			
		}
		
		
		//Power Transfer
		revalidatePowerStatus();
		
		HOEMachineData c = getServerData();
		if(c==null){return;}
		double overflow = c.incrementPower(getAECurrentPower());
		this.localAEPower=overflow;
	}


	@Override
	public void pullFromHOE() {
		//CIO		
		if(this instanceof IConfigurableIO){
			IConfigurableIO cio = (IConfigurableIO)this;
			PortInfo[] pis = cio.getPorts();
			if(pullFromHOECIOSequence<pis.length){
				PortInfo p = pis[pullFromHOECIOSequence];
				if(p!=null){
					if(p.isOutput()){
						if(p.isItemSocket()){
							p.setStack(TileEntityHelper.HOEItemPull(this, (ItemStack)p.getStack()));
						}else if(p.isFluidSocket()){
							p.setStack(TileEntityHelper.HOEFluidPull(this, (FluidStack) p.getStack()));
						}
					}
				}
				pullFromHOECIOSequence++;
			}else{
				pullFromHOECIOSequence=0;
			}			
		}		
		
	}	
	
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
	public World getWorld() {
		return this.worldObj;
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
	
	@Override
	public void invalidate() {
		if(aeNode!=null){
			aeNode.destroy();
		}
		super.invalidate();
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
	
	
	//Rotation
	@Override
	public int getRealSide(int side,int dir) {
		return RotatableBlockUtility.getIconIndiceFromSideGrid(side,dir,RotatableBlockUtility.DEFAULTSIDEGRID);
	}
	public static final ForgeDirection[] rotationChain={ForgeDirection.SOUTH,ForgeDirection.EAST,ForgeDirection.NORTH,ForgeDirection.WEST};

	@Override
	public ForgeDirection[] getRotationChain() {
		return rotationChain;
	}	
	
}