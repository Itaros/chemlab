package ru.itaros.chemlab.blocks.machines.syndication;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.api.chemlab.ISyndicationPipeConnectable;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.tiles.syndication.SyndicationEMFGeneratorTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class SyndicationEMFGenerator extends IOMachineBlock implements
		ISyndicationPipeConnectable {

	
	public SyndicationEMFGenerator(){
		super(Material.iron);
		
		this.setBlockName("syndication.utilities.emfgen");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setBlockTextureName("chemlab:syndication.utilities.battery");
	}
	
	
	private static final int METADATA_VARIATIONS = 1;	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_synd_util_emfgen_side","machine_synd_util_emfgen_face","machine_synd_util_emfgen_side","machine_synd_util_emfgen_side"}, reg);
	}

	@Override
	protected int getUIID() {
		return 0;
	}
	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}



	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new SyndicationEMFGeneratorTileEntity();
	}

}
