package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.MetalFormationMachineContainer;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.tiles.MetalFormationMachineTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class MetalFormationMachine extends IOMachineBlock {

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}	
	@Override
	protected int getUIID() {
		return HOEContainer.getID(MetalFormationMachineContainer.class);
	}	
	

	public MetalFormationMachine() {
		super(Material.iron);
		this.setBlockNameRaw("machine."+"metformer");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}


	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new MetalFormationMachineTileEntity();
	}


	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg, "chemlab");
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_mform_top","machine_base","machine_mform_face","machine_base","machine_base"}, reg);
	}

}
