package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.api.hoe.heat.Heat;
import ru.itaros.api.hoe.heat.IHeatContainer;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.data.IHOEMultiInventoryMachine;
import ru.itaros.hoe.data.ISynchroportFluids;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalFluidStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.itemhandling.UniversalStackFactory;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.utils.FluidStackTransferTuple;
import ru.itaros.hoe.utils.ItemStackTransferTuple;
import ru.itaros.hoe.utils.StackUtility;

public class HVLCFillerData extends HOEMachineData implements IHOEMultiInventoryMachine, IHeatContainer, ISynchroportItems, ISynchroportFluids{

	/*
	 * Reflection autocaster
	 */
	public HVLCFillerData(HOEData parent){
		super(parent);
	}	
	
	public HVLCFillerData(){
		super();
	}
	
	ItemStackTransferTuple transferTuple = new ItemStackTransferTuple();
	FluidStackTransferTuple transferFluidTuple = new FluidStackTransferTuple();	
	
	public ItemStack tryToPutItemsIn(ItemStack source){
		return tryToPutItemsIn(source, null);
	}

	@Override
	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter) {
		//There is no hope if this is not an item. But really, this is a mess...
		if(inbound[SLOT_CELLS] instanceof UniversalItemStack || inbound[SLOT_CELLS]==null){
			transferTuple.fill((ItemStack) UniversalStackUtils.getSafeProxy(inbound[SLOT_CELLS]), source);
			source=StackUtility.tryToPutIn(transferTuple,false,filter);
			inbound[SLOT_CELLS]=UniversalStackUtils.setSafeProxy(inbound[SLOT_CELLS],transferTuple.retr1());
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
		if(outbound[SLOT_CELLS] instanceof UniversalItemStack){
			transferTuple.fill(target, (ItemStack) outbound[SLOT_CELLS].getProxy());
			target = StackUtility.tryToGetOut(transferTuple,filter);
			outbound[SLOT_CELLS].setProxy(StackUtility.verify(transferTuple.retr2()));
			outbound[SLOT_CELLS]=outbound[SLOT_CELLS].verifyProxy();
			this.markDirty();
		}
		return target;
	}
	
	public final static int SLOT_CELLS = 0;
	public final static int SLOT_FLUID = 1;
	
	private IUniversalStack[] inbound=new IUniversalStack[2],outbound=new IUniversalStack[2];

	@Override
	public boolean sync() {
		if(super.sync()){
			HVLCFillerData childd=(HVLCFillerData) child;
			
			childd.inbound=StackUtility.syncUniversalStacks(childd.inbound, inbound);
			childd.outbound=StackUtility.syncUniversalStacks(childd.outbound, outbound);
			
			return true;
		}
		return false;
	}

	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
		inbound=StackUtility.readItemStacksFromNBT(inbound,nbt, "in");
		outbound=StackUtility.readItemStacksFromNBT(outbound,nbt, "out");
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
		StackUtility.writeItemStacksToNBT(inbound, nbt, "in");
		StackUtility.writeItemStacksToNBT(outbound, nbt, "out");
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
	
	
	
	@Override
	public FluidStack tryToPutFluidsIn(FluidStack source) {
		return tryToPutFluidsIn(source, null);
	}
	@Override
	public FluidStack tryToPutFluidsIn(FluidStack source, FluidStack filter) {
		if(inbound[SLOT_FLUID] instanceof UniversalFluidStack || inbound[SLOT_FLUID]==null){
			transferFluidTuple.fill((HOEFluidStack) UniversalStackUtils.getSafeProxy(inbound[SLOT_FLUID]), source);
			int max = 5000;
			source=StackUtility.tryToPutIn(transferFluidTuple,filter,max);
			inbound[SLOT_FLUID]=UniversalStackUtils.setSafeProxy(inbound[SLOT_FLUID],transferFluidTuple.retr1());
			this.markDirty();			
		}
		return source;
	}

	@Override
	public void produce(boolean doReal) {
		IUniversalStack in = inbound[SLOT_CELLS];
		IUniversalStack infl = inbound[SLOT_FLUID];
		IUniversalStack out = outbound[SLOT_CELLS];
		
		if((infl!=null && infl.getProxy()!=null && infl.getStackSize()>=1000) && (in!=null && in.getProxy()!=null)){
			if(in.getStackSize()>0){
				if(out!=null && out.getProxy()!=null && out.getStackSize()>=64){
					return;
				}
				
				in.decrement(1);
				in=StackUtility.verify(in);
				
				addFilledHVLC((HOEFluidStack)infl.getProxy());
				
			}
		}
		
	}

	private void addFilledHVLC(HOEFluidStack hoeFluidStack) {
		
		HOEFluid fluid = hoeFluidStack.type;
		
		if(outbound[SLOT_CELLS]==null){
			
			ItemStack stack = HiVolumeLiquidCell.getByFluid(fluid);
			outbound[SLOT_CELLS] = UniversalStackFactory.wrap(stack);
			
		}else{
			outbound[SLOT_CELLS].increment(1);
		}
		
	}

	@Override
	public boolean checkStorage() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasWork() {
		if(inbound[SLOT_FLUID]!=null && inbound[SLOT_FLUID].getProxy()!=null){
			return inbound[SLOT_FLUID].getStackSize()>=1000;
		}else{
			return false;
		}
	}

	@Override
	public FluidStack tryToGetFluidsOut(FluidStack fluid) {
		return null;
	}

	@Override
	public FluidStack tryToGetFluidsOut(FluidStack fluid, FluidStack filter) {
		return null;
	}

	private Heat heat = new Heat(100L);
	
	@Override
	public Heat getHeat() {
		return heat;
	}

	@Override
	public void updateDistribution() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getMeltdownPoint() {
		return io.getMeltdownTemperature();
	}


	public IUniversalStack get_in(int i){
		return i==0?inbound[SLOT_CELLS]:inbound[SLOT_FLUID];
	}
	public IUniversalStack get_out(int i){
		return i==0?outbound[SLOT_CELLS]:outbound[SLOT_FLUID];
	}	
	public IHOEMultiInventoryMachine set_in(int i, IUniversalStack stack){
		if(i==0){
			inbound[SLOT_CELLS]=stack;
		}else{
			inbound[SLOT_FLUID]=stack;
		}
		return this;
	}
	public IHOEMultiInventoryMachine set_out(int i, IUniversalStack stack){
		if(i==0){
			outbound[SLOT_CELLS]=stack;
		}else{
			outbound[SLOT_FLUID]=stack;
		}
		return this;
	}
	public IUniversalStack[] get_in(){
		return inbound;
	}
	public IUniversalStack[] get_out(){
		return outbound;
	}		
	
	
	
}
