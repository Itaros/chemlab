package ru.itaros.hoe.tiles;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import org.apache.logging.log4j.Level;

import ru.itaros.api.hoe.IHOEContextDetector.FMLContext;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.hoe.data.GasChimneyData;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.ContextDetector;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.fluid.FluidToHOE;
import ru.itaros.hoe.gui.ProgrammerSlot;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.recipes.Recipe;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.tiles.ioconfig.PortType;
import ru.itaros.hoe.utils.StackUtility;
import ru.itaros.hoe.utils.TileEntityHelper;
import cpw.mods.fml.common.FMLLog;


public abstract class MachineCrafterTileEntity extends MachineTileEntity implements ISidedInventory, IUniversalInventory, IHOEInventorySyncable, IFluidHandler, IConfigurableIO{

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
	FluidTank fin = new FluidTank(1000);
	FluidTank fout = new FluidTank(1000);
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		in = StackUtility.readItemStackFromNBT(nbt, "in");
		out = StackUtility.readItemStackFromNBT(nbt, "out");
		programmerStack = StackUtility.readItemStackFromNBT(nbt, "prog");
			
		this.readIOPortsNBT(nbt);
		
	}
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		StackUtility.writeItemStackToNBT(in, nbt, "in");
		StackUtility.writeItemStackToNBT(out, nbt, "out");
		StackUtility.writeItemStackToNBT(programmerStack, nbt, "prog");
		
		this.writeIOPortsNBT(nbt);
		
	}
	
	
	
	@Override
	public int getSizeInventory() {
		return portIndicesItems.length;
	}
	

	
	public IUniversalStack getStackInHOERemoteSlot(int slot){
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
		return null;
	}
	
	
	/*
	 * This method ensures retrieval of ItemStacks from slot to go along with minecraft
	 * (non-Javadoc)
	 * @see net.minecraft.inventory.IInventory#getStackInSlot(int)
	 */
	@Override
	public ItemStack getStackInSlot(int slot) {
		//prgrammer
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			return programmerStack;
		}		

		if(slot<PORTS_SHIFT){
			if(slot==0){
				return in;
			}else if(slot==1){
				return out;
			}
		}
		
		PortInfo pi = ports[slot-PORTS_SHIFT];
		if(pi!=null){
			if(pi.getType()==PortType.ITEM){
				return (ItemStack)pi.getStack();
			}
			return null;
		}else{
			return null;
		}
		
		
		
		//return null;
	}
	
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		
		if(slot==ProgrammerSlot.PROGRAMMER_DEFAULT_SLOT){
			programmerStack=stack;
			if(programmerStack==null){programmerWasOpened=false;return;}
			if(!programmerWasOpened){
				programmatorScreenLauncher();
				programmerWasOpened=true;
			}
			//programmerStack=null;//TODO: DEBUG!!!
			
		}		
		
		if(slot<PORTS_SHIFT){
			if(slot==0){
				in=stack;
			}else if(slot==1){
				out=stack;
			}
			return;
		}	
		
		PortInfo pi = ports[slot-PORTS_SHIFT];
		if(pi!=null){
			pi.setStack(stack);
			return;
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
	
	
	//Sync
	@Override
	public void pushToHOE() {		
		//Main Inventory
		in=TileEntityHelper.HOEItemPush(this, in);
		fin.setFluid(TileEntityHelper.HOEFluidPush(this,fin.getFluid()));
		super.pushToHOE();
	}

	@Override
	public void pullFromHOE() {
		out=TileEntityHelper.HOEItemPull(this, out);
		super.pullFromHOE();
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
		return (HOEMachineCrafterData)getClientData();
	}
	private HOEMachineCrafterData getCrafterServer(){
		return (HOEMachineCrafterData)getServerData();
	}
	
	//Fluids	
	
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
	
	
}
