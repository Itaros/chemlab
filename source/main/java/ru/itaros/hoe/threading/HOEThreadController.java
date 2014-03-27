package ru.itaros.hoe.threading;

import ru.itaros.api.hoe.IHOEThread;

public class HOEThreadController {

	private int nextDomain=0;
	private int threadsAmount;
	private HOEThread[] threads;
	
	public HOEThreadController(int hoeThreadsNumber) {
		threadsAmount=hoeThreadsNumber;
		System.out.println("Initializing HOE:");
		threads = new HOEThread[threadsAmount];
		nextDomain=0;
		System.out.println("->");
		spawnThreads();
		System.out.println("<-");
	}

	public void shutdown() {
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
	}
	
	
	
	
	private void spawnThreads(){
		for(int x = 0 ; x < threadsAmount; x++){
			System.out.println("	-Instantiating thread #"+x);
			HOEThread thr = new HOEThread();
			threads[x]=thr;
		}
	}

	public IHOEThread getNextDomain() {
		IHOEThread thr = threads[nextDomain];
		nextDomain++;
		if(nextDomain>=threadsAmount){nextDomain=0;}
		return thr;
	}

}
