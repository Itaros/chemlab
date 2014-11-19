package ru.itaros.chemlab.tiles.syndication;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.chemlab.syndication.utilities.ISyndicationUtility;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.client.ui.syndication.SyndicationItemPortContainer;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationItemPortData;
import ru.itaros.chemlab.hoe.io.syndication.SyndicationItemPortIO;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.IHOEInventorySyncable;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.StackUtility;

public class SyndicationItemPortTileEntity extends MachineTileEntity implements ISyndicationUtility, ISidedInventory, IHOEInventorySyncable{

	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		SyndicationItemPortData sbd= new SyndicationItemPortData();
		sbd.setOwnerFingerprint(fingerprint);
		machines.injectCustomData(sbd);
		return sbd;
	}

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (SyndicationItemPortIO) ChemLab.getIOCollection().getHOEIO("SyndicationItemPortIO");
	}
	
	//INVENTORY
	ItemStack inbound,outbound;
	
	
	private ItemStack filterCommiter;

	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slotid) {
		if(slotid == SyndicationItemPortContainer.SLOTID_FILTCOMM){
			return filterCommiter;
		}
		
		if(slotid == -5){
			SyndicationItemPortData data = (SyndicationItemPortData) this.getServerData();
			if(data==null){return null;}
			return data.getFilter();
		}
		
		//real
		switch(slotid){
		case 0:
			return inbound;
		case 1:
			return outbound;
		}		
		
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
    	if(slot<0){return (ItemStack)null;}//You can't take from client inbound
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
                if (stack.stackSize <= amt) {
                        setInventorySlotContents(slot, null);
                        
                } else {
                        stack = stack.splitStack(amt);
                        if (stack.stackSize == 0) {
                                setInventorySlotContents(slot, null);
                               
                        }
                }
        }
        return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slotid, ItemStack stack) {
		if(slotid == SyndicationItemPortContainer.SLOTID_FILTCOMM){
			filterCommiter=stack;
			return;
		}		
		
		switch(slotid){
		case 0:
			inbound=stack;
			break;
		case 1:
			outbound=stack;
			break;
		}		
		
	}

	@Override
	public String getInventoryName() {
		return "Syndication Item Port";
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
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int slotid, ItemStack stack) {
		//if(slotid == SyndicationItemPortContainer.SLOTID_FILTCOMM){return true;}
		return true;
	}

	public void pushFilter() {
		SyndicationItemPortData data = (SyndicationItemPortData) this.getServerData();
		data.setFilter(filterCommiter);
		filterCommiter=null;
		this.markDirty();
	}

	//HOE SYNC
	@Override
	public void pushToHOE() {
		HOEMachineData hmd = getServerData();
		if(hmd!=null){
			SyndicationItemPortData fd = (SyndicationItemPortData)hmd;
			//if(fd==null){throw new RuntimeException("Broken HOEData: "+hmd.getClass().getName());}
			if(fd.getFilter()==null || inbound!=null && inbound.isItemEqual(fd.getFilter())){
				inbound=fd.tryToPutItemsIn(inbound, null);
			}
		}
	}

	@Override
	public void pullFromHOE() {
		HOEMachineData hmd = getServerData();
		if(hmd!=null){
			SyndicationItemPortData fd = (SyndicationItemPortData)hmd;
			outbound=fd.tryToGetItemsOut(outbound, null);
		}
	}

	public final static int[] accessibleSlots_ALL = {0,1};
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return accessibleSlots_ALL;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int dir) {
		if(slot!=0){return false;}
		return isItemValidForSlot(slot,stack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int dir) {
		if(slot!=1){return false;}
		return true;
	}

	
	@Override
	public boolean askControllerToDie(EntityPlayer player){
		return SyndicationPipingTileEntity.utility_askControllerToDie(player, this);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		inbound=StackUtility.readItemStackFromNBT(nbt, "iport_in");
		outbound=StackUtility.readItemStackFromNBT(nbt, "iport_out");
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		StackUtility.writeItemStackToNBT(inbound, nbt, "iport_in");
		StackUtility.writeItemStackToNBT(outbound, nbt, "iport_out");
	}	
	
	
	
}
