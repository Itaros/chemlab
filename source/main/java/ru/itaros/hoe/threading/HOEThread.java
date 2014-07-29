package ru.itaros.hoe.threading;

import java.util.Vector;
import java.util.concurrent.locks.ReentrantLock;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.IHOEThread;
import ru.itaros.hoe.Config.ThreadRetentionPolicy;
import ru.itaros.hoe.HOE;
import scala.Console;

public class HOEThread extends Thread implements IHOEThread{

	public static final long _TIMESTEP = 1000L/20L;//50L
	private boolean active = true;
	private Vector<IHOEJob> programs=new Vector<IHOEJob>();
	
	private HOEThreadController controller;
	
	public HOEThread(HOEThreadController hoeThreadController){
		super();
		
		this.controller=hoeThreadController;
		
		this.setName("HOE Thread");
		
		
		timing();//Preheat
		
		
		this.start();
	}
	
	public void shutdown() {
		active=false;
		controller=null;//Break link to allow GC
	}

	
	private ReentrantLock jobsaccesslock=new ReentrantLock();
	@Override
	public void injectJob(IHOEJob r) {
		try{
			jobsaccesslock.lock();
			programs.add(r);
		}finally{
			jobsaccesslock.unlock();
		}
	}

	@Override
	public void run() {
		while(active){
			try{retention();}catch(InterruptedException e){
				active=false;
				System.out.println("HOE Thread is interrupted!");
			}
			timing();
			execute();
		}
	}

	private void execute() {
		if(diff_time>_TIMESTEP){
			isAwaitingCycleRetention=true;
			long before,after;
			before = System.currentTimeMillis();
			IHOEJob toTerminate=null;
			if(jobsaccesslock.tryLock()){
				for(IHOEJob r:programs){
					try{
						r.run();
					}catch(Exception e){
						//TODO: Send exception to console
						//TODO: Send message to server chat
						System.err.println("========ERROR REPORT========");
						System.err.println("Faulty HOEJob detected:\n"+r.getClass().getSimpleName());
						e.printStackTrace();
						System.err.println("Possible cause:"+r.getCurrentlyProccessedData().getClass().getName());
						if(r.getCurrentlyProccessedData()!=null && r.getCurrentlyProccessedData().getIO()!=null){
							System.err.println("IO:"+r.getCurrentlyProccessedData().getIO().getClass().getName());
						}
						System.err.println("=======END OF REPORT========");
						toTerminate=r;
					}
				}
				if(toTerminate!=null){
					//Kills bad program, because it can't catch its own exceptions
					programs.remove(toTerminate);
					if(controller!=null){
						controller.notifyErrored(toTerminate);
					}
				}
				jobsaccesslock.unlock();
			}else{
				System.out.println("HOETH is skipping");
			}
			after = System.currentTimeMillis();
			calcRetrospectiveTimeOffset(before,after);
		}
	}


	private void calcRetrospectiveTimeOffset(long before, long after) {
		retrospectiveStepTime = _TIMESTEP-(after-before);
	}


	long last_time,current_time;
	long diff_time;
	long retrospectiveStepTime;//threading_retentionPolicy
	boolean isAwaitingCycleRetention=false;
	private void timing() {
		current_time = System.currentTimeMillis();
		
		diff_time=current_time-last_time;
		
		if(diff_time>_TIMESTEP){
			last_time=current_time;
		}
	}

	private ThreadRetentionPolicy cachedRetentionPolicy = HOE.getInstance().getConfig().threading_retentionPolicy;
	
	private void retention() throws InterruptedException {
		if(cachedRetentionPolicy==ThreadRetentionPolicy.RETROPREDICTIVE){
			if(retrospectiveStepTime>0L){Thread.sleep(retrospectiveStepTime);retrospectiveStepTime=0L;}
		}else if(cachedRetentionPolicy==ThreadRetentionPolicy.STEPPING){
			Thread.sleep(10);
		}else if(cachedRetentionPolicy==ThreadRetentionPolicy.PERCYCLE){
			if(isAwaitingCycleRetention){
				isAwaitingCycleRetention=false;
				Thread.sleep(0);
			}
		}
		
	}
	

}
