package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services;

import java.util.ArrayList;

import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.connectivity.HOEBiPolarSocket;
import ru.itaros.toolkit.hoe.machines.basic.io.connectivity.HOEPeerToPeerConnector;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/*
 * This class provides services for HOETileEntities to connect to adjacent blocks
 */
public abstract class TileEntityConnectome {

	/*
	 * Call this method on neighbor change
	 */
	public void reconfigureHOEBiPolar(MachineTileEntity me){
		//TODO: Somewhat ineffective. I should not drop all connectomes. Only the changed(lost\found) ones.
		HOEMachineData data = me.getServerData();
		HOEBiPolarSocket socket = data.getIntercomSocket();
		
		MachineTileEntity[] adjacent = LookForAdjacentTECandidates(me);
		HOEPeerToPeerConnector[] HOEP2P = new HOEPeerToPeerConnector[adjacent.length];
		int i=-1;
		for(MachineTileEntity target:adjacent){
			i++;
			HOEPeerToPeerConnector hptp = new HOEPeerToPeerConnector(socket, target.getServerData().getIntercomSocket());
			HOEP2P[i]=hptp;
		}
		
		socket.dropOutbound();
		socket.insertOutbound(HOEP2P);
		
	}
	
	
	
	private static final int INDICES=6;
	
	ArrayList<MachineTileEntity> conserv = new ArrayList<MachineTileEntity>(INDICES);
	
	protected MachineTileEntity[] LookForAdjacentTECandidates(MachineTileEntity tile){
		World w = tile.getWorldObj();
		int x = tile.xCoord;
		int y = tile.yCoord;
		int z = tile.zCoord;
		
		if(y==0 | y==255){return new MachineTileEntity[0];}
		
		TileEntity inspectable;
		
		inspectable=w.getTileEntity(x+1, y, z);
		if(isValidTile(inspectable)){
			conserv.add((MachineTileEntity) inspectable);
		}
		inspectable=w.getTileEntity(x-1, y, z);
		if(isValidTile(inspectable)){
			conserv.add((MachineTileEntity) inspectable);
		}		
		inspectable=w.getTileEntity(x, y+1, z);
		if(isValidTile(inspectable)){
			conserv.add((MachineTileEntity) inspectable);
		}	
		inspectable=w.getTileEntity(x, y-1, z);
		if(isValidTile(inspectable)){
			conserv.add((MachineTileEntity) inspectable);
		}			
		inspectable=w.getTileEntity(x, y, z+1);
		if(isValidTile(inspectable)){
			conserv.add((MachineTileEntity) inspectable);
		}
		inspectable=w.getTileEntity(x, y, z-1);
		if(isValidTile(inspectable)){
			conserv.add((MachineTileEntity) inspectable);
		}	
		
		MachineTileEntity[] rslt = new MachineTileEntity[conserv.size()];
		rslt=conserv.toArray(rslt);
		
		conserv.clear();
		
		return rslt;
		
		
	}

	
	protected abstract boolean isValidTile(TileEntity inspectable);
	
	
}
