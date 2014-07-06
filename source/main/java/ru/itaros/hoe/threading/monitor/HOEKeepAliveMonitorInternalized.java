package ru.itaros.hoe.threading.monitor;

import java.util.ArrayList;
import java.util.Hashtable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import scala.Console;

public class HOEKeepAliveMonitorInternalized {

	Hashtable<World,Hashtable<ChunkCoordIntPair,ArrayList<WrappedTEDataPort>>> association = new Hashtable<World,Hashtable<ChunkCoordIntPair,ArrayList<WrappedTEDataPort>>>();
	//Hashtable<ChunkCoordIntPair,WrappedTEDataPort> association = new Hashtable<ChunkCoordIntPair,WrappedTEDataPort>();
	
	public HOEKeepAliveMonitorInternalized(){
		
	}
	
	public void serverStart(){
		//Does nothing. Reserved for Externalized implementation
	}
	
	public void onUnloadCatch(MachineTileEntity tile){
		System.out.println("HOEKA: Storing...");
		WrappedTEDataPort dataport = new WrappedTEDataPort(tile);
		
		Hashtable<ChunkCoordIntPair,ArrayList<WrappedTEDataPort>> inner = acquireInnerAssociation(tile.getWorldObj());
		ArrayList<WrappedTEDataPort> portlist = getDataPortArray(inner,dataport.getChcoords());
		
		
		if(!dataport.isInvalid()){
			for(WrappedTEDataPort port : portlist){
				if(port.isTE(tile)){
					//Already exists, giving up
					return;
				}
			}
			portlist.add(dataport);
		}
	}
	
	private ArrayList<WrappedTEDataPort> getDataPortArray(Hashtable<ChunkCoordIntPair,ArrayList<WrappedTEDataPort>> inner, ChunkCoordIntPair pair){	
		
		ArrayList<WrappedTEDataPort> wrapped = inner.get(pair);
		if(wrapped==null){
			wrapped = new ArrayList<WrappedTEDataPort>();
			inner.put(pair, wrapped);
		}
		return wrapped;
	}
	
	private Hashtable<ChunkCoordIntPair, ArrayList<WrappedTEDataPort>> acquireInnerAssociation(
			World worldObj) {
		Hashtable<ChunkCoordIntPair, ArrayList<WrappedTEDataPort>> inner = association.get(worldObj);
		if(inner==null){
			inner = new Hashtable<ChunkCoordIntPair, ArrayList<WrappedTEDataPort>>();
			association.put(worldObj, inner);
		}
		return inner;
	}

	public void onLoadTryInject(MachineTileEntity tile){
		System.out.println("HOEKA: Injecting...");
		//Acquiring association...
		World world = tile.getWorldObj();
		Chunk chk = world.getChunkFromBlockCoords(tile.xCoord, tile.yCoord);
		ChunkCoordIntPair pair = chk.getChunkCoordIntPair();
		
		Hashtable<ChunkCoordIntPair,ArrayList<WrappedTEDataPort>> inner = association.get(world);
		if(inner==null){return;}
		ArrayList<WrappedTEDataPort> portlist = inner.get(pair);
		if(portlist==null){return;}
		
		for(WrappedTEDataPort port : portlist){
			if(port.isTE(tile)){
				tile.injectData(port.getData());
			}
		}
		
		
		
	}
	
}
