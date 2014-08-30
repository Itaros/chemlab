package ru.itaros.hoe.gui;

import ru.itaros.hoe.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class MachineSlot extends Slot {

	private HOESlotType type;
	
	public final HOESlotType getType() {
		return type;
	}

	private IConfigurableIO cio;
	
	
	public MachineSlot(IInventory par1iInventory, int par2, int par3, int par4, HOESlotType type) {
		super(par1iInventory, par2, par3, par4);
		this.type=type;
		if(par1iInventory instanceof IConfigurableIO){
			cio = (IConfigurableIO)par1iInventory;
		}
	}


	@Override
	public boolean isItemValid(ItemStack stack) {
		if(type==HOESlotType.AUX && cio!=null){
			PortInfo[] pis = cio.getPorts();
			PortInfo c = pis[this.getSlotIndex()-MachineCrafterTileEntity.PORTS_SHIFT];
			if(c!=null && !c.isNothing()){
				if(c.isItemSocket()){
					return true;
				}else{
					//Fuck you! There is no way to put items into fluid slots anymore
					return false;
				}
			}else{
				return false;
			}
		}else{
			return super.isItemValid(stack);
		}
	}


	@Override
	public ItemStack getStack() {
		// TODO Auto-generated method stub
		return super.getStack();
	}

	
	
	
}
