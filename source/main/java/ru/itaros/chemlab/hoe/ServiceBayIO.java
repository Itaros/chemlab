package ru.itaros.chemlab.hoe;

import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.ServiceBayData;
import ru.itaros.chemlab.hoe.messages.ReplacePartMessage;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.connectivity.HOEBiPolarSocket;

public class ServiceBayIO extends HOEMachineIO {

	public ServiceBayIO(){
		super();
		this.allowToStart();
	}
	
	@Override
	public void configureData(HOEData data) {
		HOEMachineData machinedata = (HOEMachineData)data;
		
		machinedata.setMaxPower(1000);
		machinedata.ticksRequared=20;
		machinedata.setMachine(this);
		machinedata.setConfigured();
	}

	protected ReplacePartMessage currentTask = null;
	private int time=0;
	private static final int TIMEOUT=5;//Secs
	
	@Override
	protected void produce(HOEData data, boolean doReal) {
		
		//NO CLIENTS HERE
		if(ContextDetector.getInstance().getContext()==FMLContext.CLIENT){return;}
		
		if(currentTask==null){
			System.out.println("WOW, Task issued");
			HOEBiPolarSocket socket = data.getIntercomSocket();
			currentTask=new ReplacePartMessage();
			socket.scheduleBroadcast(currentTask);
			time=0;
		}else{
			if(currentTask.isAnswered()){
				currentTask=null;//Eject it for now
				System.out.println("WOW, Task Completed");
			}else{
				//Timeout handler
				time++;
				if(time>TIMEOUT){
					time=0;
					currentTask=null;
					System.out.println("WOW, Task Timeouted");
				}
			}
		}
	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		if(data instanceof ServiceBayData){
			ServiceBayData sbd = (ServiceBayData)data;
			//return sbd.hasSupplies();
			return true;
		}else{
			return false;
		}
	}





	

	
	
}
