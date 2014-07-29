package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services;

import java.util.UUID;

import scala.Console;

import com.mojang.authlib.GameProfile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySecurityTracker {
	
	public final static String NOOWNER = "~NO OWNER~";
	
	private String ownerName=NOOWNER;
	private UUID owner=null;
	private boolean isOwned=false;
	
	public void setOwnerIfNotSet(EntityPlayer entplayer) {
		if(!isOwned){
			setOwner(entplayer.getGameProfile());
		}
	}	
	public void setOwner(GameProfile profile){
		ownerName = profile.getName();
		owner = profile.getId();
	}
	public String getOwnerName(){
		return ownerName;
	}
	
	
	
	public void writeToNBT(NBTTagCompound nbt, String tag){
		NBTTagCompound n = new NBTTagCompound();
		
		n.setBoolean("isOwned", isOwned);
			if(isOwned){
			n.setString("ownerName", ownerName);
			if(owner!=null){
				n.setString("owner", owner.toString());
			}else{
				n.setString("owner", NOOWNER);
			}
		}
		
		nbt.setTag(tag, n);
	}
	
	public void readFromNBT(NBTTagCompound nbt, String tag){
		NBTTagCompound n = (NBTTagCompound) nbt.getTag(tag);
		if(n==null){return;}//There is no security data
		try{
			isOwned = n.getBoolean("isOwned");
			if(isOwned){
				ownerName = n.getString("ownerName");
				owner = UUID.fromString(n.getString("owner"));
			}
		}catch(IllegalArgumentException e){
			System.out.println("Incorrect UUID Data... Invalidating!");
			ownerName = NOOWNER;
			owner = null;
		}
		
	}

	
	
}
