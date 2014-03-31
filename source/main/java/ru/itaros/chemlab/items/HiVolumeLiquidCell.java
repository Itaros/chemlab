package ru.itaros.chemlab.items;

import java.util.Hashtable;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import net.minecraft.item.Item;

public class HiVolumeLiquidCell extends Item {
	
	private static Hashtable<HOEFluid,HiVolumeLiquidCell> registered = new Hashtable<HOEFluid,HiVolumeLiquidCell>(); 
	
	public HiVolumeLiquidCell(HOEFluid fluid){
		super();
		this.setUnlocalizedName("chemlab:"+"hvlc-"+fluid.getCommonName());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:hvlc-"+fluid.getCommonName());
		
		registered.put(fluid, this);
	}
	
	
	public static HiVolumeLiquidCell getByFluid(HOEFluid fluid){
		return registered.get(fluid);
	}
	

}
