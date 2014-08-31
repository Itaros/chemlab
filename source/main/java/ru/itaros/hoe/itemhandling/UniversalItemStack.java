package ru.itaros.hoe.itemhandling;

import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class UniversalItemStack implements IUniversalStack {

	private ItemStack proxy;

	public UniversalItemStack(ItemStack proxy){
		this.proxy=proxy;
	}
	
	public UniversalItemStack(Item source) {
		this(new ItemStack(source));
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
		return proxy==null?null:new UniversalItemStack(proxy.copy());
	}

	@Override
	public Object getItem() {
		return proxy.getItem();
	}

	@Override
	public int getItemDamage() {
		return proxy.getItemDamage();
	}

	@Override
	public String getUnlocalizedName() {
		return proxy.getUnlocalizedName();
	}

	@Override
	public void setItemDamage(int damage) {
		proxy.setItemDamage(damage);
	}

	@Override
	public int getMaxDamage() {
		return proxy.getMaxDamage();
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
		if(proxy==null){return false;}
		//Check if types are identical
		if(filter instanceof UniversalItemStack){
			return filter.getProxy()==null?false:proxy.getItem()==filter.getItem();
		}else{
			return false;//Fluids are not items
		}
	}

	//TODO: Make Factory being enum to pass ordinal ints for faster loading
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
		proxy = ItemStack.loadItemStackFromNBT(innerNbt);
		return proxy;
	}

	@Override
	public Object getProxy() {
		return proxy;
	}

	@Override
	public IUniversalStack setProxy(Object proxy) {
		this.proxy = (ItemStack) proxy;
		return this;
	}

	@Override
	public float getVolume() {
		return getVolume(getStackSize(), getItem());
	}
	public static float getVolume(int amount, Object prototype){
		return (prototype instanceof ItemBlock)?1F*amount:(1F/9F)*amount;
	}

	public int substractVolume(float deltaV) {
		float specifiedVolume = getVolume()+deltaV;
		//Exaggerate to make sure volume containment will never overflow
		int stackS = (int) Math.ceil(specifiedVolume * ((this.getItem() instanceof ItemBlock)?1F:(1F/9F)));
		int oldSize=getStackSize();
		setStackSize(stackS);
		return oldSize-getStackSize();
	}
	
	
	
	
	
}
