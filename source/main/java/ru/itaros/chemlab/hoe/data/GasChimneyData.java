package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid.HOEFluidState;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackTransferTuple;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;
import ru.itaros.toolkit.hoe.machines.interfaces.ISynchroportItems;

public class GasChimneyData extends HOEMachineData implements ISynchroportItems {

	
	/*
	 * Reflection autocaster
	 */
	public GasChimneyData(HOEData parent){
		super(parent);
	}	
	
	public GasChimneyData(){
		super();
	}	
	
	public static final int SMOKE_LIMIT=20*50;
	private int chimneyAccumulatedSmoke=0;
	public int holdChimneySmoke(){
		int temp = chimneyAccumulatedSmoke;
		chimneyAccumulatedSmoke=0;
		return temp;
	}

	
	private ItemStack inbound,outbound;
	public ItemStack get_cell_in(){
		return inbound;
	}
	public ItemStack get_cell_out(){
		return outbound;
	}	
	//=======WORK TRIGGER=======
	//TODO: Extract class and interface
	private boolean hasWork=false;
	private int refreshSpin=0;
	private static final int SPIN_THRESHOLD=20-1;
	public boolean hasWork(){
		if(hasWork){
			return true;
		}else{
			refreshSpin++;
			if(refreshSpin>SPIN_THRESHOLD){
				hasWork=checkWork();
				refreshSpin=0;
			}
			return false;
		}
	}
	/*
	 * IHOEActiveMachine
	 */
	protected void reevaluateWork(){
		hasWork=checkWork();
	}
	private boolean checkWork(){
		if(outbound!=null && outbound.stackSize>=64){
			return false;
		}
		if(inbound==null){
			return false;
		}else if(inbound.stackSize<1){
			return false;
		}
		return true;
	}
	/*
	 * IHOEActiveMachine
	 */
	public void produce(boolean doReal) {
		if(checkWork()){
			Item i = inbound.getItem();
			if(isItemValid(i)){
				inbound.stackSize--;
				inbound=StackUtility.verify(inbound);
				
				if(outbound==null){
					outbound = new ItemStack(ItemLoader.emptyhvlc,1);
				}else{
					outbound.stackSize++;
				}
				//Smoke
				chimneyAccumulatedSmoke+=this.ticksRequared*5;
				if(chimneyAccumulatedSmoke>SMOKE_LIMIT){
					chimneyAccumulatedSmoke=SMOKE_LIMIT;
				}
			}else{
				if(outbound==null){
					ItemStack temp = inbound;
					inbound=null;
					outbound=temp.copy();
				}
			}
			//Optimized reevaluation
			if(inbound==null){
				reevaluateWork();
			}
		}
	}
	private boolean isItemValid(Item i) {
		if(i instanceof HiVolumeLiquidCell){
			HiVolumeLiquidCell cell = (HiVolumeLiquidCell)i;
			return cell.getFluid().getState()==HOEFluidState.GAS;
		}else{
			return false;
		}
	}

	//==========================
	
	
	
	StackTransferTuple transferTuple = new StackTransferTuple();
	
	public ItemStack tryToPutIn(ItemStack source){
		return tryToPutIn(source, null);
	}

	public ItemStack tryToPutIn(ItemStack source, ItemStack filter){
		transferTuple.fill(inbound, source);
		source=StackUtility.tryToPutIn(transferTuple,false,null);
		inbound=transferTuple.retr1();
		this.markDirty();
		return source;
	}
	public ItemStack tryToGetOut(ItemStack target){
		return tryToGetOut(target, null);
	}

	public ItemStack tryToGetOut(ItemStack target, ItemStack filter){
		transferTuple.fill(target, outbound);
		target = StackUtility.tryToGetOut(transferTuple,null);
		outbound=StackUtility.verify(transferTuple.retr2());
		this.markDirty();
		return target;
	}	
	
	@Override
	public void readNBT(NBTTagCompound nbt) {
		super.readNBT(nbt);
		inbound=StackUtility.readItemStackFromNBT(nbt, "initem");
		outbound=StackUtility.readItemStackFromNBT(nbt, "outitem");
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		super.writeNBT(nbt);
		StackUtility.writeItemStackToNBT(inbound, nbt, "initem");
		StackUtility.writeItemStackToNBT(outbound, nbt, "outitem");
	}

	@Override
	public void sync() {
		super.sync();
		
		GasChimneyData childd=(GasChimneyData) child;
		
		childd.hasWork=hasWork;
		
		childd.inbound = StackUtility.syncItemStacks(childd.inbound, inbound);
		childd.outbound = StackUtility.syncItemStacks(childd.outbound, outbound);
		
	}


	//Synchromanager(visual inventory sync)
	protected boolean isDirty=false;
	@Override
	public void markDirty() {
		isDirty=true;
	}
	@Override
	public boolean pollDirty() {
		boolean cache = isDirty;
		isDirty=false;
		return cache;
	}
	
	
	
}
