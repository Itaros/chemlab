package ru.itaros.chemlab.client.isbr;

import ru.itaros.chemlab.blocks.AdvancedComponentBlock.AdvancedComponentBlockType;
import ru.itaros.chemlab.loader.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

public class AdvancedComponentBlockISBR extends ItarosISBR {

	private static final double POSITIVE_DIAMETER = 1D;
	private static final double NEGATIVE_DIAMETER = -0D;
	private static final double APERTURE_ELECTRODE = 0.5D-0.1D;
	private static final double OFFSET_ELECTRODE = 0.2D;
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		
		int meta = world.getBlockMetadata(x, y, z);
		AdvancedComponentBlockType type = BlockLoader.advcompblock.getType(meta);
		
		switch(type){
		case ArcFurnaceElectrodes:
			renderArcFurnaceElectrodes(world,x,y,z,block,modelId,renderer);
			break;
		}
		
		return true;
	}

	private void renderArcFurnaceElectrodes(IBlockAccess world, int x, int y,
			int z, Block block, int modelId, RenderBlocks renderer) {

		IIcon iron_icon=Blocks.iron_block.getIcon(0, 0);
		renderer.setOverrideBlockTexture(iron_icon);
		//Base
		if(isAroundOccupied(world,x,y,z)){	
			renderer.setRenderBounds(NEGATIVE_DIAMETER, NEGATIVE_DIAMETER+0.25D, NEGATIVE_DIAMETER, POSITIVE_DIAMETER, POSITIVE_DIAMETER-0.25D , POSITIVE_DIAMETER);
			renderer.renderStandardBlock(block,x,y,z);
		}
		
		//Electrodes \o/
		renderer.setRenderBounds(NEGATIVE_DIAMETER+APERTURE_ELECTRODE+OFFSET_ELECTRODE, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER+APERTURE_ELECTRODE+OFFSET_ELECTRODE, POSITIVE_DIAMETER-APERTURE_ELECTRODE+OFFSET_ELECTRODE, POSITIVE_DIAMETER , POSITIVE_DIAMETER-APERTURE_ELECTRODE+OFFSET_ELECTRODE);
		renderer.renderStandardBlock(block,x,y,z);
		renderer.setRenderBounds(NEGATIVE_DIAMETER+APERTURE_ELECTRODE-OFFSET_ELECTRODE, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER+APERTURE_ELECTRODE+OFFSET_ELECTRODE, POSITIVE_DIAMETER-APERTURE_ELECTRODE-OFFSET_ELECTRODE, POSITIVE_DIAMETER , POSITIVE_DIAMETER-APERTURE_ELECTRODE+OFFSET_ELECTRODE);
		renderer.renderStandardBlock(block,x,y,z);	
		renderer.setRenderBounds(NEGATIVE_DIAMETER+APERTURE_ELECTRODE, NEGATIVE_DIAMETER, NEGATIVE_DIAMETER+APERTURE_ELECTRODE-OFFSET_ELECTRODE, POSITIVE_DIAMETER-APERTURE_ELECTRODE, POSITIVE_DIAMETER , POSITIVE_DIAMETER-APERTURE_ELECTRODE-OFFSET_ELECTRODE);
		renderer.renderStandardBlock(block,x,y,z);			
		//Cleaning
		renderer.clearOverrideBlockTexture();
		

	}

	/*
	 * Triggers if one adjacent side is occupied by solid full block
	 */
	private boolean isAroundOccupied(IBlockAccess world, int x, int y,
			int z) {
		for(ForgeDirection fd : ForgeDirection.VALID_DIRECTIONS){
			if(world.getBlock(x+fd.offsetX, y+fd.offsetY, z+fd.offsetZ).isOpaqueCube()){return true;}
		}
		return false;
	}

	
	
}
