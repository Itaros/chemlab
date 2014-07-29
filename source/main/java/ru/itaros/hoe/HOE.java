package ru.itaros.hoe;


import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.hoe.interfacer.HOEInterfacer;
import ru.itaros.hoe.proxy.HOEProxy;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.hoe.registries.HOEIORegistry;
import ru.itaros.hoe.registries.HOERecipeRegistry;
import ru.itaros.hoe.signatures.HOEExecutor;
import ru.itaros.hoe.threading.HOEThreadController;
import ru.itaros.hoe.threading.monitor.HOEKeepAliveMonitorInternalized;
import ru.itaros.hoe.toolkit.worldgen.HOEWorldGenerator;
import ru.itaros.hoe.vanilla.registries.WorldGenRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;

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
    
    private static HOE instance;
    
    public HOE(){
    	instance=this;
    }
    
    public static HOE getInstance(){
    	return instance;
    }
    
    public WorldGenRegistry getRegistryWorldGen(){
    	return worldgenregistry;
    }
    
    public HOEExecutor getHOEExecutor(){
    	return hoeexec;
    }
    
    public HOEKeepAliveMonitorInternalized getKeepAlive(){
    	return keepalive;
    }
    
    public Config getConfig(){
    	return config;
    }
    
    //============MTA============
    private HOEFluidRegistry hoefluidregistry;
    private HOEIORegistry ioregistry;
    private HOERecipeRegistry reciperegistrry;
    
    private ContextDetector contextdetector;
    private HOEKeepAliveMonitorInternalized keepalive;
    //===========================
    //============AHT============
    private HOEExecutor hoeexec;
    //===========================
    //============VMC============
    private WorldGenRegistry worldgenregistry;
    private Config config;
    //===========================
    
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	config = new Config().loadConfig(event);
    	
    	if(config.threading_keepalive){
    		keepalive = new HOEKeepAliveMonitorInternalized();
    	}
    	
    	hoeexec = new HOEExecutor();
    	
    	contextdetector = new ContextDetector();
    	
    	ForgeFixer.forgeOreDictFix();
    	
    	worldgenregistry=new WorldGenRegistry();
    	
    	hoefluidregistry=new HOEFluidRegistry();
    	ioregistry =new HOEIORegistry();
    	reciperegistrry=new HOERecipeRegistry();
    	interfacer=new HOEInterfacer();
    	
    }    
    
    HOETilePostLoadTickHandler postload_tickhandler;
    public HOETilePostLoadTickHandler getTEPostLoadManager(){
    	return postload_tickhandler;
    }
    HOESynchroportOperationsTickHandler synchroop_tickhandler;
    public HOESynchroportOperationsTickHandler getTESynchroOpManager(){
    	return  synchroop_tickhandler;
    }
    
    private HOEWorldGenerator worldgen;
    public HOEWorldGenerator getHOEWorldGenerator(){
    	return worldgen;
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {   
    	postload_tickhandler = new HOETilePostLoadTickHandler();
    	FMLCommonHandler.instance().bus().register(postload_tickhandler);
    	
    	synchroop_tickhandler = new HOESynchroportOperationsTickHandler();
    	FMLCommonHandler.instance().bus().register(synchroop_tickhandler);
    	
    	worldgen = new HOEWorldGenerator();
    	GameRegistry.registerWorldGenerator(worldgen, 100);
    }
    
    
    @EventHandler
    public void serverInit(FMLServerAboutToStartEvent event)
    {
    	postload_tickhandler.init();
    	synchroop_tickhandler.init();
    	
    	contextdetector.requestContextData(event);
    	//Hack to load correct proxy	
    	//if(contextdetector.getContext()==FMLContext.DEDICATED || contextdetector.getContext()==FMLContext.INTEGRATED){
	    //	proxy = new HOEServer();
	    //	FMLLog.log(Level.INFO, "HOE PROXY HACK ELEVATED!");
    	//}
    	
		proxy.initHOE();
		
		
    } 
    @EventHandler
    public void serverInit(FMLServerStartingEvent event)
    {    
    	event.registerServerCommand(new HOEAdminCommands());
    }
    
    @EventHandler
    public void serverShutdown(FMLServerStoppingEvent event)
    {
		proxy.shutdownHOE();
		contextdetector.rejectContext();
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
