package ru.itaros.api.chemlab.syndication.utilities;

/*
 * Applied on a blocks who want to be extenders of internal energy reserves
 */
public interface ISyndicationCapacitor extends ISyndicationUtility {

	/*
	 * Defines storage size in EMFs(Wrapped MJ)
	 */
	public int storageSize();
	
	
}
