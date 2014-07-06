package ru.itaros.hoe.vanilla.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import org.apache.logging.log4j.Level;

import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.hoe.data.GasChimneyData;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineCrafterData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ProgrammerSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.TileEntityHelper;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.IHOEInventorySyncable;
import cpw.mods.fml.common.FMLLog;


public abstract class MachineCrafterTileEntity extends MachineTileEntity implements ISidedInventory, IHOEInventorySyncable{

	ItemStack programmerStack=null;
	
	public MachineCrafterTileEntity(){
		super();

	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint){
		HOEMachineData data = machines.generateMachineCrafterData();
		data.setOwnerFingerprint(fingerprint);
		return data;
	}


	//INVENTORY	
	
	ItemStack in, out;
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		in = StackUtility.readItemStackFromNBT(nbt, "in");
		out = StackUtility.readItemStackFromNBT(nbt, "out");
		programmerStack = StackUtility.readItemStackFromNBT(nbt, "prog");
			
		
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		StackUtility.writeItemStackToNBT(in, nbt, "in");
		StackUtility.writeItemStackToNBT(out, nbt, "out");
		StackUtility.writeItemStackToNBT(programmerStack, nbt, "prog");
		
	}
	
	
	
	@Override
	public int getSizeInventory() {
		return 1+1;//in and out synchrobound! %_%
	}
	

	
	@Override
	public ItemStack getStackInSlot(int slot) {
		//prgrammer
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			return programmerStack;
		}
		//hoe synced
		if(slot<0){
			HOEMachineCrafterData sd = (HOEMachineCrafterData)this.getClientData();
			if(sd==null){
				return null;
				}
			if(slot>-10){
				int realdataslot = (slot*-1)-1;
				if(realdataslot<sd.inSize()){
					return sd.get_in_withRecipe(realdataslot);
				}
			}else{
				int realdataslot = (slot*-1)-1-10;
				if(realdataslot<sd.outSize()){
					return sd.get_out_withRecipe(realdataslot);
				}				
			}
		}
		
		//real
		switch(slot){
		case 0:
			return in;
		case 1:
			return out;
		}
		return null;
	}
	
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		switch(slot){
		case 0:
			in=stack;
			break;
		case 1:
			out=stack;
			break;

			
			
		case ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT:
			programmerStack=stack;
			if(programmerStack==null){programmerWasOpened=false;break;}
			if(!programmerWasOpened){
				programmatorScreenLauncher();
				programmerWasOpened=true;
			}
			//programmerStack=null;//TODO: DEBUG!!!
			break;
			
		}		
	}
	
	private boolean programmerWasOpened=false;
	
	private void programmatorScreenLauncher() {
		if(programmerStack!=null && programmerStack.getItem()==ItemLoader.programmer){
			
			ChemLab.proxy.openProgrammerGUI(this);
		}
		
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			if(stack.getItem()==ItemLoader.programmer){
				return true;
			}else{
				return false;
			}
		}		
		
		if(slot<0){return false;}//There is no way to push something into chamber
		if(slot==1){return false;}
		
		if(slot==0){
			//Injector slot
			return true;
		}
		
		return true;
	}
	
	

	@Override
    public ItemStack decrStackSize(int slot, int amt) {
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			ItemStack ret = this.getStackInSlot(slot);
			setInventorySlotContents(slot, null);
			return ret;
		}
		
    	if(slot<0){return (ItemStack)null;}//You can't take from client inbound
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
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;//Do not drops
	}
	@Override
	public String getInventoryName() {
		return "HOEMachineInventory";
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
	}
	@Override
	public void closeInventory() {
	}
	
	
	private static final int[] synchroportAccessIndices = {0,1};
	
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return synchroportAccessIndices;
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if(slot==0){
			return !false;//Cant insert if failed
		}else{
			return false;
		}
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		if(slot==0){
			return false;
		}else{
			//1
			return true;
		}
	}
	
	
	//Sync
	
	@Override
	public void pushToHOE() {
		in=TileEntityHelper.HOEpush(this, in);
	}

	@Override
	public void pullFromHOE() {
		out=TileEntityHelper.HOEpull(this, out);
	}
	
	
	
	
	public boolean trySetRecipe(Recipe r){
		if(server!=null){
			HOEMachineCrafterData d = getCrafterServer();
			if(!d.evaluateHasItems()){
				d.setRecipe(r);
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
	
	
	
	//Wrappers
	private HOEMachineCrafterData getCrafterClient(){
		return (HOEMachineCrafterData)client;
	}
	private HOEMachineCrafterData getCrafterServer(){
		return (HOEMachineCrafterData)server;
	}	
	
	
}
