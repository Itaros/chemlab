package ru.itaros.chemlab.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.HeatingFurnaceData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.IUniversalInventory;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.TileEntityHelper;

public class HeatingFurnaceTileEntity extends MachineTileEntity implements
		IInventory, IUniversalInventory {

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("HeatingFurnaceIO");
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines,
			HOEDataFingerprint fingerprint) {
		HeatingFurnaceData data = new HeatingFurnaceData();
		machines.injectCustomData(data);
		data.setOwnerFingerprint(fingerprint);
		return data;
	}
	
	protected ItemStack in,out;

	@Override
	public void pushToHOE() {
		in=TileEntityHelper.HOEItemPush(this, in);
		super.pushToHOE();
	}

	@Override
	public void pullFromHOE() {
		out=TileEntityHelper.HOEItemPull(this, out);
		super.pullFromHOE();
	}

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		if(slot==0){return in;}else{return out;}
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
		 ItemStack stack = getStackInSlot(slot);
         if (stack != null) {
                 if (stack.stackSize <= amt) {
                         setInventorySlotContents(slot, null);
                         if(stack.stackSize==0){
                         	//non existant stack
                         	return null;
                         }
                 } else {
                 		ItemStack originalStack = stack;
                         stack = originalStack.splitStack(amt);
                         if (originalStack.stackSize == 0) {
                                 setInventorySlotContents(slot, null);
                         }
                 }
         }
         return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		if(slot==0){
			in=stack;
		}else if(slot==1){
			out=stack;
		}
		
	}

	@Override
	public String getInventoryName() {
		return "CLHeatingFurnaceInventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
	}
	
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return true;
	}

	public IUniversalStack getStackInHOERemoteSlot(int slot){
		//hoe synced
		if(slot==-1 || slot==-2){
			HeatingFurnaceData sd = (HeatingFurnaceData)this.getClientData();
			if(sd==null){
				return null;
				}
			if(slot==-1){
				return sd.get_in();
			}else{
				return sd.get_out();			
			}
		}	
		return null;
	}

	@Override
	protected double getLocalAEMaxPower() {
		return 0;
	}

}
