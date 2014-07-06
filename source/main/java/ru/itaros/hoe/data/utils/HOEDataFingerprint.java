package ru.itaros.hoe.data.utils;

import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class HOEDataFingerprint {

	
	int wid,x,y,z;
	HOEMachineIO expected_io;
	
	
	public final int getWid() {
		return wid;
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public final int getZ() {
		return z;
	}

	private HOEDataFingerprint(){
		
	}
	
	public HOEDataFingerprint(MachineTileEntity host){
		//Gathering data
		wid = host.getWorldObj().provider.dimensionId;
		x = host.xCoord;
		y = host.yCoord;
		z = host.zCoord;
		
		expected_io = host.getSuperIO();
	}
	

	
	public NBTTagCompound getNBT(){
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("wid", wid);
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		
		nbt.setString("io", expected_io.getClass().getSimpleName());
		return nbt;
	}
	
	public HOEDataFingerprint setNBT(NBTTagCompound nbt){
		wid = nbt.getInteger("wid");
		x = nbt.getInteger("x");
		y = nbt.getInteger("y");
		z = nbt.getInteger("z");
		
		String ioname = nbt.getString("io");
		if(ioname!=null && ioname!=""){
			expected_io=(HOEMachineIO) ChemLab.getIOCollection().getHOEIO(ioname);
		}
		return this;
	}
	

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof HOEDataFingerprint){
			HOEDataFingerprint other = (HOEDataFingerprint)obj;
			if(wid==other.wid){
				if(x==other.x){
					if(y==other.y){
						if(z==other.z){
							//TE data validated. Validating IOs
							if(expected_io==other.expected_io){
								if(expected_io==null || other.expected_io==null){
									return false;//Null IOs can't be the same
								}
								return true;
							}
						}
					}
				}
			}
			return false;
		}else{
			return super.equals(obj);
		}
	}



	public static HOEDataFingerprint generateFromNBT(NBTTagCompound nbt) {
		return new HOEDataFingerprint().setNBT(nbt);
	}
	
	
	
	
	
}
