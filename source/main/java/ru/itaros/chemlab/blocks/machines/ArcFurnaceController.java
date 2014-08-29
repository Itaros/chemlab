package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.blocks.multiblock.MBAssociativeDataPayload;
import ru.itaros.chemlab.blocks.multiblock.MBStrictComparator;
import ru.itaros.chemlab.client.ui.ArcFurnaceContainer;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.loader.MultiblockLoader;
import ru.itaros.chemlab.tiles.ArcFurnaceControllerTileEntity;
import ru.itaros.hoe.blocks.IOMachineBlock;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.utils.MetaIconFolder;

public class ArcFurnaceController extends IOMachineBlock {


	@Override
	protected int getUIID() {
		return HOEContainer.getID(ArcFurnaceContainer.class);
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

	@Override
	public int onBlockPlaced(World p_149660_1_, int p_149660_2_,
			int p_149660_3_, int p_149660_4_, int p_149660_5_,
			float p_149660_6_, float p_149660_7_, float p_149660_8_,
			int p_149660_9_) {
		

		
		return super.onBlockPlaced(p_149660_1_, p_149660_2_, p_149660_3_, p_149660_4_,
				p_149660_5_, p_149660_6_, p_149660_7_, p_149660_8_, p_149660_9_);
	}

	@Override
	public void onBlockAdded(World w, int x,
			int y, int z) {
		
		MBAssociativeDataPayload payload = new MBAssociativeDataPayload();
		MBStrictComparator comparator = new MBStrictComparator(MultiblockLoader.arcFurnace, w, x, y, z);
		comparator.compareAll(payload);
		
		if(comparator.getErrorMargin()==0F){
			//No errors, valid.
			comparator.fillAllWithTE();
		}		
		
		TileEntity te = w.getTileEntity(x, y, z);
		ArcFurnaceControllerTileEntity afcte = (ArcFurnaceControllerTileEntity)te;
		afcte.setValues(payload);
		
		super.onBlockAdded(w, x, y, z);
	}

	
	
	
}
