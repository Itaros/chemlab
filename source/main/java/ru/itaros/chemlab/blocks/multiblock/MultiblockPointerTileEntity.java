package ru.itaros.chemlab.blocks.multiblock;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MultiblockPointerTileEntity extends TileEntity {

	public MultiblockPointerTileEntity(){
	}
	public MultiblockPointerTileEntity(int x, int y, int z){
		host_x=x;
		host_y=y;
		host_z=z;
	}	
	
	private int host_x,host_y,host_z;
	//private World host_w;//It is assumed multiblocks can't traverse through worlds

	@Override
	public void readFromNBT(NBTTagCompound p_145839_1_) {
		// TODO Auto-generated method stub
		super.readFromNBT(p_145839_1_);
	}

	@Override
	public void writeToNBT(NBTTagCompound p_145841_1_) {
		// TODO Auto-generated method stub
		super.writeToNBT(p_145841_1_);
	}
	@Override
	public boolean shouldRefresh(Block oldBlock, Block newBlock, int oldMeta,
			int newMeta, World world, int x, int y, int z) {
		boolean composite = oldBlock!=newBlock&oldMeta!=newMeta;
		if(composite){
			callDecomposition();
		}
		return composite;
	}
	
	private void callDecomposition() {
		TileEntity te = this.getWorldObj().getTileEntity(host_x, host_y, host_z);
		if(te instanceof IMultiblockController){
			IMultiblockController imc = (IMultiblockController)te;
			imc.notifyDecomposition();
		}
	}
	

}
