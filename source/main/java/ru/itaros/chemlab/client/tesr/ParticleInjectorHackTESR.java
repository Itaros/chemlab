package ru.itaros.chemlab.client.tesr;

import ru.itaros.hoe.tiles.ITileEntityParticleManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class ParticleInjectorHackTESR extends TileEntitySpecialRenderer {

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float possibly_tick_fraction) {
		ITileEntityParticleManager parman = (ITileEntityParticleManager)tile;
		
		parman.pushParticlesIntoRenderer();
		
	}

}
