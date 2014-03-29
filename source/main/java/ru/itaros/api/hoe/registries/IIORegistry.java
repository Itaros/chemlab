package ru.itaros.api.hoe.registries;

import ru.itaros.api.hoe.internal.HOEIO;

public interface IIORegistry {

	public void add(HOEIO io);
	public HOEIO get(String fullname);
}
