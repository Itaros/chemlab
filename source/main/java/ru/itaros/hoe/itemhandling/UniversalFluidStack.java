package ru.itaros.hoe.itemhandling;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluidStack;

public class UniversalFluidStack implements IUniversalStack {

	private HOEFluidStack proxy;

	UniversalFluidStack(HOEFluidStack proxy){
		this.proxy=proxy;
	}
	
	public UniversalFluidStack(HOEFluid fluid) {
		proxy = new HOEFluidStack(fluid,1000);
	}

	@Override
	public int getMaxStackSize() {
		return proxy.getMaxStackSize();
	}

	@Override
	public int getStackSize() {
		return proxy.stackSize;
	}

	@Override
	public void setStackSize(int newSize) {
		proxy.stackSize=newSize;
	}

	@Override
	public IUniversalStack copy() {
		return new UniversalFluidStack(proxy.copy());
	}	

	@Override
	public Object getItem() {
		return proxy.getFluid();
	}

	@Override
	public int getItemDamage() {
		return 0;//HOE Fluids don't have meta
	}	
	
	@Override
	public String getUnlocalizedName() {
		return proxy.getUnlocalizedName();
	}

	@Override
	public void setItemDamage(int damage) {
		return;//You can't set meta for fluids
	}

	@Override
	public int getMaxDamage() {
		return 0;//You can't set meta for fluids
	}

	@Override
	public void decrement(int amount) {
		proxy.stackSize-=amount;
	}

	@Override
	public void increment(int amount) {
		proxy.stackSize+=amount;
	}

	@Override
	public boolean isItemEqual(IUniversalStack filter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		NBTTagCompound uistack = new NBTTagCompound();
		uistack.setString("factory", this.getClass().getSimpleName());
		if(proxy!=null){
			proxy.writeToNBT(uistack);
		}
		nbt.setTag("inner", uistack);
	}

	@Override
	public Object readProxyFromNBT(NBTTagCompound innerNbt) {
		proxy = HOEFluidStack.loadFluidStackFromNBT(innerNbt);
		return proxy;
	}

	@Override
	public Object getProxy() {
		return proxy;
	}

	@Override
	public IUniversalStack setProxy(Object proxy) {
		this.proxy=(HOEFluidStack) proxy;
		return this;
	}

	@Override
	public float getVolume() {
		return getVolume(this.getStackSize());
	}	
	public static float getVolume(float amount){
		return 1F*amount;
	}
}
