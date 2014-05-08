package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.ServiceBayTileEntity;
import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class ServiceBay extends IOMachineBlock {

	@Override
	protected int getUIID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}

	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new ServiceBayTileEntity();
	}
	
	
	public ServiceBay(){		
		super(Material.iron);
		this.setBlockNameRaw("machine."+"servicebay");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}
	
	
	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_base","machine_servicebay_face","machine_base","machine_base"}, reg);
	}

	@Override
	public void onNeighborBlockChange(World w, int x,
			int y, int z, Block block) {
		
		ServiceBayTileEntity sbte = (ServiceBayTileEntity) w.getTileEntity(x, y, z);
		sbte.updateConnectome();
		
	}	

	
	
	
	
}
