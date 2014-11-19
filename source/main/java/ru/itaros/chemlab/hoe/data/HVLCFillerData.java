//package ru.itaros.chemlab.hoe.data;
//
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraftforge.fluids.FluidStack;
//import ru.itaros.api.hoe.internal.HOEData;
//import ru.itaros.chemlab.items.HiVolumeLiquidCell;
//import ru.itaros.chemlab.loader.HOEFluidLoader;
//import ru.itaros.hoe.data.IHasLiquidStorage;
//import ru.itaros.hoe.data.ISynchroportItems;
//import ru.itaros.hoe.data.machines.HOEMachineData;
//import ru.itaros.hoe.fluid.HOEFluidDepot;
//import ru.itaros.hoe.itemhandling.IUniversalStack;
//import ru.itaros.hoe.itemhandling.UniversalItemStack;
//import ru.itaros.hoe.itemhandling.UniversalStackUtils;
//import ru.itaros.hoe.utils.ItemStackTransferTuple;
//import ru.itaros.hoe.utils.StackUtility;
//
//public class HVLCFillerData extends HOEMachineData implements IHasLiquidStorage, ISynchroportItems {
//
//	/*
//	 * Reflection autocaster
//	 */
//	public HVLCFillerData(HOEData parent){
//		super(parent);
//	}	
//	
//	public HVLCFillerData(){
//		super();
//	}
//	
//	
//	
//	public FluidStack tryToPutIn(FluidStack source){
//		if(source==null){
//			return source;
//		}
//		int accepted = fluidDepot.inject(HOEFluidLoader.water_natural, source.amount, true);
//		source.amount-=accepted;
//		return source;
//		//return FluidUtility.tryToPutIn(target, source, cap);
//	}
//	
//	ItemStackTransferTuple transferTuple = new ItemStackTransferTuple();
//	
//	public ItemStack tryToPutItemsIn(ItemStack source){
//		return tryToPutItemsIn(source, null);
//	}
//
//	@Override
//	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter) {
//		//There is no hope if this is not an item. But really, this is a mess...
//		if(inbound instanceof UniversalItemStack || inbound==null){
//			transferTuple.fill((ItemStack) UniversalStackUtils.getSafeProxy(inbound), source);
//			source=StackUtility.tryToPutIn(transferTuple,false,filter);
//			inbound=UniversalStackUtils.setSafeProxy(inbound,transferTuple.retr1());
//			this.markDirty();
//		}
//		return source;
//	}
//	public ItemStack tryToGetItemsOut(ItemStack target){
//		return tryToGetItemsOut(target, null);
//	}
//
//	@Override
//	public ItemStack tryToGetItemsOut(ItemStack target, ItemStack filter) {
//		//There is no hope if this is not an item. But really, this is a mess...
//		if(outbound instanceof UniversalItemStack){
//			transferTuple.fill(target, (ItemStack) outbound.getProxy());
//			target = StackUtility.tryToGetOut(transferTuple,filter);
//			outbound.setProxy(StackUtility.verify(transferTuple.retr2()));
//			outbound=outbound.verifyProxy();
//			this.markDirty();
//		}
//		return target;
//	}
//	
//	private IUniversalStack inbound,outbound;
//	public IUniversalStack get_cell_in(){
//		return inbound;
//	}
//	public IUniversalStack get_cell_out(){
//		return outbound;
//	}
//	
//	public static final int DEPOT_CAPACITY=10000;//mb
//	HOEFluidDepot fluidDepot = new HOEFluidDepot(DEPOT_CAPACITY);
//	
//	@Override
//	public HOEFluidDepot getFluidDepot() {
//		return fluidDepot;
//	}
//
//	@Override
//	public void configureDepot() {
//		fluidDepot.configureDepot(HOEFluidLoader.water_natural);
//	}
//
//	@Override
//	public boolean isDepotReconfigurable() {
//		return false;
//		//return fluidDepot.isDeportReconfigurable();
//	}
//
//	
//	public boolean isThereSpareCell(){
//		if(inbound==null){return false;}
//		if(inbound.getStackSize()>0){
//			return true;
//		}else{return false;}
//	}
//	public int getCellsCount() {
//		if(outbound==null){return 0;}
//		return outbound.getStackSize();
//	}
//
//	public boolean decrementEmptyCells(){
//		if(isThereSpareCell()){
//			inbound.decrement(1);
//			return true;
//		}else{
//			return false;
//		}
//	}
//	
//	public void incrementCellsCount() {
//		if(outbound==null){
//			outbound = UniversalStackUtils.convert(new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.water_natural)));
//		}
//		outbound.increment(1);
//	}
//
//	@Override
//	public void sync() {
//		super.sync();
//		
//		HVLCFillerData childd=(HVLCFillerData) child;
//		
//		childd.inbound=StackUtility.syncUniversalStacks(childd.inbound, inbound);
//		childd.outbound=StackUtility.syncUniversalStacks(childd.outbound, outbound);
//
//		//Should I do that?
//		childd.fluidDepot=fluidDepot;
//	}
//
//	@Override
//	public void readNBT(NBTTagCompound nbt) {
//		super.readNBT(nbt);
//		
//		fluidDepot.readFromNBT(nbt,"fluiddepot");
//	}
//
//	@Override
//	public void writeNBT(NBTTagCompound nbt) {
//		super.writeNBT(nbt);
//		
//		fluidDepot.writeToNBT(nbt,"fluiddepot");
//	}
//
//	@Override
//	protected void readInventoryNBT(NBTTagCompound nbt) {
//		super.readInventoryNBT(nbt);
//		inbound=StackUtility.readUniversalStackFromNBT(nbt, "in");
//		outbound=StackUtility.readUniversalStackFromNBT(nbt, "out");
//	}
//
//	@Override
//	protected void writeInventoryNBT(NBTTagCompound nbt) {
//		super.writeInventoryNBT(nbt);
//		StackUtility.writeItemStackToNBT(inbound, nbt, "in");
//		StackUtility.writeItemStackToNBT(outbound, nbt, "out");
//	}
//
//	
//	//Synchromanager(visual inventory sync)
//	protected boolean isDirty=false;
//	@Override
//	public void markDirty() {
//		isDirty=true;
//	}
//	@Override
//	public boolean pollDirty() {
//		boolean cache = isDirty;
//		isDirty=false;
//		return cache;
//	}	
//	
//	
//	
//	
//	
//	
//
//	
//	
//}
