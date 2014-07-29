package ru.itaros.chemlab.tiles;

import buildcraft.api.gates.IAction;
import buildcraft.core.IMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.HVLCFillerData;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.IHOEInventorySyncable;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.FluidUtility;
import ru.itaros.hoe.utils.StackUtility;

public class HVLCFillerTileEntity extends MachineTileEntity implements ISidedInventory, IHOEInventorySyncable, IFluidHandler, IMachine {


	
	public HVLCFillerTileEntity(){
		super();
	}

	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub

	}

	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		HVLCFillerData sbd= new HVLCFillerData();
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
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("HVLCFillerIO");
	}
	
	
	//INVENTORY
	ItemStack inbound,outbound;
	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		//hoe synced
		if(slot<0){
			HVLCFillerData sd = (HVLCFillerData)this.getClientData();
			if(sd==null){return null;}
			switch(slot){
			case -1:
				return sd.getExemplar_cell_in();
			case -2:
				return sd.getExemplar_cell_out();
			}
		}
		
		//real
		switch(slot){
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
	public void setInventorySlotContents(int slot, ItemStack stack) {
		switch(slot){
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
		return "HVLCFillerCustomInventory";
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

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot==0){
			return stack.getItem()==ItemLoader.emptyhvlc;
		}
		return false;
	}

	//public final static int[] accessibleSlots = {0,1};
	public final static int[] accessibleSlots_TOP = {0};
	public final static int[] accessibleSlots_SIDES = {1};
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side==1){
			//TOP
			return accessibleSlots_TOP;
		}else{
			return accessibleSlots_SIDES;
		}
		
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int dir) {
		if(slot!=0){return false;}
		return isItemValidForSlot(slot,stack);
		//There is NO REBOUND
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int dir) {
		if(slot!=1){return false;}
		return true;
	}

	
	//HOE SYNC
	@Override
	public void pushToHOE() {
		HOEMachineData hmd = getServerData();
		if(hmd!=null){
			HVLCFillerData fd = (HVLCFillerData)hmd;
			//Items
			inbound=fd.tryToPutIn(inbound);
			//Fluid
			tank.setFluid(fd.tryToPutIn(tank.getFluid()));
		}
	}

	@Override
	public void pullFromHOE() {
		HOEMachineData hmd = getServerData();
		if(hmd!=null){
			HVLCFillerData fd = (HVLCFillerData)hmd;
			outbound=fd.tryToGetOut(outbound);
		}
	}

	
	//FLUIDS
	
	private FluidTank tank = new FluidTank(5000);
	private FluidTankInfo[] tankinfo = {new FluidTankInfo(tank)};
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return tankinfo;
	}
	
	public FluidTank getInputTank(){
		return tank;
	}


	
	//NBT
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		inbound = StackUtility.readItemStackFromNBT(nbt, "in");
		outbound = StackUtility.readItemStackFromNBT(nbt, "out");
		
		tank.setFluid(FluidUtility.readFluidStackFromNBT(nbt, "intank"));
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		StackUtility.writeItemStackToNBT(inbound, nbt, "in");
		StackUtility.writeItemStackToNBT(outbound, nbt, "out");
		
		FluidUtility.writeFluidStackToNBT(tank.getFluid(),nbt,"intank");
	}

	
	//BC Machine
	
	
	@Override
	public boolean allowAction(IAction arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isActive() {
		HOEMachineData hmd = getServerData();
		if(hmd!=null){
			HVLCFillerData fd = (HVLCFillerData)hmd;
			return fd.isThereSpareCell();
		}
		return false;
	}

	@Override
	public boolean manageFluids() {
		return true;
	}

	@Override
	public boolean manageSolids() {
		return true;
	}	
	
	
	
}
