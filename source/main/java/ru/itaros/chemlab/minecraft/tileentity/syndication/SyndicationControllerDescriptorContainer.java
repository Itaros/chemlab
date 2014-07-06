package ru.itaros.chemlab.minecraft.tileentity.syndication;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping.PipingMode;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.EnumUtility;

public final class SyndicationControllerDescriptorContainer {

	private ISyndicationPiping father;
	public SyndicationControllerDescriptorContainer(ISyndicationPiping father){
		this.father=father;
	}
	
	private PipingMode mode = PipingMode.DISABLED;
	
	public PipingMode getMode() {
		return mode;
	}

	public void setMode(PipingMode mode) {
		this.mode = mode;
	}



	private int heat;
	
	public int getHeat() {
		return heat;
	}

	public void setHeat(int heat) {
		this.heat = heat;
	}
	
	

	private int controller_x, controller_y,controller_z;
	private boolean isSet=false;


	public void readFromNBT(NBTTagCompound nbt) {
		isSet=nbt.getBoolean("s_isset");
		controller_x=nbt.getInteger("s_cx");
		controller_y=nbt.getInteger("s_cy");
		controller_z=nbt.getInteger("s_cz");
		heat=nbt.getInteger("s_heat");
		
		mode=EnumUtility.readEnumValueImplicitly(PipingMode.class, nbt, "s_mode");
	}

	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("s_isset", isSet);
		nbt.setInteger("s_cx", controller_x);
		nbt.setInteger("s_cy", controller_y);
		nbt.setInteger("s_cz", controller_z);
		nbt.setInteger("s_heat", heat);		
		
		EnumUtility.writeEnumValueImplicitly(nbt, mode, "s_mode");
	}
	
	public void setController(SyndicationHubTileEntity controller){
		if(controller!=null){
			controller_x=controller.xCoord;
			controller_y=controller.yCoord;
			controller_z=controller.zCoord;
			isSet=true;
		}else{
			isSet=false;
			controller_x=0;
			controller_y=0;
			controller_z=0;
		}
	}
	
	public int getController_x() {
		return controller_x;
	}

	public int getController_y() {
		return controller_y;
	}

	public int getController_z() {
		return controller_z;
	}	
	
	public SyndicationHubTileEntity getController(World world){
		if(isSet){
			TileEntity te = world.getTileEntity(controller_x, controller_y, controller_z);
			if(te instanceof SyndicationHubTileEntity){
				SyndicationHubTileEntity sh = (SyndicationHubTileEntity)te;
				//if(sh.getIdentityHash()==identityHash){
				return sh;
				//}else{
				//	return null;
				//}
			}else{
				return null;
			}
		}else{
			return null;
		}
	}



	public void setClear() {
		this.setHeat(0);
		this.setMode(PipingMode.DISABLED);
		father.setBlockMetadata();
	}

	public void setAdHoc() {
		this.setHeat(0);
		this.setMode(PipingMode.ADHOCGATE);
		father.setBlockMetadata();	
	}

	
	
	
	
}
