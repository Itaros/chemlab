package ru.itaros.toolkit.hoe.facilities.fluid;

import net.minecraft.nbt.NBTTagCompound;

public class HOEFluidStack {

	public HOEFluid type;
	public int stackSize;
	
	public HOEFluidStack(HOEFluid type, int stackSize){
		this.type=type;
		this.stackSize=stackSize;
	}

	private HOEFluidStack() {
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
	
}
