package ru.itaros.hoe.threading;

import java.util.Vector;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.IHOEThread;

public class HOEThread extends Thread implements IHOEThread{

	public static final long _TIMESTEP = 50L;
	private boolean active = true;
	private Vector<IHOEJob> programs=new Vector<IHOEJob>();
	
	public HOEThread(){
		super();
		
		timing();//Preheat
		
		
		this.start();
	}
	
	public void shutdown() {
		active=false;		
	}

	@Override
	public void injectJob(IHOEJob r) {
		programs.add(r);
		
	}

	@Override
	public void run() {
		while(active){
			timing();
			execute();
		}
	}

	
	private void execute() {
		if(diff_time>_TIMESTEP){
			IHOEJob toTerminate=null;
			for(IHOEJob r:programs){
				try{
					r.run();
				}catch(Exception e){
					//TODO: Send exception to console
					//TODO: Send message to server chat
					toTerminate=r;
				}
			}
			if(toTerminate!=null){
				//Kills bad program, because it can't catch its own exceptions
				programs.remove(toTerminate);
			}
		}
	}


	long last_time,current_time;
	long diff_time;
	private void timing() {
		current_time = System.currentTimeMillis();
		
		diff_time=current_time-last_time;
		
		if(diff_time>_TIMESTEP){
			last_time=current_time;
		}
	}
	
	

}
