package ru.itaros.hoe.utils;

import net.minecraft.nbt.NBTTagCompound;

public class EnumUtility {

	
	public static void writeEnumValueImplicitly(NBTTagCompound nbt, Enum<?> e, String tag){
		int id = e.ordinal();
		nbt.setInteger(tag, id);
	}
	
	public static <T extends Enum<T>> T readEnumValueImplicitly(
		Class<T> prototype,
		NBTTagCompound nbt,
		String tag
	){
		int id = nbt.getInteger(tag);
		T[] constants = prototype.getEnumConstants();
		if(id>=constants.length || id<0){return null;}
		T result = constants[id];
		return result;
	}
	
}
