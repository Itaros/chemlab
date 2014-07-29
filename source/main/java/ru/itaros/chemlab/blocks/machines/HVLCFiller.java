package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.client.ui.special.HVLCFillerContainer;
import ru.itaros.chemlab.tiles.HVLCFillerTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class HVLCFiller extends IOMachineBlock {

	@Override
	protected int getUIID() {
		return HOEContainer.getID(HVLCFillerContainer.class);
	}

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}

	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new HVLCFillerTileEntity();
	}
	
	
	public HVLCFiller(){		
		super(Material.iron);
		this.setBlockNameRaw("machine."+"hvlcfiller");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}
	
	
	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_part_iosocket","machine_hvlcfiller_side","machine_hvlcfiller_face","machine_hvlcfiller_side","machine_hvlcfiller_side"}, reg);
	}


	
	
	
	
}
