package ru.itaros.chemlab;

import ru.itaros.api.hoe.HOEAbstractLinker;
import ru.itaros.hoe.jobs.HOEMachines;

public class HOELinker extends HOEAbstractLinker{

	private HOEMachines machinejobs;
	
	public HOELinker(){
		init();
		attach();
	}
	

	public HOEMachines getMyMachinesHandler(){
		return machinejobs;
	}
	
	private void attach(){
		machinejobs = new HOEMachines();
		lowjob=machinejobs;
		myapartment.injectJob(machinejobs);
	}
}
