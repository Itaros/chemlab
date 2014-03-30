package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.BiogrinderContainer;
import ru.itaros.chemlab.client.ui.CentrifugalExtractorContainer;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.CentrifugalExtractorTileEntity;
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

}
