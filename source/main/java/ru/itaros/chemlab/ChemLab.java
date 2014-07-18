package ru.itaros.chemlab;

import java.util.EnumMap;

import net.minecraftforge.common.MinecraftForge;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.chemlab.client.ui.common.GUIHandler;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.events.SyndicationSystemPipingProtection;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.DamageSourceLoader;
import ru.itaros.chemlab.loader.GUILoader;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.RecipesLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.chemlab.loader.recipes.optional.VanillaTweaks;
import ru.itaros.chemlab.loader.tileentity.TileEntityLoader;
import ru.itaros.chemlab.minecraft.achievements.ChemLabAchievements;
import ru.itaros.chemlab.network.ChemLabChannel;
import ru.itaros.chemlab.network.IPacketCodecDescriptor;
import ru.itaros.chemlab.proxy.Proxy;
import ru.itaros.hoe.HOE;
import ru.itaros.hoe.tiers.TierRegistry;
import ru.itaros.toolkit.hoe.io.IOCollectionHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = ChemLab.MODID, version = ChemLab.VERSION, dependencies="required-after:hoelib;required-after:BuildCraft|Core")
public class ChemLab
{
	public ChemLab(){
		super();
		instance=this;
	}
	
    public static final String MODID = "chemlab";
    public static final String VERSION = "2.x";
    public static final String getPublicVersionNotation(){
    	return "PR-2.03";
    }
    public static final String getMCVersionNotation(){
    	return "1.7.2";
    }    
    
    @SidedProxy(clientSide="ru.itaros.chemlab.proxy.Client", serverSide="ru.itaros.chemlab.proxy.Server")
    public static Proxy proxy;
    
    private static IOCollectionHelper iocollection;
    public static IOCollectionHelper getIOCollection(){
    	return iocollection;
    }
    
    //TODO: should move channels to a different class
    EnumMap<Side, FMLEmbeddedChannel> channels;
    public void SendPacketAsClientToServer(IPacketCodecDescriptor descriptor){
    	channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
    	channels.get(Side.CLIENT).writeOutbound(descriptor);
    }
    
    private static Config cfg;
    public static Config getConfig(){
    	return cfg;
    }
    
    VersionCheckerIntegration versioncheck;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	versioncheck = new VersionCheckerIntegration().startPolling();
    	
    	tier_registry=new TierRegistry();
    	TierLoader.loadTiers();
    	
    	cfg = new Config().loadConfig(event);
    	
    	channels = NetworkRegistry.INSTANCE.newChannel("chemlabchannel", new ChemLabChannel());
    	
    	GUILoader.loadGUIs();
    	
    	new ChemLabCreativeTab();//There is forgebug somewhere close
    
    	HOEFluidLoader.load();
    	
 		BlockLoader.loadBlocks();
 		ItemLoader.loadItems();
		iocollection = TileEntityLoader.load();   
		
		DamageSourceLoader.load();
		
		//GFX
		proxy.registerGFX();
		
		//Tweaks
		VanillaTweaks.tweakIron();
		VanillaTweaks.createWroughtIronTools();
		
		//Block Breaking Events
		MinecraftForge.EVENT_BUS.register(new SyndicationSystemPipingProtection());
		
		//Achievements
		ChemLabAchievements.load();
    	
    }     
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
    	versioncheck.makeFinal();
    	
    	HOE.getInstance().getHOEExecutor().execute(cfg.worldgenerator_clid);
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		RecipesLoader.load();
		HOEIO.getIORegistry().claimOwnership();
		
		new GUIHandler();
    }   
    
 
    private TierRegistry tier_registry;
    public TierRegistry getTierRegistry(){
    	return tier_registry;
    }
    
    
    @EventHandler
    public void serverInit(FMLServerAboutToStartEvent event)
    {    
    	//Hack to load correct proxy	
    	
    	//if(ContextDetector.getInstance().getContext()==FMLContext.DEDICATED || ContextDetector.getInstance().getContext()==FMLContext.INTEGRATED){
		//    proxy = new Server();
		//    FMLLog.log(Level.INFO, "HOE[ChemLab] PROXY HACK ELEVATED!");
    	//}
    	
    	
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
