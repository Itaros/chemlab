package ru.itaros.hoe.utils;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.hoe.data.HVLCFillerData;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.tiles.IHOEInventorySyncable;

public class TileEntityHelper {

	public static ItemStack HOEpush(IHOEInventorySyncable tile, ItemStack item){
		HOEMachineData hmd = tile.getServerData();
		if(hmd!=null){
			if(hmd instanceof ISynchroportItems){
				ISynchroportItems synchroport = (ISynchroportItems)hmd;
				item=synchroport.tryToPutIn(item, null);
			}
		}
		return item;
	}
	public static ItemStack HOEpull(IHOEInventorySyncable tile, ItemStack item){
		HOEMachineData hmd = tile.getServerData();
		if(hmd!=null){
			if(hmd instanceof ISynchroportItems){
				ISynchroportItems synchroport = (ISynchroportItems)hmd;
				item=synchroport.tryToGetOut(item, null);
			}
		}
		return item;
	}
	
	
	
}
