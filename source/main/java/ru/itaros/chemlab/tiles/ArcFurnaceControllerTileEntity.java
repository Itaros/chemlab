package ru.itaros.chemlab.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.blocks.multiblock.IMultiblockController;
import ru.itaros.chemlab.blocks.multiblock.MBAssociativeDataPayload;
import ru.itaros.chemlab.blocks.multiblock.MBDefinitionArcFurnace;
import ru.itaros.chemlab.hoe.data.ArcFurnaceControllerData;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.MultiblockLoader;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.gui.ProgrammerSlot;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.jobs.HOEMachines;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.tiles.ioconfig.PortType;

public class ArcFurnaceControllerTileEntity extends MachineTileEntity implements IMultiblockController, ISidedInventory, IConfigurableIO{

	private static final float MAX_HEATRESIST = 1600F;
	private static final float MAX_VOLRESIST = 1F;

	public ArcFurnaceControllerTileEntity(){
		super();
	}
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines,
			HOEDataFingerprint fingerprint) {
		ArcFurnaceControllerData sbd= new ArcFurnaceControllerData();
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
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("ArcFurnaceControllerIO");
	}

	private boolean isDecomposed=true;
	
	@Override
	public void notifyDecomposition() {
		isDecomposed=true;
	}
	@Override
	public void notifyComposition() {
		isDecomposed=false;
	}

	public void setValues(MBAssociativeDataPayload payload) {
		//tempresist
		//volresist
		Integer tempresist = (Integer) payload.get("tempresist");
		Integer volresist = (Integer) payload.get("volresist");

		if(tempresist==null){tempresist=0;}
		if(volresist==null){volresist=0;}
		
		//Calculating factor
		float tempResistFactor=(float)tempresist/(float)MBDefinitionArcFurnace.DEFID_INSULATION;
		float volumeResistFactor=(float)volresist/(float)MBDefinitionArcFurnace.DEFID_CHASSIS;
		
		//Enforce postload. It is already populated
		this.onPostLoad();
		
		//Pushing factors to HOE
		ArcFurnaceControllerData data = (ArcFurnaceControllerData) this.getServerData();
		data.setHeatResistance(tempResistFactor*MAX_HEATRESIST/MultiblockLoader.arcFurnace.getInsulationLevels());
		data.setVolumeResistance(volumeResistFactor*MAX_VOLRESIST/MultiblockLoader.arcFurnace.getChassisLevels());
	}

	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		this.readIOPortsNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		this.writeIOPortsNBT(nbt);
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
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		
		PortInfo pi = ports[slot-PORTS_SHIFT];
		if(pi!=null){
			return (ItemStack)pi.getStack();
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
		return "Arc Furnace Assembly";
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

	@Override
	public int getSizeInventory() {
		return portIndicesItems.length;
	}
	
}
