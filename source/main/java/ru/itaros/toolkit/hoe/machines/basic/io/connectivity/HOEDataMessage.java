package ru.itaros.toolkit.hoe.machines.basic.io.connectivity;

import ru.itaros.api.hoe.internal.HOEData;

public abstract class HOEDataMessage{
	
	private boolean isAnswered;
	
	public final boolean isAnswered() {
		return isAnswered;
	}

	public final void setAnswered() {
		this.isAnswered = true;
	}

	public abstract void run(HOEData context);
	
	
}
