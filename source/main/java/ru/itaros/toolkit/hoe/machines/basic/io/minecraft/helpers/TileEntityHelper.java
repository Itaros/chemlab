package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers;

import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.hoe.data.HVLCFillerData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.IHOEInventorySyncable;
import ru.itaros.toolkit.hoe.machines.interfaces.ISynchroportItems;

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
