package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks;

import java.util.Random;

import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class IOMachineBlock extends Block implements IRotatableBlock, ITileEntityProvider{



	protected abstract int getUIID();
	protected abstract Object getOwnerMod();
	

	protected IOMachineBlock(Material material) {
		super(material);
		System.out.println("Registering: "+this.getClass().getName());
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
	
	
	@Override
	public boolean onBlockActivated(World world, int x,
			int y, int z, EntityPlayer entplayer,
			int unknown, float px, float py,
			float pz) {	
		
		entplayer.openGui(getOwnerMod(), getUIID(), world, x, y, z);		
		
		if(world.isRemote){
			//client
			
		}else{
			//server
			world.markBlockForUpdate(x, y, z);
		}	
		return true;
	}
	
	
	
	
	//Sync thingie
	@Override
	public void updateTick(World world, int x, int y,
			int z, Random random) {
		
		
		TileEntity te  =world.getTileEntity(x, y, z);
		if(te instanceof MachineCrafterTileEntity){
			MachineCrafterTileEntity me = (MachineCrafterTileEntity)te;
			me.pullFromHOE();
			me.pushToHOE();
			me.sync();
		}
		
		world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
	}

    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
            par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
    }	
    
	@Override
	public int tickRate(World p_149738_1_) {
		return 20*5;
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
		w.setBlockMetadataWithNotify(x, y, z, off, 2);
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
