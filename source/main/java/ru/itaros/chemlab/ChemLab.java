package ru.itaros.chemlab;

import org.apache.logging.log4j.Level;

import ru.itaros.chemlab.client.ui.GUIHandler;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.recipes.RecipesLoader;
import ru.itaros.chemlab.loader.tileentity.TileEntityLoader;
import ru.itaros.chemlab.proxy.Proxy;
import ru.itaros.chemlab.proxy.Server;
import ru.itaros.toolkit.hoe.io.IOCollectionHelper;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ChemLab.MODID, version = ChemLab.VERSION, dependencies="required-after:hoelib")
public class ChemLab
{
	public ChemLab(){
		super();
		instance=this;
	}
	
    public static final String MODID = "chemlab";
    public static final String VERSION = "1.0";
    
    
    @SidedProxy(clientSide="ru.itaros.chemlab.proxy.Client", serverSide="ru.itaros.chemlab.proxy.Server")
    public static Proxy proxy;
    
    private static IOCollectionHelper iocollection;
    public static IOCollectionHelper getIOCollection(){
    	return iocollection;
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	new ChemLabCreativeTab();//There is forgebug somewhere close
    	
 		BlockLoader.loadBlocks();
		iocollection = TileEntityLoader.load();   	
    	
    }     
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {

    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		RecipesLoader.load();
		
		new GUIHandler();
    }   
    
 
    
    
    
    @EventHandler
    public void serverInit(FMLServerAboutToStartEvent event)
    {    
    	//Hack to load correct proxy	
    	try {
			Class<?> integrated = (Class<?>) Class.forName("IntegratedServer");
			if(integrated.isInstance(event.getServer())){
	    		proxy = new Server();
	    		FMLLog.log(Level.INFO, "HOE[ChemLab] PROXY HACK ELEVATED!");
	    	}
		} catch (ClassNotFoundException e) {
			//NOP. Fail Silently. This class doesn't exist on server
		}     	
    	
    	
		proxy.initLinker();   	
    }
    @EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {

    }

    private static ChemLab instance;
	public static ChemLab getInstance() {
		return instance;
	}

    
    
}
