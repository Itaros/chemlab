package ru.itaros.chemlab.loader;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.items.HiVolumeLiquidCellEmpty;
import ru.itaros.chemlab.items.LignocelluloseFlakes;
import ru.itaros.chemlab.items.Programmer;
import ru.itaros.chemlab.items.WoodchipClump;
import ru.itaros.chemlab.items.Woodchips;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class ItemLoader {

	public static Programmer programmer;
	
	public static HiVolumeLiquidCellEmpty emptyhvlc;
	
	public static Woodchips woodchips;
	public static WoodchipClump woodchipclump;
	
	public static LignocelluloseFlakes lignocelluloseflakes;
	
	public static void loadItems(){
		programmer = new Programmer();
		GameRegistry.registerItem(programmer,programmer.getUnlocalizedName());
		
		
		woodchips = new Woodchips();
		GameRegistry.registerItem(woodchips, woodchips.getUnlocalizedName());
		
		woodchipclump = new WoodchipClump();
		GameRegistry.registerItem(woodchipclump, woodchipclump.getUnlocalizedName());	
		
		lignocelluloseflakes = new LignocelluloseFlakes();
		GameRegistry.registerItem(lignocelluloseflakes,lignocelluloseflakes.getUnlocalizedName());	
		
		
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
