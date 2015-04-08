package ru.itaros.hoe.physics;

/*
 * Defines a thing able to receive or give energy(in MJ, MEGAJOULES, not BC)
 * REQUIRES IMatterState to get Cp!
 */
public interface IEnergyReceiver extends IHeatable{

	public void giveEnergy(float mj);
	public float receiveEnergy(float mj);
}
