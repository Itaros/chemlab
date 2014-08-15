package ru.itaros.hoe.tiles;

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
		return 1+1;//in and out synchrobound! %_%
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
		in=TileEntityHelper.HOEItemPush(this, in);
		fin.setFluid(TileEntityHelper.HOEFluidPush(this,fin.getFluid()));
	}

	@Override
	public void pullFromHOE() {
		out=TileEntityHelper.HOEItemPull(this, out);
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
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		FluidStack fl = fout.getFluid();
		if(fl==null){return null;}
		
		Fluid comparable = resource.getFluid();
		if(comparable==fl.getFluid()){
			return fout.drain(resource.amount, doDrain);
		}else{
			return null;
		}
		
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return fin.getFluidAmount()==0;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;//Drain is always possible \o/
	}

	private FluidTankInfo[] fluidTankInfo = new FluidTankInfo[2];
	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		fluidTankInfo[0]=new FluidTankInfo(fin);
		fluidTankInfo[1]=new FluidTankInfo(fout);
		return fluidTankInfo;
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
	
	public ItemStack setPort(int side, PortType type){
		PortInfo old = ports[side];
		
		ItemStack retr = getOldPortItem(old);
		if(type!=null){
			ports[side]=new PortInfo(type,null,false);
		}else{
			ports[side]=null;
		}
		this.getWorld().markBlockForUpdate(xCoord, yCoord, zCoord);
		
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
	}
	
	
}
