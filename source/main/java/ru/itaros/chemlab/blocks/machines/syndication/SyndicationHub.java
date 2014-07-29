package ru.itaros.chemlab.blocks.machines.syndication;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.client.ui.syndication.SyndicationHubContainer;
import ru.itaros.chemlab.tiles.syndication.SyndicationHubTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class SyndicationHub extends IOMachineBlock implements ISyndicationPipeConnectable {

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}	
	@Override
	protected int getUIID() {
		return HOEContainer.getID(SyndicationHubContainer.class);
	}	
	

	public SyndicationHub() {
		super(Material.iron);
		this.setBlockNameRaw("machine."+"syndication.hub");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}


	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new SyndicationHubTileEntity();
	}


	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_base","machine_syndicationhub_face","machine_base","machine_base"}, reg);
	}

}
