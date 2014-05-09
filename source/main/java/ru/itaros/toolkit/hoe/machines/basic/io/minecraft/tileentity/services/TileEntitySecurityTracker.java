package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySecurityTracker {

	public final static String NOOWNER = "~NO OWNER~";
	
	private String owner=NOOWNER;
	
	
	public void setOwnerIfNotSet(EntityPlayer entplayer) {
		if(owner.equals(NOOWNER)){
			setOwner(entplayer);
		}
	}	
	public void setOwner(EntityPlayer player){
		owner = player.getDisplayName();
	}
	public String getOwnerName(){
		return owner;
	}
	
	
	
	public void writeToNBT(NBTTagCompound nbt, String tag){
		NBTTagCompound n = new NBTTagCompound();
		
		n.setString("owner", owner);
		
		
		nbt.setTag(tag, n);
	}
	
	public void readFromNBT(NBTTagCompound nbt, String tag){
		NBTTagCompound n = (NBTTagCompound) nbt.getTag(tag);
		
		owner = n.getString("owner");
		
		
	}

	
	
}
