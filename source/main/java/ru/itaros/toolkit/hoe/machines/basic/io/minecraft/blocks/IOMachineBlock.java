package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.IHOEInventorySyncable;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.services.TileEntitySecurityTracker;

public abstract class IOMachineBlock extends Block implements IRotatableBlock, ITileEntityProvider, ISyndicationPipeConnectable{



	protected abstract int getUIID();
	protected abstract Object getOwnerMod();
	

	protected IOMachineBlock(Material material) {
		super(material);
		System.out.println("HOEBlock. Registration: "+this.getClass().getName());
		
		this.setHardness(ChemLabValues.BASE_MACHINE_HARDNESS);
		this.setResistance(ChemLabValues.BASE_MACHINE_RESISTANCE);
		
		 this.setHarvestLevel("pickaxe", 2);
		
	}
	
	
	protected abstract MachineTileEntity getNewTileEntity();
	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return getNewTileEntity();
	}
	
	
	private String rawname;
	public String getUnlocalizedNameRaw(){
		return rawname;
	}
	public void setBlockNameRaw(String name){
		rawname=name;
		this.setBlockName(rawname);
	}
	
	
	private void securityCheck(World world, int x,
			int y, int z,EntityPlayer entplayer){
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof MachineTileEntity){
			MachineTileEntity mte = (MachineTileEntity)te;
			TileEntitySecurityTracker sec =mte.getSecurity();
			sec.setOwnerIfNotSet(entplayer);
		}
		
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer entplayer,
			int unknown, float px, float py,
			float pz) {	
		
		securityCheck(world,x,y,z, entplayer);
		
		entplayer.openGui(getOwnerMod(), getUIID(), world, x, y, z);		
		
		if(world.isRemote){
			//client
			
		}else{
			//server
			world.markBlockForUpdate(x, y, z);
			
			System.out.println("ISEN: "+((MachineTileEntity)world.getTileEntity(x, y, z)).getServerData().isSkipEventNotified());
		}	
		return true;
	}
	
	
	
	
	//Sync thingie
	@Override
	public void updateTick(World world, int x, int y,
			int z, Random random) {
		
		
		TileEntity te  =world.getTileEntity(x, y, z);
		if(te instanceof IHOEInventorySyncable){
			IHOEInventorySyncable me = (IHOEInventorySyncable)te;
			me.pullFromHOE();
			me.pushToHOE();
			me.sync();
		}
		if(te instanceof MachineTileEntity){
			HOEMachineData data = ((MachineTileEntity) te).getServerData();
			if(data != null && data.isPerformingBlockUpdates()){
				//If it is syndicated it tries to relieve load from tickmanager at all
				world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
			}
		}
		
		
	}

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
            par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
    }	
    
	
	
	@Override
	public int tickRate(World p_149738_1_) {
		return 20*5;
	}
	

	@Override
	public void breakBlock(World w, int x, int y,
			int z, Block block, int meta) {
		
		//This method sets flag to decline HOEData which will not be existant in world
		TileEntity te = w.getTileEntity(x, y, z);
		if(te instanceof MachineTileEntity){
			MachineTileEntity mte =(MachineTileEntity)te;
			mte.invalidateHOEData();
		}
		
		super.breakBlock(w, x, y, z,
				block, meta);
	}	
	
	
	@Override
	public boolean hasTileEntity(int meta) {
		return true;
	}	
	
	
//ROTATION
	//private ForgeDirection currentRotation=ForgeDirection.SOUTH;
	
	



	@Override
	public ForgeDirection getDirection(World w, int x, int y, int z) {
		return ForgeDirection.getOrientation(w.getBlockMetadata(x, y, z));
	}

	
	
	@Override
	public boolean rotateBlock(World worldObj, int x, int y, int z,
			ForgeDirection axis) {
		rotate(worldObj,x,y,z);
		return true;
	}
	@Override
	public void rotate(World w, int x, int y, int z) {
		int off = RotatableBlockUtility.calculateSpinIncrement(w,x,y,z,rotationChain.length);
		w.setBlockMetadataWithNotify(x, y, z, off, 1+2);
	}
	public static final ForgeDirection[] rotationChain={ForgeDirection.SOUTH,ForgeDirection.EAST,ForgeDirection.NORTH,ForgeDirection.WEST};

	@Override
	public ForgeDirection[] getRotationChain() {
		return rotationChain;
	}
	
//GFX
	protected MetaIconFolder icons;
	
	@Override
	public IIcon getIcon(int side, int meta) {
		int realside=getRealSide(side,meta);
		return icons.GetIcon(realside, 0);
	}
	

	
	private int getRealSide(int side,int dir) {
		return RotatableBlockUtility.getIconIndiceFromSideGrid(side,dir,RotatableBlockUtility.DEFAULTSIDEGRID);
	}

	
	
}
