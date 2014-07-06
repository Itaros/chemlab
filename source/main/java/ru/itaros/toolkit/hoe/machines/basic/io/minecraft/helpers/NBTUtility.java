package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers;

import net.minecraft.nbt.NBTTagCompound;

public class NBTUtility {

	
	public static void addFlag(NBTTagCompound nbt, String tag, int flag){
		int origin = nbt.getInteger(tag);	
		origin|=flag;
		nbt.setInteger(tag, origin);	
	}
	
	
}
