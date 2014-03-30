package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import ru.itaros.chemlab.client.ui.BiogrinderContainer;
import ru.itaros.chemlab.client.ui.GUIBiogrinder;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class CentrifugalExtractor extends IOMachineBlock {

	protected CentrifugalExtractor(Material material) {
		super(Material.iron);
		this.setBlockNameRaw("chemlab:machinecentrifugalextractor");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}

	@Override
	protected int getUIID() {
		return BiogrinderContainer.ID;
	}

	@Override
	protected Object getOwnerMod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MachineTileEntity getNewTileEntity() {
		// TODO Auto-generated method stub
		return null;
	}

}
