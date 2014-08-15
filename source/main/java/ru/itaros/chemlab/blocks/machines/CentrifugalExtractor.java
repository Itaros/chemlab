package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.CentrifugalExtractorContainer;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.tiles.CentrifugalExtractorTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class CentrifugalExtractor extends IOMachineBlock {

	public CentrifugalExtractor() {
		super(Material.iron);
		this.setBlockNameRaw("machine."+"centrifugalextractor");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}

	@Override
	protected int getUIID() {
		return HOEContainer.getID(CentrifugalExtractorContainer.class);
	}

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}

	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new CentrifugalExtractorTileEntity();
	}

	
	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg, "chemlab");
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_part_chambernozzle","machine_centrifugalextractor_topface","machine_part_powerio","machine_base","machine_part_iosocket","machine_part_iosocket"}, reg);
	}	
	
}
