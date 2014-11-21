package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.items.CIOWrench;
import ru.itaros.chemlab.items.HiVolumeLiquidCellEmpty;
import ru.itaros.chemlab.items.ItemPortApplianceItem;
import ru.itaros.chemlab.items.PipeWrench;
import ru.itaros.chemlab.items.Programmer;
import ru.itaros.hoe.tiles.ioconfig.PortType;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLoader {

	public static Programmer programmer;
	public static PipeWrench wrench;
	public static CIOWrench ciowrench;
	
	public static HiVolumeLiquidCellEmpty emptyhvlc;

	
	//general components
	public static ItemPortApplianceItem panel;
	
	//IPAIs
	public static ItemPortApplianceItem ipai_items;
	public static ItemPortApplianceItem ipai_fluids;	
	
	public static void loadItems(){
		
		//COMPONENTS
		panel=new ItemPortApplianceItem("component.panel",null);
		GameRegistry.registerItem(panel,panel.getUnlocalizedName());
		
		
		//IPAIs
		ipai_items= new ItemPortApplianceItem("ipai.items",PortType.ITEM);
		ipai_fluids= new ItemPortApplianceItem("ipai.fluids",PortType.FLUID);
		GameRegistry.registerItem(ipai_items,ipai_items.getUnlocalizedName());
		GameRegistry.registerItem(ipai_fluids,ipai_fluids.getUnlocalizedName());
		
		//TOOLS
		programmer = new Programmer();
		GameRegistry.registerItem(programmer,programmer.getUnlocalizedName());
		
		wrench = new PipeWrench();
		GameRegistry.registerItem(wrench,wrench.getUnlocalizedName());
		
		ciowrench = new CIOWrench();
		GameRegistry.registerItem(ciowrench,ciowrench.getUnlocalizedName());
		
		emptyhvlc = new HiVolumeLiquidCellEmpty();
		GameRegistry.registerItem(emptyhvlc,emptyhvlc.getUnlocalizedName());
		
		
	}

}
