package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluid.HOEFluidState;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.ItemStackTransferTuple;
import ru.itaros.hoe.utils.StackUtility;

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

	
	private IUniversalStack inbound,outbound;
	public IUniversalStack get_cell_in(){
		return inbound;
	}
	public IUniversalStack get_cell_out(){
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
		if(outbound!=null && outbound.getStackSize()>=64){
			return false;
		}
		if(inbound==null){
			return false;
		}else if(inbound.getStackSize()<1){
			return false;
		}
		return true;
	}
	/*
	 * IHOEActiveMachine
	 */
	public void produce(boolean doReal) {
		if(checkWork()){
			if(isItemValid(inbound)){
				inbound.decrement(1);
				inbound=StackUtility.verify(inbound);
				
				if(outbound==null){
					outbound = UniversalStackUtils.convert(new ItemStack(ItemLoader.emptyhvlc,1));
				}else{
					outbound.increment(1);
				}
				//Smoke
				chimneyAccumulatedSmoke+=this.ticksRequared*5;
				if(chimneyAccumulatedSmoke>SMOKE_LIMIT){
					chimneyAccumulatedSmoke=SMOKE_LIMIT;
				}
			}else{
				if(outbound==null){
					IUniversalStack temp = inbound;
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
	private boolean isItemValid(IUniversalStack stack) {
		Object pre = stack.getItem();
		if(pre instanceof HiVolumeLiquidCell){
			HiVolumeLiquidCell cell = (HiVolumeLiquidCell)pre;
			return cell.getFluid(stack).getState()==HOEFluidState.GAS;
		}else if(pre instanceof HOEFluid){
			return ((HOEFluid) pre).getState()==HOEFluidState.GAS;
		}else{
			return false;
		}
	}

	//==========================
	
	
	
	ItemStackTransferTuple transferTuple = new ItemStackTransferTuple();
	
	public ItemStack tryToPutItemsIn(ItemStack source){
		return tryToPutItemsIn(source, null);
	}

	@Override
	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter) {
		//There is no hope if this is not an item. But really, this is a mess...
		if(inbound instanceof UniversalItemStack || inbound==null){
			transferTuple.fill((ItemStack) UniversalStackUtils.getSafeProxy(inbound), source);
			source=StackUtility.tryToPutIn(transferTuple,false,filter);
			inbound=UniversalStackUtils.setSafeProxy(inbound,transferTuple.retr1());
			this.markDirty();
		}
		return source;
	}
	public ItemStack tryToGetItemsOut(ItemStack target){
		return tryToGetItemsOut(target, null);
	}

	@Override
	public ItemStack tryToGetItemsOut(ItemStack target, ItemStack filter) {
		//There is no hope if this is not an item. But really, this is a mess...
		if(outbound instanceof UniversalItemStack){
			transferTuple.fill(target, (ItemStack) outbound.getProxy());
			target = StackUtility.tryToGetOut(transferTuple,filter);
			outbound.setProxy(StackUtility.verify(transferTuple.retr2()));
			outbound=outbound.verifyProxy();
			this.markDirty();
		}
		return target;
	}
	
	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
		inbound=StackUtility.readUniversalStackFromNBT(nbt, "initem");
		outbound=StackUtility.readUniversalStackFromNBT(nbt, "outitem");		
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
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

	//HACK: Fluids are discarded
	@Override
	public FluidStack tryToPutFluidsIn(FluidStack fluid) {
		return fluid;
	}

	@Override
	public FluidStack tryToPutFluidsIn(FluidStack fluid, FluidStack filter) {
		return fluid;
	}
	
	
	
}
