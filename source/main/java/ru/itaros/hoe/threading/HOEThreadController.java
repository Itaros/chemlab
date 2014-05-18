package ru.itaros.hoe.threading;

import java.util.ArrayList;

import ru.itaros.api.hoe.IHOEJob;
import ru.itaros.api.hoe.IHOEThread;

public class HOEThreadController {

	public enum HOETCState{
		CLEAR,
		STARTED,
		STOPPED,
		STARTED_HASERRORS,
		SWITCHING
	}
	private HOETCState state = HOETCState.CLEAR;
	
	private int nextDomain=0;
	private int threadsAmount;
	private HOEThread[] threads;
	
	public HOEThreadController(int hoeThreadsNumber) {
		startup(hoeThreadsNumber);
	}

	public HOETCState getState(){
		return state;
	}
	
	public boolean startup() {
		return startup(threadsAmount);
	}
	public boolean startup(int hoeThreadsNumber){
		if(state == HOETCState.CLEAR | state == HOETCState.STOPPED){
			state = HOETCState.SWITCHING;
			threadsAmount=hoeThreadsNumber;
			System.out.println("Initializing HOE:");
			threads = new HOEThread[threadsAmount];
			nextDomain=0;
			System.out.println("->");
			spawnThreads();
			System.out.println("<-");
			state = HOETCState.STARTED;
			return true;
		}else{
			return false;
		}
	}

	public boolean shutdown() {
		if(state == HOETCState.STARTED | state == HOETCState.STARTED_HASERRORS){
			state = HOETCState.SWITCHING;
			System.out.println("Shutting HOE down:");
			System.out.println("->");		
			for(int x = 0 ; x < threadsAmount; x++){
				System.out.println("	-Sending shutdown signal to thread #"+x);
				HOEThread thr = threads[x];
				thr.shutdown();
				try {
					thr.join();
				} catch (InterruptedException e) {
					//NOP. Interrupted, let it go
					System.out.println("		-Done(something wrong)!");
				}
				System.out.println("		-Done!");
			}
			System.out.println("<-");
			state = HOETCState.STOPPED;
			return true;
		}else{
			return false;
		}
	}
	
	
	
	public synchronized boolean recover(){
		if(state==HOETCState.STARTED_HASERRORS){
			IHOEThread thread = getNextDomain();
			for(IHOEJob job : errored_list){
				thread.injectJob(job);
			}
			errored_list.clear();
		}
		return false;
	}
	
	private void spawnThreads(){
		for(int x = 0 ; x < threadsAmount; x++){
			System.out.println("	-Instantiating thread #"+x);
			HOEThread thr = new HOEThread(this);
			threads[x]=thr;
		}
	}

	public IHOEThread getNextDomain() {
		IHOEThread thr = threads[nextDomain];
		nextDomain++;
		if(nextDomain>=threadsAmount){nextDomain=0;}
		return thr;
	}

	ArrayList<IHOEJob> errored_list = new ArrayList<IHOEJob>();
	
	public synchronized void notifyErrored(IHOEJob terminated) {
		errored_list.add(terminated);
		if(state==HOETCState.STARTED){
			state = HOETCState.STARTED_HASERRORS;
		}
	}



}
