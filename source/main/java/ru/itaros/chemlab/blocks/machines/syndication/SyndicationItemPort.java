package ru.itaros.chemlab.blocks.machines.syndication;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.client.ui.syndication.SyndicationItemPortContainer;
import ru.itaros.chemlab.tiles.syndication.SyndicationItemPortTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class SyndicationItemPort extends IOMachineBlock implements ISyndicationPipeConnectable {

	public SyndicationItemPort(){
		super(Material.iron);
		
		this.setBlockName("syndication.utilities.itemport");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}
	
	
	private static final int METADATA_VARIATIONS = 1;	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_synd_util_itemport_side","machine_synd_util_itemport_face","machine_synd_util_itemport_side","machine_synd_util_itemport_side"}, reg);
	}

	@Override
	protected int getUIID() {
		return HOEContainer.getID(SyndicationItemPortContainer.class);
	}
	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}



	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new SyndicationItemPortTileEntity();
	}

}
