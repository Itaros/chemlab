package ru.itaros.chemlab.addon.cl3.userspace;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import ru.itaros.hoe.HOE;
import ru.itaros.hoe.client.textures.ExternalTextureAtlasSprite;

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
	ArrayList<ZipEntry> tex = new ArrayList<ZipEntry>();
	
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
				if(ze.getName().startsWith("assets/itex/")){
					tex.add(ze);
				}
			}
		}
		
		//Reading data
		//TextureStitchers
		for(ZipEntry z:tex){
			System.out.println("Deploying Texture: "+z.getName());
			uploadTexture(zf,z);
		}		
		//AutoItems
		for(ZipEntry z:autoitems){
			System.out.println("Deploying Items: "+z.getName());
			String[] data = readZippedFile(zf,z);
			String groupname = consolidateName(Paths.get(zf.getName()).getFileName().toString())+"."+selectCapitalLetters(Paths.get(z.getName()).getFileName().toString());
			loaderAutoitems.parse(groupname,data, this);
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

	private void uploadTexture(ZipFile f, ZipEntry e) throws IOException{
		//Getting memCache
		InputStream str = f.getInputStream(e);
		byte[] blob = new byte[(int) e.getSize()];
		str.read(blob);
		//Constructing sprite loading instructions
		ExternalTextureAtlasSprite etas = new ExternalTextureAtlasSprite(e.getName().replace("assets/itex/", "chemlab:").replace(".png", ""),blob);
		//Registering
		textureHash.put(etas.getIconName(), etas);
	}
	
	private Hashtable<String,ExternalTextureAtlasSprite> textureHash = new Hashtable<String,ExternalTextureAtlasSprite>();
	
	public AutoitemsLoader getItemLoader() {
		return loaderAutoitems;
	}
	public SimpleRecipesLoader getRecipesLoader(){
		return loaderDirproc;
	}
	
	public ExternalTextureAtlasSprite getTexture(String iconName){
		return textureHash.get(iconName);
	}
	
}
