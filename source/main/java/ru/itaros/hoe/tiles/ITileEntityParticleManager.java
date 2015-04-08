package ru.itaros.hoe.tiles;

import java.util.ArrayList;

import net.minecraft.client.particle.EntityFX;

public interface ITileEntityParticleManager {
	
	public ArrayList<EntityFX> getPFXL();
	
	public void pushParticlesIntoRenderer();
	
}
