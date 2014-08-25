package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.tiles.ArcFurnaceControllerTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class ArcFurnaceController extends IOMachineBlock {


	@Override
	protected int getUIID() {
		//return HOEContainer.getID(ArcFurnaceControllerContainer.class);
		return 0;
	}

	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}

	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new ArcFurnaceControllerTileEntity();
	}
	
	
	public ArcFurnaceController(){		
		super(Material.iron);
		this.setBlockNameRaw("machine."+"multiblock.arcfurnace");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}
	
	
	private static final int METADATA_VARIATIONS = 1;	
	//Graphics
	

	@Override
	public void registerBlockIcons(IIconRegister reg) {
		super.registerBlockIcons(reg, "chemlab");
		icons = new MetaIconFolder(METADATA_VARIATIONS);
		icons.Register(0, "chemlab", new String[]{"machine_base","machine_part_iosocket","machine_hvlcfiller_side","machine_hvlcfiller_face","machine_hvlcfiller_side","machine_hvlcfiller_side"}, reg);
	}

}
