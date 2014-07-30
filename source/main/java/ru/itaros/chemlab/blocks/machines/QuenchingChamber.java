package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.MixerContainer;
import ru.itaros.chemlab.client.ui.QuenchingChamberContainer;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.tiles.MixerTileEntity;
import ru.itaros.chemlab.tiles.QuenchingChamberTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class QuenchingChamber extends IOMachineBlock {

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}	
	@Override
	protected int getUIID() {
		return HOEContainer.getID(QuenchingChamberContainer.class);
	}	
	

	public QuenchingChamber() {
		super(Material.iron);
		this.setBlockNameRaw("machine."+"quencher");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}


	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new QuenchingChamberTileEntity();
	}


	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_quencher_back","machine_quencher_face","machine_base","machine_base"}, reg);
	}

}