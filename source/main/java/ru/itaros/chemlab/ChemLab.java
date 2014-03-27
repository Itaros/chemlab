package ru.itaros.chemlab;

import ru.itaros.chemlab.loader.recipes.RecipesLoader;
import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ChemLab.MODID, version = ChemLab.VERSION)
public class ChemLab
{
    public static final String MODID = "chemlab";
    public static final String VERSION = "1.0";
    
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
		
    }     
    @EventHandler
    public void Init(FMLInitializationEvent event)
    {
		
    }
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
		RecipesLoader.load();
    }   
    
    
}
