package ru.itaros.chemlab.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
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
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.fluid.FluidToHOE;
import ru.itaros.hoe.gui.ProgrammerSlot;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.IHOEInventorySyncable;
import ru.itaros.hoe.tiles.IUniversalInventory;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.tiles.ioconfig.PortType;
import ru.itaros.hoe.utils.FluidUtility;
import ru.itaros.hoe.utils.StackUtility;
import ru.itaros.hoe.utils.TileEntityHelper;

public class HVLCFillerTileEntity extends MachineTileEntity implements ISidedInventory, IUniversalInventory, IHOEInventorySyncable, IFluidHandler, IConfigurableIO {


	
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
	@Override
	public int getSizeInventory() {
		return portIndicesItems.length;
	}
	
	/*
	 * This method ensures retrieval of ItemStacks from slot to go along with minecraft
	 * (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getStackInSlot(int)
	 */
	@Override
	public ItemStack getStackInSlot(int slot) {
		PortInfo pi = ports[slot-PORTS_SHIFT];
		if(pi!=null){
			if(pi.getType()==PortType.ITEM){
				return (ItemStack)pi.getStack();
			}
			return null;
		}else{
			return null;
		}
	}
	
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		PortInfo pi = ports[slot-PORTS_SHIFT];
		if(pi!=null){
			pi.setStack(stack);
			return;
		}	
	}
	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
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
		int realside = this.getRealSide(side, this.getBlockMeta());
		return portIndicesItems[realside];
	}
	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		int realside = this.getRealSide(side, this.getBlockMeta());
		PortInfo pi = ports[realside];
		return pi!=null&&pi.isInput();
	}
	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		int realside = this.getRealSide(side, this.getBlockMeta());
		PortInfo pi = ports[realside];
		return pi!=null&&pi.isOutput();		
	}

	@Override
	public String getInventoryName() {
		return "HVLCFillerCustomInventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}


	
	//HOE SYNC
//	@Override
//	public void pushToHOE() {
//		HOEMachineData hmd = getServerData();
//		if(hmd!=null){
//			HVLCFillerData fd = (HVLCFillerData)hmd;
//			//Items
//			inbound=fd.tryToPutItemsIn(inbound);
//			//Fluid
//			tank.setFluid(fd.tryToPutIn(tank.getFluid()));
//		}
//	}
//
//	@Override
//	public void pullFromHOE() {
//		HOEMachineData hmd = getServerData();
//		if(hmd!=null){
//			HVLCFillerData fd = (HVLCFillerData)hmd;
//			outbound=fd.tryToGetItemsOut(outbound);
//		}
//	}

	//NBT
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		//inbound = StackUtility.readItemStackFromNBT(nbt, "in");
		//outbound = StackUtility.readItemStackFromNBT(nbt, "out");
		
		this.readIOPortsNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		//StackUtility.writeItemStackToNBT(inbound, nbt, "in");
		//StackUtility.writeItemStackToNBT(outbound, nbt, "out");
		
		this.writeIOPortsNBT(nbt);
	}

	@Override
	public IUniversalStack getStackInHOERemoteSlot(int slot){
		//hoe synced
		if(slot<0){
			HVLCFillerData sd = (HVLCFillerData)this.getClientData();
			if(sd==null){
				return null;
				}
			if(slot>-10){
				int realdataslot = (slot*-1)-1;
				if(realdataslot<2){
					return sd.get_in(realdataslot);
				}
			}else{
				int realdataslot = (slot*-1)-1-10;
				if(realdataslot<2){
					return sd.get_out(realdataslot);
				}				
			}
		}	
		return null;
	}

	//ConfigurableIO
	protected PortInfo[] ports = new PortInfo[PortInfo.amountOfPorts()];
	@Override
	public PortInfo[] getPorts(){
		return ports;
	}
	
	public ItemStack setPortToNothing(int side){
		return setPort(side,PortType.NOTHING);
	}
	
	public static int PORTS_SHIFT=2;//Slots 0 and 1(UI)
	private int[][] portIndicesItems=new int[6][0];
	private int[][] portIndicesFluids=new int[6][0];
	/*
	 * Revalidates io for mod crosscompats
	 */
	private void markPortsDirty(){
		portIndicesItems = new int[6][];//It is better to recreate it instead of cleaning it
		for(int i = 0 ; i < portIndicesItems.length; i ++){
			PortInfo pi = ports[i];
			int[] rslt;
			if(pi!=null && pi.isItemSocket()){
				rslt = new int[1];
				rslt[0]=i+PORTS_SHIFT;
			}else{
				rslt=new int[0];
			}
			portIndicesItems[i]=rslt;
		}
		
		portIndicesFluids = new int[6][];//It is better to recreate it instead of cleaning it
		for(int i = 0 ; i < portIndicesFluids.length; i ++){
			PortInfo pi = ports[i];
			int[] rslt;
			if(pi!=null && pi.isFluidSocket()){
				rslt = new int[1];
				rslt[0]=i+PORTS_SHIFT;
			}else{
				rslt=new int[0];
			}
			portIndicesFluids[i]=rslt;
		}		
	}
	
	public ItemStack setPort(int side, PortType type){
		PortInfo old = ports[side];
		
		ItemStack retr = getOldPortItem(old);
		if(type!=null){
			ports[side]=new PortInfo(type,null,false);
		}else{
			ports[side]=null;
		}
		this.getWorld().markBlockForUpdate(xCoord, yCoord, zCoord);
		
		markPortsDirty();
		
		return retr;		
	}
	
	private ItemStack getOldPortItem(PortInfo old){
		if(old==null){
			//Assumed plate
			return  new ItemStack(ItemLoader.panel,1);
		}else{
			//There is port!
			if(old.isNothing()){
				return null;
			}else{
				return old.getType().getRelevantItem();
			}
		}
	}

	public void writeIOPortsNBT(NBTTagCompound stackTagCompound){
		NBTTagList list = new NBTTagList();
		for(PortInfo pi:ports){
			list.appendTag(PortInfo.writeNBT(pi));
		}
		stackTagCompound.setTag("ioports", list);
	}
	public void readIOPortsNBT(NBTTagCompound stackTagCompound){
		NBTTagList list = stackTagCompound.getTagList("ioports", Constants.NBT.TAG_COMPOUND);
		for(int i = 0;i<list.tagCount();i++){
			NBTTagCompound c = list.getCompoundTagAt(i);
			ports[i]=PortInfo.readNBT(c);
		}
		markPortsDirty();
	}
	

	//Fluids	
	
	//ItemStack in, out;
	FluidTank fin = new FluidTank(1000);
	//FluidTank fout = new FluidTank(1000);
	
	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		FluidStack fl = fin.getFluid();
		if(fl==null){return fin.fill(resource, doFill);}
		
		if(fl.getFluid()==resource.getFluid()){
			return fin.fill(resource, doFill);
		}else{
			if(fl.amount==0){
				fin.setFluid(new FluidStack(resource.getFluid(),0));
				return fin.fill(resource, doFill);
			}
			return 0;
		}
	}

	@Override
	public FluidStack drain(ForgeDirection side, FluidStack resource,
			boolean doDrain) {
		return drain(side, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection side, int maxDrain, boolean doDrain) {
		PortInfo pi = ports[side.ordinal()];
		if(pi.isFluidSocket()){
			FluidStack flst = (FluidStack)pi.getStack();
			if(flst!=null && flst.amount>0){
				FluidStack drain;
				int differential = flst.amount-maxDrain;
				if(differential<0){
					//underflow
					drain = new FluidStack(flst.getFluid(),maxDrain+differential);
					if(doDrain){pi.setStack((FluidStack)null);}
				}else{
					//overflow
					drain = new FluidStack(flst.getFluid(),maxDrain);
					if(doDrain){
						flst.amount=differential;
						if(flst.amount==0){
							pi.setStack((FluidStack)null);
						}else{
							pi.setStack(flst);
						}
					}
				}
				return drain;
			}
		}
		
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection side, Fluid fluid) {
		PortInfo pi = ports[side.ordinal()];
		return pi.isFluidSocket() && pi.isInput();
	}

	@Override
	public boolean canDrain(ForgeDirection side, Fluid fluid) {
		PortInfo pi = ports[side.ordinal()];
		return pi.isFluidSocket() && pi.isOutput();
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection side) {
		PortInfo pi = ports[side.ordinal()];
		if(pi!=null && pi.isFluidSocket()){
			FluidStack flst = (FluidStack)pi.getStack();
			return new FluidTankInfo[]{new FluidTankInfo(flst,flst!=null?FluidToHOE.get(flst.getFluid()).getMaxStack():250)};
		}
		return null;
	}	
	
	
	@Override
	public void pushToHOE() {		
		//Main Inventory
		fin.setFluid(TileEntityHelper.HOEFluidPush(this,fin.getFluid()));
		super.pushToHOE();
	}
	
}
