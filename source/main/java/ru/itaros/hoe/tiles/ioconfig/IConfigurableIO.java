package ru.itaros.hoe.tiles.ioconfig;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/*
 * User-configured machine ports. 
 * They are not suitable for automatic port adjustments.
 */
public interface IConfigurableIO {

	/*
	 * It is assumed there are 5 port infos:
	 * DOWN, UP, N, S(FACE), W, E.
	 * Can return null if there is no port at all
	 */
	public PortInfo[] getPorts();
	
	/*
	 * Performs port switching returning previous port slot provider
	 */
	public ItemStack setPortToNothing(int side);

	public ItemStack setPort(int side, PortType type);

	
	public void writeIOPortsNBT(NBTTagCompound stackTagCompound);
	public void readIOPortsNBT(NBTTagCompound stackTagCompound);
}
