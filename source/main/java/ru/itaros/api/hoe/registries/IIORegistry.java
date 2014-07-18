package ru.itaros.api.hoe.registries;

import ru.itaros.api.hoe.internal.HOEIO;

public interface IIORegistry {

	public void add(HOEIO io);
	public HOEIO get(String fullname);
	/*
	 * This method sends call for all IOs to take ownership of static data
	 * For example: recipes and stuff.
	 */
	public void claimOwnership();
}
