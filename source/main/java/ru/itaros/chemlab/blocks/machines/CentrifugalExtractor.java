package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.BiogrinderContainer;
import ru.itaros.chemlab.client.ui.CentrifugalExtractorContainer;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.CentrifugalExtractorTileEntity;
import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class CentrifugalExtractor extends IOMachineBlock {

	public CentrifugalExtractor() {
		super(Material.iron);
		this.setBlockNameRaw("chemlab:machinecentrifugalextractor");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}

	@Override
	protected int getUIID() {
		return CentrifugalExtractorContainer.ID;
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
	
	MetaIconFolder icons;
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return icons.GetIcon(side, meta);
	}
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_part_chambernozzle","machine_centrifugalextractor_topface","machine_part_powerio","machine_base","machine_part_iosocket","machine_part_iosocket"}, reg);
	}	
	
}
