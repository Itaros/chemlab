package ru.itaros.hoe;


import ru.itaros.api.hoe.IHOEInterfacer;
import ru.itaros.hoe.interfacer.HOEInterfacer;
import ru.itaros.hoe.proxy.HOEProxy;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import ru.itaros.hoe.registries.HOEIORegistry;
import ru.itaros.hoe.registries.HOERecipeRegistry;
import ru.itaros.hoe.registries.vanilla.WorldGenRegistry;
import ru.itaros.hoe.signatures.HOEExecutor;
import ru.itaros.hoe.threading.HOEThreadController;
import ru.itaros.hoe.toolkit.worldgen.HOEWorldGenerator;
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
    
    //============MTA============
    private HOEFluidRegistry hoefluidregistry;
    private HOEIORegistry ioregistry;
    private HOERecipeRegistry reciperegistrry;
    
    private ContextDetector contextdetector;
    //===========================
    //============AHT============
    private HOEExecutor hoeexec;
    //===========================
    //============VMC============
    private WorldGenRegistry worldgenregistry;
    //===========================
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	hoeexec = new HOEExecutor();
    	
    	contextdetector = new ContextDetector();
    	
    	ForgeFixer.forgeOreDictFix();
    	
    	worldgenregistry=new WorldGenRegistry();
    	
    	hoefluidregistry=new HOEFluidRegistry();
    	ioregistry =new HOEIORegistry();
    	reciperegistrry=new HOERecipeRegistry();
    	interfacer=new HOEInterfacer();
    	
    }    
    
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {   
    	GameRegistry.registerWorldGenerator(new HOEWorldGenerator(), 100);
    }
    
    
    @EventHandler
    public void serverInit(FMLServerAboutToStartEvent event)
    {
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
