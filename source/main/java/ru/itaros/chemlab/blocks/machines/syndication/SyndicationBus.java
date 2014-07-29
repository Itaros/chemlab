package ru.itaros.chemlab.blocks.machines.syndication;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.isbr.SyndicationBusISBR;
import ru.itaros.chemlab.loader.client.ISBRLoader;
import ru.itaros.chemlab.tiles.syndication.ISyndicationPiping;
import ru.itaros.chemlab.tiles.syndication.SyndicationPipingTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class SyndicationBus extends Block implements ISyndicationPipeConnectable, ITileEntityProvider {

	
	
	public SyndicationBus() {
		super(Material.iron);
		
		this.setBlockName("syndication.bus");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		
		this.setResistance(5);
		this.setHardness(1);
		//this.setBlockTextureName("chemlab:syndication.bus");
		
		
//		this.setBlockBounds(
//				  (float)(SyndicationBusISBR.AXIAL_CENTER-SyndicationBusISBR.aperture)
//				, (float)(SyndicationBusISBR.AXIAL_CENTER-SyndicationBusISBR.aperture)
//				, (float)(SyndicationBusISBR.AXIAL_CENTER-SyndicationBusISBR.aperture)
//				, (float)(SyndicationBusISBR.AXIAL_CENTER+SyndicationBusISBR.aperture)
//				, (float)(SyndicationBusISBR.AXIAL_CENTER+SyndicationBusISBR.aperture)
//				, (float)(SyndicationBusISBR.AXIAL_CENTER+SyndicationBusISBR.aperture));
		
	}

	@Override
	public int getRenderType() {
		if(ISBRLoader.syndicationBusModel==null){return 0;}
		return ISBRLoader.syndicationBusModel.getRenderId();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	private byte culling=0b00000000;
	@Override
	public boolean shouldSideBeRendered(IBlockAccess access,
			int x, int y, int z, int side) {
		byte mask = SyndicationBusISBR.SRSLT[side];
		//return true;
		return (culling & mask) != mask;
	}
	public void setCulling(byte mask){
		culling=mask;
	}

	
	private boolean opaqueHack=false;
	@Override
	public boolean isOpaqueCube() {
		return opaqueHack;
	}
	public void setOpaqueHack(boolean state){
		opaqueHack=state;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new SyndicationPipingTileEntity();
	}


	@Override
	public IIcon getIcon(int side, int meta) {
		switch(meta){
		case META_DISABLED:
			return disabled;
		case META_SEARCHING:
			return searching;
		case META_ADHOC:
			return adhoc;
		case META_ONLINE:
			return online;
		}
		return null;
	}

	
	IIcon disabled;
	IIcon searching;
	IIcon adhoc;
	IIcon online;
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg);
		
		disabled 	= reg.registerIcon("chemlab:syndication.bus.disabled");
		searching	= reg.registerIcon("chemlab:syndication.bus.searching");
		adhoc		= reg.registerIcon("chemlab:syndication.bus.adhoc");
		online		= reg.registerIcon("chemlab:syndication.bus.online");
	}

	

	public static final short META_DISABLED	=	0;
	public static final short META_SEARCHING	=	1;
	public static final short META_ADHOC		=	2;
	public static final short META_ONLINE		=	3;
	
	
	
	@Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
		TileEntity te = world.getTileEntity(x, y, z);
		if(te instanceof ISyndicationPiping){
			ISyndicationPiping isp = (ISyndicationPiping)te;
			
			isp.setControllerOverloaded();
		}
        super.onBlockExploded(world, x, y, z, explosion);
    }	
	

}
