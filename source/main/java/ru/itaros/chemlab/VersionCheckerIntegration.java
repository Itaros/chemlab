package ru.itaros.chemlab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInterModComms;

public class VersionCheckerIntegration {
	
	private  FetchVersion versionFetcher;
	private Thread thr;
	
	private boolean isOutdated=false;
	
	
	private boolean passAway=false;
	
	public VersionCheckerIntegration startPolling() {
		if(Loader.isModLoaded("VersionChecker")){
			versionFetcher = new FetchVersion();
			thr = new Thread(versionFetcher,"CL-VCI");
			thr.start();
		}else{
			passAway=true;
		}
		
		
		return this;
	}	
	

	private void block() {
		
		thr.interrupt();
		try {
			thr.join();
		} catch (InterruptedException e) {
			//Main Thread can't be interrupted in normal circumstances
			e.printStackTrace();
		}
		
	}	
	
	public void makeFinal(){
		if(passAway){return;}
		
		block();
		
		if(passAway){return;}
		
		isOutdated = ! ChemLab.getPublicVersionNotation().equals(versionFetcher.getVersionid());
		
		if(isOutdated){
			System.out.println("ChemLab is outdated!");
			
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setString("oldVersion",ChemLab.getPublicVersionNotation());
			nbt.setString("newVersion",versionFetcher.getVersionid());
			nbt.setString("updateUrl","http://chemlab.sg-studio.ru/platform/latestArtifact.jar");
			nbt.setBoolean("isDirectLink", true);
			nbt.setString("changeLog",versionFetcher.getChangelog());
			FMLInterModComms.sendRuntimeMessage(ChemLab.MODID, "VersionChecker", "addUpdate", nbt);
		}else{
			System.out.println("ChemLab is uptodate!");
		}
	}



	private class FetchVersion implements Runnable{

		private String addr = "http://chemlab.sg-studio.ru/platform/latest-"+ChemLab.getMCVersionNotation()+".clpt";
		
		private String versionid;
		public String getVersionid() {
			return versionid;
		}

		public String getChangelog() {
			return changelog;
		}

		private String changelog="";
		
		@Override
		public void run() {
			int timeout = 1500;
            URL url;
			try {
				url = new URL(addr);
	            URLConnection con = url.openConnection();
	            con.setConnectTimeout(timeout);
	            con.setReadTimeout(timeout);
	            InputStream io = con.getInputStream();
	            BufferedReader br = new BufferedReader(new InputStreamReader(io));
	           
	            String str;
	            boolean chlmode=false;
	            while ((str = br.readLine()) != null) {
	            	if(chlmode){
	            		changelog+=str+"\n";
	            	}else{
		            	if(str.equals("NEWVERSION:")){
		            		versionid=br.readLine();
		            		continue;
		            	}
		            	if(str.equals("CHANGELOG:")){
		            		chlmode=true;
		            		continue;
		            	}
	            	}
	            }
	            
	            
	            
	            
			} catch (IOException e) {
				passAway=true;
				e.printStackTrace();
			}			
            
			
            
		}
		
	}

	
	
}
