package ru.itaros.chemlab.client.isbr;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public abstract class ItarosISBR implements ISimpleBlockRenderingHandler {

	public ItarosISBR(){
		modelID = RenderingRegistry.getNextAvailableRenderId();
	}
	
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return false;
	}

	
	private int modelID;
	
	@Override
	public int getRenderId() {
		return modelID;
	}

}
