package ru.itaros.hoe;

import java.util.LinkedList;

import net.minecraftforge.event.world.WorldEvent;
import ru.itaros.hoe.tiles.MachineTileEntity;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class HOETilePostLoadTickHandler {

	LinkedList<MachineTileEntity> candidates;
	
	public void init(){
		candidates = new LinkedList<MachineTileEntity>();
	}
	
	public void pushTile(MachineTileEntity candidate){
		if(candidates!=null){
			candidates.add(candidate);
		}
	}
	
	//public void onStart(PlayerEvent.PlayerLoggedInEvent event){
	//	if(event.world.)
	//	init();
	//}
	
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event){
		if(candidates!=null){
			process();
		}
	}
	
	private void process(){
		MachineTileEntity te = candidates.poll();
		if(te==null){return;}
		te.onPostLoad();		
	}
	
	
	
	
}
