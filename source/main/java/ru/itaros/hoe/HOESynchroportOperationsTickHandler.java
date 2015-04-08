package ru.itaros.hoe;

import java.util.ArrayList;

import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.tiles.MachineTileEntity;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

public class HOESynchroportOperationsTickHandler {

	private int pointer;
	
	ArrayList<MachineTileEntity> known = new ArrayList<MachineTileEntity>();
	
	void init(){
		known.clear();
	}
	
	public void register(MachineTileEntity tile){
		if(known!=null){
			known.add(tile);
			System.out.println("SOP registered: "+tile.getClass().getSimpleName());
		}
	}
	
	public void unregister(MachineTileEntity tile){
		if(known!=null){
			known.remove(tile);
			System.out.println("SOP unregistered: "+tile.getClass().getSimpleName());
		}
	}
	
	@SubscribeEvent
	public void onTick(TickEvent.ServerTickEvent event){
		if(known!=null){
			process();
		}
	}

	private void process() {
		long timeTaken = 0;
		long maxPullTime = HOE.getInstance().getConfig().interop_pushpulltime;
		boolean homogeneousHOESOPTime = HOE.getInstance().getConfig().interop_hoeshomo;
		
		boolean startEncounter=false;
		
		long mstart = System.currentTimeMillis();
		while(timeTaken < maxPullTime){
			if(!homogeneousHOESOPTime){
				if(startEncounter){
					if(pointer == 0){break;}
				}else{
					if(pointer == 0){startEncounter=true;}
				}
			}
			
			movePointer();
			MachineTileEntity s = select();
			if(s!=null){
				if(!isValid(s)){unregister(s);pointer--;return;}//Fallback
				s.onSynchroOperationsUpdate();
			}
			long mend = System.currentTimeMillis();
			long mdif = mend - mstart;
			//if(mdif == 0){mdif=1;}
			timeTaken = mdif;
		}
	}

	private boolean isValid(MachineTileEntity s) {
		HOEMachineData data = s.getServerData();
		boolean blockUpdatesStatus = data==null?true:data.isPerformingBlockUpdates();
		return s.isHOEAssumesLoaded() && blockUpdatesStatus;
	}

	private MachineTileEntity select() {
		if(known.size()==0){
			return null;
		}
		return known.get(pointer);
	}

	private void movePointer() {
		pointer++;
		if(pointer>=known.size()){
			pointer=0;
		}
	}
	

	
}
