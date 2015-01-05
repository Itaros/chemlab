package ru.itaros.hoe.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.hoe.connectome.ConnectomeController;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.tiles.IHOEInventorySyncable;
import ru.itaros.hoe.tiles.IRedstoneControllable;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.tiles.TileEntitySecurityTracker;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.IconsLibrary;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.utils.MetaIconFolder;
import ru.itaros.hoe.utils.euclideanspace.Transformations;
import ru.itaros.hoe.utils.euclideanspace.Vector3;

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
		}	
		return true;
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
		int off = RotatableBlockUtility.calculateSpinIncrement(w.getBlockMetadata(x, y, z),rotationChain.length);
		setRotationTo(w, x, y, z, off);
	}
	protected void setRotationTo(World world, int x, int y, int z,
			int rotationmeta) {
		world.setBlockMetadataWithNotify(x, y, z, rotationmeta, 1+2);
		//After setting metadata we need to cache meta into TE
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof MachineTileEntity){
			MachineTileEntity mte = (MachineTileEntity)te;
			mte.setBlockMeta(rotationmeta);
		}
	}
	public static final ForgeDirection[] rotationChain={ForgeDirection.SOUTH,ForgeDirection.EAST,ForgeDirection.NORTH,ForgeDirection.WEST};

	@Override
	public ForgeDirection[] getRotationChain() {
		return rotationChain;
	}
	
	protected final int getMetaFromRotationChain(ForgeDirection expected){
		for(int i = 0; i < rotationChain.length ; i ++){
			ForgeDirection query = rotationChain[i];
			if(query==expected){
				return i;
			}
		}
		return -1;//No rotation in chain. This is a block configuration bug
	}
	
//GFX
	protected MetaIconFolder icons;
	
	//cached icons
	private static IconsLibrary library;
	protected IconsLibrary getPortsIcons(){
		return library;
	}
	
	
	public void registerBlockIcons(IIconRegister reg, String modid) {
		if(library==null){
			library = new IconsLibrary();
		}
		library.register(reg,modid+":port_items_in",modid+":port_items_out",modid+":port_fluids_in",modid+":port_fluids_out",modid+":port_power_in",modid+":port_power_out",modid+":port_nothing");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		int realside=getRealSide(side,meta);
		return icons.GetIcon(realside, 0);
	}
	
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		
		//Meta indicates top side
		int meta = access.getBlockMetadata(x, y, z);
		
		TileEntity te = access.getTileEntity(x, y, z);
		if(te instanceof IConfigurableIO){
			IConfigurableIO io = (IConfigurableIO)te;
			PortInfo[] ports = io.getPorts();
			int realside=getRealSide(side,meta);
			PortInfo local = ports[realside];
			if(local!=null){
				return local.getIcon(library);
			}
		}
		
		return getIcon(side, meta);
		
	}
	
	@Override
	public int getRealSide(int side,int dir) {
		return RotatableBlockUtility.getIconIndiceFromSideGrid(side,dir,RotatableBlockUtility.DEFAULTSIDEGRID);
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
		
		//Interception to get drops
		if(te instanceof IConfigurableIO){
			IConfigurableIO io = (IConfigurableIO)te;
			nbtCacheDropState = new NBTTagCompound();
			io.writeIOPortsNBT(nbtCacheDropState);
			
		}		
	
		super.breakBlock(w, x, y, z, block, meta);
	}		

	//Might break in MC 1.8. If world multithreading will happen move it to hashmap(HOE Fingerprint based?)
	private NBTTagCompound nbtCacheDropState;
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z,
			int metadata, int fortune) {
		
		ArrayList<ItemStack> drops = new ArrayList<ItemStack>(1);
		
		ItemStack initial = new ItemStack(this,1,0);
		
		initial.stackTagCompound=nbtCacheDropState;
		nbtCacheDropState=null;
		
		drops.add(initial);
		return drops;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x,
			int y, int z, EntityLivingBase host,
			ItemStack stack) {
		
		//IOPorts restorer
		
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof IConfigurableIO){
			IConfigurableIO io = (IConfigurableIO)te;
			if(stack.stackTagCompound!=null){
				io.readIOPortsNBT(stack.stackTagCompound);
			}
		}		
		
		if(!world.isRemote){
			//Rotation
			Vector3 blockvector = Transformations.getVectorFromBlock(x, y, z);
			Vector3 playerpos = new Vector3(host.posX,host.posZ,host.posY);
			Vector3 radial = Transformations.getRadialPoint(blockvector, playerpos);
			ForgeDirection expectedDir = Transformations.getDirectionFromRadialPoint(radial);
			
			int meta = getMetaFromRotationChain(expectedDir);
			this.setRotationTo(world, x, y, z, meta);
		}
		
	}
	
	@Override
	public void onBlockAdded(World w, int x,
			int y, int z) {
		
		super.onBlockAdded(w, x, y, z);
	}
	@Override
	public void onNeighborBlockChange(World w, int x,
			int y, int z, Block block) {
		
		TileEntity te = w.getTileEntity(x, y, z);
		if(te!=null){
			if(te instanceof IRedstoneControllable){
				IRedstoneControllable irc = (IRedstoneControllable)te;
				irc.setPowered(w.isBlockIndirectlyGettingPowered(x, y, z));
			}
			if(te instanceof MachineTileEntity){
				HOEData data = ((MachineTileEntity)te).getServerData();
				HOEData[] adjacent = ((MachineTileEntity) te).getAdjacentHOEDatas();
				ConnectomeController hostConnectome = data.getConnectome();
				for(HOEData adj : adjacent){
					if(adj==null){continue;}
					hostConnectome.tryToConnect(data, adj);
				}
			}
		}
		
		super.onNeighborBlockChange(w, x, y, z,
				block);
	}

	

		
	
}
