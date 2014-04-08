package ru.itaros.chemlab;

import java.util.EnumMap;

import org.apache.logging.log4j.Level;

import ru.itaros.chemlab.client.ui.common.GUIHandler;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.GUILoader;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.recipes.RecipesLoader;
import ru.itaros.chemlab.loader.tileentity.TileEntityLoader;
import ru.itaros.chemlab.network.ChemLabChannel;
import ru.itaros.chemlab.network.IPacketCodecDescriptor;
import ru.itaros.chemlab.network.NetworkChannel;
import ru.itaros.chemlab.proxy.Proxy;
import ru.itaros.chemlab.proxy.Server;
import ru.itaros.chemlab.worldgen.WorldGenerator;
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
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

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
    
    //TODO: should move channels to a different class
    EnumMap<Side, FMLEmbeddedChannel> channels;
    public void SendPacketAsClientToServer(IPacketCodecDescriptor descriptor){
    	channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
    	channels.get(Side.CLIENT).writeOutbound(descriptor);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	channels = NetworkRegistry.INSTANCE.newChannel("chemlabchannel", new ChemLabChannel());
    	
    	GUILoader.loadGUIs();
    	
    	new ChemLabCreativeTab();//There is forgebug somewhere close
    
    	HOEFluidLoader.load();
    	
 		BlockLoader.loadBlocks();
 		ItemLoader.loadItems();
		iocollection = TileEntityLoader.load();   	
    	
    }     
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
    	GameRegistry.registerWorldGenerator(new WorldGenerator(), 100);
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
