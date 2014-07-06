package ru.itaros.api.hoe.minecraft;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTSerializable {

	public void readNBT(NBTTagCompound nbt);
	public void writeNBT(NBTTagCompound nbt);
	
}
