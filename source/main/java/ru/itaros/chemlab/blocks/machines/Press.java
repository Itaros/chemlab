package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.PressContainer;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.PressTileEntity;
import ru.itaros.toolkit.hoe.facilities.client.textures.MetaIconFolder;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;


public class Press extends IOMachineBlock {
	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}	
	@Override
	protected int getUIID() {
		return PressContainer.ID;
	}	
	

	public Press() {
		super(Material.iron);
		this.setBlockNameRaw("machine."+"press");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}


	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new PressTileEntity();
	}


	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_base","machine_presschamber_sides","machine_presschamber_sides","machine_presschamber_sides","machine_presschamber_sides"}, reg);
	}
}
