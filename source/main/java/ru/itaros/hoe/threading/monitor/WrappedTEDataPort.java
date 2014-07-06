package ru.itaros.hoe.threading.monitor;

import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class WrappedTEDataPort {

	private boolean invalid=false;
	public boolean isInvalid(){
		return invalid;
	}
	
	private World world;
	private int x,y,z;
	private ChunkCoordIntPair chcoords;
	private HOEData data;
	
	public WrappedTEDataPort(MachineTileEntity tile){
		this(tile,tile.getServerData());
	}
	
	public WrappedTEDataPort(MachineTileEntity tile, HOEData data){
		this.world=tile.getWorldObj();
		this.x=tile.xCoord;
		this.y=tile.yCoord;
		this.z=tile.zCoord;
		
		this.data=data;
		if(world!=null){
			Chunk chunk = world.getChunkFromBlockCoords(x, z);
			chcoords = chunk.getChunkCoordIntPair();
		}else{
			invalid=true;
		}
	}

	public World getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public ChunkCoordIntPair getChcoords() {
		return chcoords;
	}

	public HOEData getData() {
		return data;
	}

	public boolean isTE(MachineTileEntity tile) {
		boolean isX = (tile.xCoord==x);
		boolean isY = (tile.yCoord==y);
		boolean isZ = (tile.zCoord==z);
		boolean isWorld = (tile.getWorldObj()==world);
		
		return isX & isY & isZ & isWorld;
	}
	
	
	
}
