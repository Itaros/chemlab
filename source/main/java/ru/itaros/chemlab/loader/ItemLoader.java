package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.items.*;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class ItemLoader {

	public static Programmer programmer;
	
	public static HiVolumeLiquidCellEmpty emptyhvlc;
	
	public static Woodchips woodchips;
	public static WoodchipClump woodchipclump;
	
	public static WoodFiberFlakes lignocelluloseflakes;
	public static PureWoodFiberFlakes purelignocelluloseflakes;
	public static ImpregnatedWoodFibers impregnatedlignocelluloseflakes;
	public static ImpregnatedWoodFibersPellet impregnatedwoodfiberspellet;
	public static ExplodedWoodFibers explodedwoodfibers;
	public static WashedLignocellulose washedlignocellulose;
	public static ImpregnatedLignocellulose impregnatedlignocellulose;
	public static PressedLignocellulose pressedlignocellulose;
	public static DecomposedLignocellulose decomposedlignocellulose;
	
	
	public static void loadItems(){
		programmer = new Programmer();
		GameRegistry.registerItem(programmer,programmer.getUnlocalizedName());
		
		
		woodchips = new Woodchips();
		GameRegistry.registerItem(woodchips, woodchips.getUnlocalizedName());
		
		woodchipclump = new WoodchipClump();
		GameRegistry.registerItem(woodchipclump, woodchipclump.getUnlocalizedName());	
		
		lignocelluloseflakes = new WoodFiberFlakes();
		GameRegistry.registerItem(lignocelluloseflakes,lignocelluloseflakes.getUnlocalizedName());	
		
		purelignocelluloseflakes = new PureWoodFiberFlakes();
		GameRegistry.registerItem(purelignocelluloseflakes,purelignocelluloseflakes.getUnlocalizedName());	
		
		impregnatedlignocelluloseflakes = new ImpregnatedWoodFibers();
		GameRegistry.registerItem(impregnatedlignocelluloseflakes,impregnatedlignocelluloseflakes.getUnlocalizedName());	
				
		impregnatedwoodfiberspellet = new ImpregnatedWoodFibersPellet();
		GameRegistry.registerItem(impregnatedwoodfiberspellet,impregnatedwoodfiberspellet.getUnlocalizedName());	
			
		explodedwoodfibers = new ExplodedWoodFibers();
		GameRegistry.registerItem(explodedwoodfibers,explodedwoodfibers.getUnlocalizedName());
			
		washedlignocellulose = new WashedLignocellulose();
		GameRegistry.registerItem(washedlignocellulose,washedlignocellulose.getUnlocalizedName());
			
		impregnatedlignocellulose = new ImpregnatedLignocellulose();
		GameRegistry.registerItem(impregnatedlignocellulose,impregnatedlignocellulose.getUnlocalizedName());
		
		pressedlignocellulose = new PressedLignocellulose();
		GameRegistry.registerItem(pressedlignocellulose,pressedlignocellulose.getUnlocalizedName());
		
		decomposedlignocellulose = new DecomposedLignocellulose();
		GameRegistry.registerItem(decomposedlignocellulose,decomposedlignocellulose.getUnlocalizedName());
		
		
		
		emptyhvlc = new HiVolumeLiquidCellEmpty();
		GameRegistry.registerItem(emptyhvlc,emptyhvlc.getUnlocalizedName());
		
		
		
		
		
		loadAutoItems();
		
	}

	
	private static void loadAutoItems() {
		//This method is unsafe.
		//Waiting for Player to fix FML skipper
		
		hiVolumeLiquidCellAutoloader();
		
	}


	private static void hiVolumeLiquidCellAutoloader() {
		HOEFluid[] all = HOEFluid.getFluidRegistry().all();
		for(HOEFluid f : all){
			HiVolumeLiquidCell hvlc = new HiVolumeLiquidCell(f);
			GameRegistry.registerItem(hvlc, hvlc.getUnlocalizedName());
		}
		
	}
}
