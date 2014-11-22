package ru.itaros.hoe.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class HOEFluidStack {

	public HOEFluid type;
	public int stackSize;
	
	public HOEFluidStack(HOEFluid type, int stackSize){
		this.type=type;
		this.stackSize=stackSize;
	}

	private HOEFluidStack() {
	}

	public HOEFluidStack(FluidStack proxy) {
		stackSize = proxy.amount;
		type = FluidToHOE.get(proxy.getFluid());
	}

	public HOEFluid getFluid() {
		return type;
	}

	public void writeNBT(NBTTagCompound nbt) {
		String name = type.getUnlocalizedName();
		
		nbt.setString("type", name);
		nbt.setInteger("size", stackSize);
		
	}
	
	public void readNBT(NBTTagCompound nbt){
		String name = nbt.getString("type");
		type = HOEFluid.getFluidRegistry().pop(name);
		stackSize=nbt.getInteger("size");
	}

	public static HOEFluidStack createFromNBT(NBTTagCompound tnbt) {
		HOEFluidStack stack = new HOEFluidStack();
		stack.readNBT(tnbt);
		return stack;
	}

	public int getMaxStackSize() {
		return type.getMaxStack();
	}

	public HOEFluidStack copy() {
		HOEFluidStack newstack = new HOEFluidStack(type,stackSize);
		return newstack;
	}

	public String getUnlocalizedName() {
		return type.getUnlocalizedName();
	}

	public static HOEFluidStack loadFluidStackFromNBT(NBTTagCompound nbt) {
		HOEFluidStack fluidstack = new HOEFluidStack();
        fluidstack.readFromNBT(nbt);
        return fluidstack.getFluid() != null ? fluidstack : null;
	}

	public void readFromNBT(NBTTagCompound nbt) {
		type=HOEFluid.getFluidRegistry().pop(nbt.getString("id"));
		stackSize=nbt.getInteger("amount");
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		if(type!=null){
			nbt.setString("id", type.getUnlocalizedName());
			nbt.setInteger("amount", stackSize);
		}
	}
	
}
