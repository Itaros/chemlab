package ru.itaros.hoe.itemhandling;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/*
 * HOE Universal stack to deploy HOEFluidStacks and ItemStacks
 * as interchangable entity.
 * 
 * Universal Stacks require underlying stack to be wrapped. 
 * If it goes null screw you
 */
public interface IUniversalStack {

	public int getMaxStackSize();
	
	public int getStackSize();
	public void setStackSize(int newSize);
	
	public IUniversalStack copy();
	
	public Object getItem();
	
	public int getItemDamage();
	public void setItemDamage(int damage);
	public int getMaxDamage();
	
	public String getUnlocalizedName();

	public void decrement(int amount);
	public void increment(int amount);

	public boolean isItemEqual(IUniversalStack filter);

	public void writeToNBT(NBTTagCompound nbt);
	Object readProxyFromNBT(NBTTagCompound innerNbt);

	public Object getProxy();

	public IUniversalStack setProxy(Object proxy);

	/*
	 * Return folume for mixture. Ideally, blocks should return 1F, items 1F/9F, fluids 1F;
	 */
	public float getVolume();

	public String getLocalizedName();
		
	/*
	 * This method tests if proxy is healthy(not null). If not it returns null instead of self
	 */
	public IUniversalStack verifyProxy();
}
