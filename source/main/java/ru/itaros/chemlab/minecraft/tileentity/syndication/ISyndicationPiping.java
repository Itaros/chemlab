package ru.itaros.chemlab.minecraft.tileentity.syndication;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface ISyndicationPiping {

	public enum PipingMode{
		DISABLED,
		SEARCHING,
		ADHOCGATE,
		ACTIVE
	}	
	
	
	public PipingMode getMode();	
	public ISyndicationPiping setMode(PipingMode mode);
	
	public int getHeat();
	public ISyndicationPiping setHeat(int heat);
	
	public ISyndicationPiping setController(SyndicationHubTileEntity controller);
	public int getController_x();
	public int getController_y();
	public int getController_z();
	public SyndicationHubTileEntity getController(World world);
	
	//Visual Utility
	public ISyndicationPiping setBlockMetadata();
	public ISyndicationPiping setClear();
	public ISyndicationPiping setAdHoc();
	
	//TE Coords
	public int getX();
	public int getY();
	public int getZ();
	public World getWorld();
	
	//Controller operations
	public boolean askControllerToDie(EntityPlayer player);
	public void setControllerOverloaded();
	
}
