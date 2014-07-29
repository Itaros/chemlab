package ru.itaros.hoe.utils;

import net.minecraft.nbt.NBTTagCompound;

public class NBTUtility {

	
	public static void addFlag(NBTTagCompound nbt, String tag, int flag){
		int origin = nbt.getInteger(tag);	
		origin|=flag;
		nbt.setInteger(tag, origin);	
	}
	
	
}
