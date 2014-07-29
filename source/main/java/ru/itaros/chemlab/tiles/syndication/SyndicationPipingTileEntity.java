package ru.itaros.chemlab.tiles.syndication;

import ru.itaros.chemlab.loader.DamageSourceLoader;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SyndicationPipingTileEntity extends TileEntity implements ISyndicationPiping {

	public SyndicationPipingTileEntity(){
		syndicationDescriptor = new SyndicationControllerDescriptorContainer(this);
	}
	
	
	
	protected SyndicationControllerDescriptorContainer syndicationDescriptor;
	
	
	
	public ISyndicationPiping setBlockMetadata() {
		int meta = syndicationDescriptor.getMode().ordinal();
		this.worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta,2);
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
	public World getWorld() {
		return this.worldObj;
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
	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		syndicationDescriptor.readFromNBT(p_145839_1_);
	}
	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
		syndicationDescriptor.writeToNBT(p_145841_1_);
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
	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta,
			int newMeta, World world, int x, int y, int z) {
		return oldBlock != newBlock;
	}
	
	
	@Override
	public boolean askControllerToDie(EntityPlayer player){
		return utility_askControllerToDie(player, this);
	}
	
	@Override
	public void setControllerOverloaded(){
		utility_setControllerOverloaded(this);
	}
	
	public static void utility_setControllerOverloaded(ISyndicationPiping piping){
		World w = piping.getWorld();
		if(w==null){return;}//No world = no controller
		TileEntity te = w.getTileEntity(piping.getController_x(), piping.getController_y(), piping.getController_z());
		if(te==null){return;}//No TE = no controller
		if(te instanceof SyndicationHubTileEntity){		
			SyndicationHubTileEntity shte = (SyndicationHubTileEntity)te;
			if(shte.isConfigured()){	
				shte.setOverloaded();
				//world.createExplosion(null, tx, ty, tz, 4.0F, true);
			}
		}
	}
	public static boolean utility_askControllerToDie(EntityPlayer player, ISyndicationPiping piping){
		World w = piping.getWorld();
		if(w==null){return true;}//No world = no controller
		TileEntity te = w.getTileEntity(piping.getController_x(), piping.getController_y(), piping.getController_z());
		if(te==null){return true;}//No TE = no controller
		if(te instanceof SyndicationHubTileEntity){
			SyndicationHubTileEntity shte = (SyndicationHubTileEntity)te;
			if(shte.isConfigured()){
				//Not disengaged. Teach player a lesson.
				player.attackEntityFrom(DamageSourceLoader.syndic_electro, 10);
				return false;
			}else{
				return true;//It is already disengaged. No problems here.
			}
		}else{
			return true;//What the hell is it? It is not a controller
		}		
	}
	
	
	
}
