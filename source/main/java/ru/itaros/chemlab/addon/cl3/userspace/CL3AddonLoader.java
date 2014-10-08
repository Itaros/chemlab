package ru.itaros.chemlab.addon.cl3.userspace;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/*
 * This class loads all addons and prepares loaders to be fired
 */
public class CL3AddonLoader {

	private AutoitemsLoader loaderAutoitems = new AutoitemsLoader();
	private SimpleRecipesLoader loaderDirproc = new SimpleRecipesLoader();
	
	public CL3AddonLoader(File cfgdir){
		File root = cfgdir.getAbsoluteFile().getParentFile();
		File addons = new File(root.getPath()+"\\chemlab_addons");
		if(!addons.exists()){
			addons.mkdir();
		}
		
		File[] candidates = addons.listFiles();
		parseCandidates(candidates);
	}

	private void parseCandidates(File[] candidates) {
		for(File f:candidates){
			if(f!=null){
				try {
					System.out.println("Attempting to load CL3Addon: "+f+"...");
					ZipFile zf = new ZipFile(f);
					readPackage(zf);
					zf.close();
					System.out.println("...Finished!");
				} catch (ZipException e) {
					throw new RuntimeException(e);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
	}

	ArrayList<ZipEntry> autoitems = new ArrayList<ZipEntry>();
	ArrayList<ZipEntry> dirproc = new ArrayList<ZipEntry>();
	
	/*
	 * Reads packages and fills loaders
	 */
	private void readPackage(ZipFile zf) throws IOException {
		//Polling entries
		Enumeration<? extends ZipEntry> zenum = zf.entries();
		
		while(zenum.hasMoreElements()){
			ZipEntry ze = zenum.nextElement();
			if(!ze.isDirectory()){
				if(ze.getName().startsWith("autoitems/")){
					autoitems.add(ze);
				}
				if(ze.getName().startsWith("dirproc/")){
					dirproc.add(ze);
				}
			}
		}
		
		//Reading data
		//AutoItems
		for(ZipEntry z:autoitems){
			System.out.println("Deploying Items: "+z.getName());
			String[] data = readZippedFile(zf,z);
			String groupname = consolidateName(Paths.get(zf.getName()).getFileName().toString())+"."+selectCapitalLetters(Paths.get(z.getName()).getFileName().toString());
			loaderAutoitems.parse(groupname,data);
		}
		//DirProc
		for(ZipEntry z:dirproc){
			System.out.println("Deploying Dirprocs: "+z.getName());
			String[] data = readZippedFile(zf,z);
			loaderDirproc.parse(data);
		}		
		
	}
	
	
	private static String consolidateName(String name){
		String sname="";
		for(int x = 0 ; x < name.length(); x++){
			char c = name.charAt(x);
			if(c=='.'){break;}//No need for extension
			if(c=='_'){continue;}
			sname+=c;
			
		}
		return sname.toLowerCase();
	}	
	private static String selectCapitalLetters(String name){
		String sname="";
		for(int x = 0 ; x < name.length(); x++){
			char c = name.charAt(x);
			if(c=='.'){break;}//No need for extension
			if(Character.isUpperCase(c)){
				sname+=c;
			}
		}
		return sname.toLowerCase();
	}
	
	private String[] readZippedFile(ZipFile f, ZipEntry e) throws IOException{
        ArrayList<String> s = new ArrayList<String>();
		BufferedReader br = new BufferedReader(
                new InputStreamReader(f.getInputStream(e)));
            String line;
            while ((line = br.readLine()) != null) {
             s.add(line);
            }
            br.close();
            
            String[] arr = new String[s.size()];
            arr = s.toArray(arr);
            return arr;
	}

	public AutoitemsLoader getItemLoader() {
		return loaderAutoitems;
	}
	public SimpleRecipesLoader getRecipesLoader(){
		return loaderDirproc;
	}
	
}
