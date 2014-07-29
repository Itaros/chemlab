package ru.itaros.chemlab.addon.bc.builder;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class HOENBTManifold {
	
	public enum ManifoldFilter{
		BCSCHEMATICS
	}
	
	private NBTTagCompound typefactory;
	
	private NBTTagCompound inventory;
	private NBTTagCompound configuration;
	
	private NBTTagCompound syndication;
	
	public NBTTagCompound holdInvetory(){
		if(inventory==null){
			inventory=new NBTTagCompound();
		}
		return inventory;
	}
	public NBTTagCompound holdConfiguration(){
		if(configuration==null){
			configuration=new NBTTagCompound();
		}
		return configuration;
	}	
	public NBTTagCompound holdTypeFactoryData(){
		if(typefactory==null){
			typefactory=new NBTTagCompound();
		}
		return typefactory;
	}
	public NBTTagCompound holdSyndication() {
		if(syndication==null){
			syndication=new NBTTagCompound();
		}
		return syndication;
	}	
		
	public void filter(ManifoldFilter filter, boolean full){
		switch(filter){
		case BCSCHEMATICS:
			inventory=null;
			if(full){
				syndication=null;
			}
			break;
		}
	}
	
	private static final String PREFIX="mfld_";
	
	public void mergeInto(NBTTagCompound nbt){
		if(typefactory!=null){
			nbt.setTag(PREFIX+"typefactory", typefactory);
		}
		if(configuration!=null){
			nbt.setTag(PREFIX+"configuration", configuration);
		}
		if(inventory!=null){
			nbt.setTag(PREFIX+"inventory", inventory);
		}
		if(syndication!=null){
			nbt.setTag(PREFIX+"syndication", syndication);
		}
	}
	
	public void extractFrom(NBTTagCompound nbt){
		NBTBase temp;
		
		temp =  nbt.getTag(PREFIX+"typefactory");
		if(temp!=null){
			typefactory=(NBTTagCompound) temp;
		}
		temp =  nbt.getTag(PREFIX+"configuration");
		if(temp!=null){
			configuration=(NBTTagCompound) temp;
		}		
		temp =  nbt.getTag(PREFIX+"inventory");
		if(temp!=null){
			inventory=(NBTTagCompound) temp;
		}	
		temp =  nbt.getTag(PREFIX+"syndication");
		if(temp!=null){
			syndication=(NBTTagCompound) temp;
		}			
		
	}
	
	public static HOENBTManifold deploy(NBTTagCompound nbt) {
		HOENBTManifold manifold = new HOENBTManifold();
		manifold.extractFrom(nbt);
		return manifold;
	}

	
	
}
