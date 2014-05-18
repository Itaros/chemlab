package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;
import ru.itaros.toolkit.hoe.facilities.fluid.containment.HOEFluidDepot;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.data.facilities.IHasLiquidStorage;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.FluidUtility;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackTransferTuple;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;

public class HVLCFillerData extends HOEMachineData implements IHasLiquidStorage {

	/*
	 * Reflection autocaster
	 */
	public HVLCFillerData(HOEData parent){
		super(parent);
	}	
	
	public HVLCFillerData(){
		super();
	}
	
	
	
	public FluidStack tryToPutIn(FluidStack source){
		if(source==null){
			return source;
		}
		int accepted = fluidDepot.inject(HOEFluidLoader.water_natural, source.amount, true);
		source.amount-=accepted;
		return source;
		//return FluidUtility.tryToPutIn(target, source, cap);
	}
	
	StackTransferTuple transferTuple = new StackTransferTuple();
	
	public ItemStack tryToPutIn(ItemStack source){
		transferTuple.fill(exemplar_cell_in, source);
		source=StackUtility.tryToPutIn(transferTuple);
		exemplar_cell_in=transferTuple.retr1();
		return source;
	}
	public ItemStack tryToGetOut(ItemStack target){
		transferTuple.fill(target, exemplar_cell_out);
		target = StackUtility.tryToGetOut(transferTuple);
		exemplar_cell_out=transferTuple.retr2();
		return target;
	}
	
	private ItemStack exemplar_cell_in;
	
	public final ItemStack getExemplar_cell_in() {
		return exemplar_cell_in;
	}

	public final ItemStack getExemplar_cell_out() {
		return exemplar_cell_out;
	}

	private ItemStack exemplar_cell_out;
	
	public static final int DEPOT_CAPACITY=10000;//mb
	HOEFluidDepot fluidDepot = new HOEFluidDepot(DEPOT_CAPACITY);
	
	@Override
	public HOEFluidDepot getFluidDepot() {
		return fluidDepot;
	}

	@Override
	public void configureDepot() {
		fluidDepot.configureDepot(HOEFluidLoader.water_natural);
	}

	@Override
	public boolean isDepotReconfigurable() {
		return false;
		//return fluidDepot.isDeportReconfigurable();
	}

	
	public boolean isThereSpareCell(){
		if(exemplar_cell_in==null){return false;}
		if(exemplar_cell_in.stackSize>0){
			return true;
		}else{return false;}
	}
	public int getCellsCount() {
		if(exemplar_cell_out==null){return 0;}
		return exemplar_cell_out.stackSize;
	}

	public boolean decrementEmptyCells(){
		if(isThereSpareCell()){
			exemplar_cell_in.stackSize--;
			return true;
		}else{
			return false;
		}
	}
	
	public void incrementCellsCount() {
		if(exemplar_cell_out==null){
			exemplar_cell_out = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.water_natural));
		}
		exemplar_cell_out.stackSize++;
	}

	@Override
	public void sync() {
		super.sync();
		
		HVLCFillerData childd=(HVLCFillerData) child;
		
		childd.exemplar_cell_in=StackUtility.syncItemStacks(childd.exemplar_cell_in, exemplar_cell_in);
		childd.exemplar_cell_out=StackUtility.syncItemStacks(childd.exemplar_cell_out, exemplar_cell_out);

		//Should I do that?
		childd.fluidDepot=fluidDepot;
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		super.readNBT(nbt);
		
		exemplar_cell_in=StackUtility.readItemStackFromNBT(nbt, "in");
		exemplar_cell_out=StackUtility.readItemStackFromNBT(nbt, "out");
		
		fluidDepot.readFromNBT(nbt,"fluiddepot");
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		super.writeNBT(nbt);
		
		StackUtility.writeItemStackToNBT(exemplar_cell_in, nbt, "in");
		StackUtility.writeItemStackToNBT(exemplar_cell_out, nbt, "out");
		
		fluidDepot.writeToNBT(nbt,"fluiddepot");
	}		
	
	
	
	
	
	

	
	
}
