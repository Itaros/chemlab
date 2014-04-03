package ru.itaros.hoe;


import org.apache.logging.log4j.Level;

import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.hoe.interfacer.HOEInterfacer;
import ru.itaros.hoe.proxy.HOEProxy;
import ru.itaros.hoe.proxy.HOEServer;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.hoe.registries.HOEIORegistry;
import ru.itaros.hoe.registries.HOERecipeRegistry;
import ru.itaros.hoe.threading.HOEThreadController;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;

@Mod(modid = HOE.MODID, version = HOE.VERSION, useMetadata = true)
public class HOE {
    public static final String MODID = "hoelib";
    public static final String VERSION = "1.0";
    
    @SidedProxy(clientSide="ru.itaros.hoe.proxy.HOEClient", serverSide="ru.itaros.hoe.proxy.HOEServer")
    public static HOEProxy proxy;
    
    //@EventHandler
    //public void intercomm(IMCEvent event){
    //	for(IMCMessage m:event.getMessages()){
    //		//TODO: Messages
    //	}
    //}
    
    private HOEFluidRegistry hoefluidregistry;
    private HOEIORegistry ioregistry;
    private HOERecipeRegistry reciperegistrry;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	hoefluidregistry=new HOEFluidRegistry();
    	ioregistry =new HOEIORegistry();
    	reciperegistrry=new HOERecipeRegistry();
    	interfacer=new HOEInterfacer();
    }    
    
    @EventHandler
    public void serverInit(FMLServerAboutToStartEvent event)
    {
    	//Hack to load correct proxy	
    	try {
			Class<?> integrated = (Class<?>) Class.forName("IntegratedServer");
			if(integrated.isInstance(event.getServer())){
	    		proxy = new HOEServer();
	    		FMLLog.log(Level.INFO, "HOE PROXY HACK ELEVATED!");
	    	}
		} catch (ClassNotFoundException e) {
			//NOP. Fail Silently. This class doesn't exist on server
		}    	
    	
		proxy.initHOE();
    } 
    @EventHandler
    public void serverShutdown(FMLServerStoppingEvent event)
    {
		proxy.shutdownHOE();
    }
	public static void setController(HOEThreadController hoeThreadController) {
		HOETC = hoeThreadController;
	}     
    
	
	private static HOEThreadController HOETC;
	public static HOEThreadController HOETC(){
		return HOETC;
	}
	
	
	private static IHOEInterfacer interfacer;
	public static IHOEInterfacer getInterfacer(){
		return interfacer;
	}
	
}
