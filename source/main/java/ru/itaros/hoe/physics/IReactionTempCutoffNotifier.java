package ru.itaros.hoe.physics;

/*
 * Used in conjunction with IReaction.EventType.TEMP_CUTOFF.
 * 
 */
public interface IReactionTempCutoffNotifier {

	public float getLow();
	public float getHigh();
	
}
