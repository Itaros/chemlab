package ru.itaros.chemlab.minecraft.tileentity;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.client.fx.ChimneySmoke;
import ru.itaros.chemlab.hoe.data.GasChimneyData;
import ru.itaros.chemlab.hoe.data.HVLCFillerData;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.StackUtility;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.helpers.TileEntityHelper;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.IHOEInventorySyncable;
import ru.itaros.toolkit.hoe.machines.interfaces.ISynchroportItems;
import ru.itaros.toolkit.hoe.machines.interfaces.tileentity.ITileEntityParticleManager;

public class GasChimneyTileEntity extends MachineTileEntity implements ISidedInventory, IHOEInventorySyncable, ITileEntityParticleManager {

	public static Random random = new Random();
	
	public GasChimneyTileEntity(){
		super();
	}
	
	int synchedSmoke=0;
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(ChemLab.getConfig().gfx_gasChimneyFX){
			HOEMachineData data = this.getClientData();
			if(data!=null && data instanceof GasChimneyData){
				GasChimneyData gas = (GasChimneyData)data;
				synchedSmoke+=gas.holdChimneySmoke();
				if(synchedSmoke>GasChimneyData.SMOKE_LIMIT){
					synchedSmoke=GasChimneyData.SMOKE_LIMIT;
				}
			}
			smokeTick(this,this.worldObj,this.xCoord,this.yCoord,this.zCoord,random);
		}
	}

	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		GasChimneyData sbd= new GasChimneyData();
		sbd.setOwnerFingerprint(fingerprint);
		machines.injectCustomData(sbd);
		return sbd;
	}

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (HOEMachineIO) ChemLab.getIOCollection().getHOEIO("GasChimneyIO");
	}

	
	private ItemStack in,out;
	
	@Override
	public void pushToHOE() {
		in=TileEntityHelper.HOEpush(this, in);
	}

	@Override
	public void pullFromHOE() {
		out=TileEntityHelper.HOEpull(this, out);
	}

	
	ArrayList<EntityFX> fxs = new ArrayList<EntityFX>();
	ArrayList<EntityFX> fxs_trash = new ArrayList<EntityFX>();
	@Override
	public ArrayList<EntityFX> getPFXL() {
		return fxs;
	}
	private int incrflip=0;
	private void smokeTick(ITileEntityParticleManager mng, World world, double x,
			double y, double z, Random rnd) {
		

		//ArrayList<EntityFX> fxs = mng.getPFXL();
		for(EntityFX f:fxs){
			f.onUpdate();
			if(f.isDead){
				fxs_trash.add(f);
			}
		}
		for(EntityFX f:fxs_trash){
			fxs.remove(f);
		}
		fxs_trash.clear();
		
		double off = 0.5D;	
		double nozzleSize = 0.8D;
		double chaosFactor=0.2D;
		
		if(synchedSmoke>0){
			synchedSmoke--;
			incrflip++;
			if(incrflip>3){
				incrflip=0;
				double xo = (rnd.nextDouble()-off)*nozzleSize;
				double yo = (rnd.nextDouble()-off)*nozzleSize;
				
				double angX = (rnd.nextDouble()-0.5D)*chaosFactor;
				double angZ = (rnd.nextDouble()-0.5D)*chaosFactor;
				//double hoff = rnd.nextDouble()*0.85D;
				
				//world.spawnParticle("smoke", x+0.5D, y, z+0.5D, 0, .1, 0);
				EntityFX smokeFX = new ChimneySmoke(world,x+off+xo, y+1D, z+off+yo, angX, .1, angZ);
				
				fxs.add(smokeFX);
				//Minecraft.getMinecraft().effectRenderer.addEffect(smokeFX);
			}
		}
		
	}

	//INVENTORY
	//ItemStack inbound,outbound;
	@Override
	public int getSizeInventory() {
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		//hoe synced
		if(slot<0){
			GasChimneyData sd = (GasChimneyData)this.getClientData();
			if(sd==null){return null;}
			switch(slot){
			case -1:
				return sd.get_cell_in();
			case -2:
				return sd.get_cell_out();
			}
		}
		
		//real
		switch(slot){
		case 0:
			return in;
		case 1:
			return out;
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int slot, int amt) {
    	if(slot<0){return (ItemStack)null;}//You can't take from client inbound
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
                if (stack.stackSize <= amt) {
                        setInventorySlotContents(slot, null);
                        
                } else {
                        stack = stack.splitStack(amt);
                        if (stack.stackSize == 0) {
                                setInventorySlotContents(slot, null);
                               
                        }
                }
        }
        return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		switch(slot){
		case 0:
			in=stack;
			break;
		case 1:
			out=stack;
			break;
		}		
	}

	@Override
	public String getInventoryName() {
		return "GasChimneyCustomInventory";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		if(slot==0){
			return stack.getItem() instanceof HiVolumeLiquidCell;
		}
		return false;
	}

	//public final static int[] accessibleSlots = {0,1};
	public final static int[] accessibleSlots_BOTTOM = {1};
	public final static int[] accessibleSlots_SIDES = {0};
	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		if(side==0){
			//BOTTOM
			return accessibleSlots_BOTTOM;
		}else{
			return accessibleSlots_SIDES;
		}
		
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int dir) {
		if(slot!=0){return false;}
		return isItemValidForSlot(slot,stack);
		//There is NO REBOUND
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int dir) {
		if(slot!=1){return false;}
		return true;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		in = StackUtility.readItemStackFromNBT(nbt, "in");
		out = StackUtility.readItemStackFromNBT(nbt, "out");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		StackUtility.writeItemStackToNBT(in, nbt, "in");
		StackUtility.writeItemStackToNBT(out, nbt, "out");
	}

	@Override
	public void pushParticlesIntoRenderer() {
		EffectRenderer efrer=Minecraft.getMinecraft().effectRenderer;
		for(EntityFX f : this.getPFXL()){
			efrer.addEffect(f);
		}
		this.getPFXL().clear();
		
	}
	
	
	
	
	

}
