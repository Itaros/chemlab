package ru.itaros.hoe.physics;

import ru.itaros.hoe.itemhandling.MixtureStack;

/*
 * There many ways to evaluate reaction.
 * They can be ongoing gradiental(K-related/Ea-shift reactions).
 * Or ongoing eventual(state transition)
 */
public interface IReaction {

	public EventType getEventType();
	public static enum EventType{
		INERT,
		TEMP_CUTOFF
	}
	
	public void perform(MixtureStack stack);
}
