package ru.itaros.hoe.utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import ru.itaros.chemlab.hoe.data.ArcFurnaceControllerData;
import ru.itaros.hoe.data.ISynchroportFluids;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.tiles.IHOEInventorySyncable;

public class TileEntityHelper {
	
	public static ItemStack HOEItemPush(IHOEInventorySyncable tile, ItemStack item){
		HOEMachineData hmd = tile.getServerData();
		if(hmd!=null){
			if(hmd instanceof ISynchroportItems){
				ISynchroportItems synchroport = (ISynchroportItems)hmd;
				item=synchroport.tryToPutItemsIn(item, null);
			}
		}
		return item;
	}
	public static FluidStack HOEFluidPush(IHOEInventorySyncable tile, FluidStack fluid) {
		HOEMachineData hmd = tile.getServerData();
		if(hmd!=null){
			if(hmd instanceof ISynchroportFluids){
				ISynchroportFluids synchroport = (ISynchroportFluids)hmd;
				fluid=synchroport.tryToPutFluidsIn(fluid, null);
			}
		}
		return fluid;
	}	
	
	public static ItemStack HOEItemPull(IHOEInventorySyncable tile, ItemStack item){
		HOEMachineData hmd = tile.getServerData();
		if(hmd!=null){
			if(hmd instanceof ISynchroportItems){
				ISynchroportItems synchroport = (ISynchroportItems)hmd;
				item=synchroport.tryToGetItemsOut(item, null);
			}
		}
		return item;
	}
	
	public static FluidStack HOEFluidPull(IHOEInventorySyncable tile, FluidStack fluid){
		HOEMachineData hmd = tile.getServerData();
		if(hmd!=null){
			if(hmd instanceof ISynchroportFluids){
				ISynchroportFluids synchroport = (ISynchroportFluids)hmd;
				fluid=synchroport.tryToGetFluidsOut(fluid, null);
			}
		}
		return fluid;
	}
	
	
	
}
