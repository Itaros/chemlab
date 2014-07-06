package ru.itaros.chemlab.network.packets;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import io.netty.buffer.ByteBuf;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationItemPortData;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationHubTileEntity;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationItemPortTileEntity;
import ru.itaros.chemlab.network.IPacketCodecDescriptor;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class ChemLabSyndicationHubCommand implements IPacketCodecDescriptor {

	public enum SyndicationCommandMode{
		ENGAGE_NETWORK_INSPECTION,DISENGAGE_NETWORK,
		GRAB_FILTERSETTINGS, GRAB_INBOUNDMODE
	}
	
	
	protected boolean isCorrect=true;
	
	private SyndicationCommandMode mode;
	private MachineTileEntity tile;
	
	public ChemLabSyndicationHubCommand(){}//DECODER
	public ChemLabSyndicationHubCommand(MachineTileEntity tile2, SyndicationCommandMode mode){
		this.tile=tile2;
		this.mode=mode;
	}
	
	public void execute(){
		SyndicationItemPortTileEntity sipte;
		switch(mode){	
		case ENGAGE_NETWORK_INSPECTION:
			if(isCorrect){
				
				((SyndicationHubTileEntity)tile).engageSyndicationInspection();
				
			}
			break;
		case DISENGAGE_NETWORK:
			if(isCorrect){
				
				((SyndicationHubTileEntity)tile).disengageSyndication();
				
			}
			break;			
		case GRAB_FILTERSETTINGS:
			sipte = (SyndicationItemPortTileEntity)tile;
			sipte.pushFilter();
			
			break;
		case GRAB_INBOUNDMODE:
			sipte = (SyndicationItemPortTileEntity)tile;
			((SyndicationItemPortData)sipte.getServerData()).cycleSOM();
			break;
			

			
		}
	}
	
	
	@Override
	public void readBytes(ByteBuf bytes) {

		int wid = bytes.readInt();
		
		int x = bytes.readInt();
		int y = bytes.readInt();
		int z = bytes.readInt();
		
		int mid = bytes.readInt();
		
		compose(wid,x,y,z,mid);

	}

	private void compose(int wid, int x, int y, int z, int mid) {
		if(!DimensionManager.isDimensionRegistered(wid)){invalidate();return;}
		World w = DimensionManager.getWorld(wid);
		
		TileEntity tile = w.getTileEntity(x, y, z);
		if(tile instanceof MachineTileEntity){
			this.tile = (MachineTileEntity)tile;
		}else{
			invalidate();return;
		}
		
		if(mid>=mode.values().length){invalidate();return;}
		this.mode = mode.values()[mid];
		
	}
	private void invalidate() {
		isCorrect=false;
	}
	@Override
	public void writeBytes(ByteBuf bytes) {

		bytes.writeInt(tile.getWorldObj().provider.dimensionId);
		
		bytes.writeInt(tile.xCoord);
		bytes.writeInt(tile.yCoord);
		bytes.writeInt(tile.zCoord);
		
		bytes.writeInt(mode.ordinal());

	}

	public static int getInternalCodecID() {
		return 1;
	}	
	
}
