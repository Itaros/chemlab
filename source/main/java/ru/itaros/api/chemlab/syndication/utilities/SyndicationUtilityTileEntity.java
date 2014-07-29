package ru.itaros.api.chemlab.syndication.utilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.tiles.syndication.ISyndicationPiping;
import ru.itaros.chemlab.tiles.syndication.SyndicationControllerDescriptorContainer;
import ru.itaros.chemlab.tiles.syndication.SyndicationHubTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationPipingTileEntity;

public abstract class SyndicationUtilityTileEntity extends TileEntity implements ISyndicationUtility,ISyndicationPiping {

	protected SyndicationControllerDescriptorContainer syndicationDescriptor;
	
	public SyndicationUtilityTileEntity(){
		syndicationDescriptor = new SyndicationControllerDescriptorContainer(this);
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
	public World getWorld() {
		return this.worldObj;
	}	
	
	@Override
	public boolean askControllerToDie(EntityPlayer player){
		return SyndicationPipingTileEntity.utility_askControllerToDie(player, this);
	}
	@Override
	public void setControllerOverloaded(){
		SyndicationPipingTileEntity.utility_setControllerOverloaded(this);
	}	
	
	
}
