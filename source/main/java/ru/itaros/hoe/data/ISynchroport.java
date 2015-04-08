package ru.itaros.hoe.data;

public abstract interface ISynchroport {
	public void markDirty();
	public boolean pollDirty();
}
