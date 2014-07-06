package ru.itaros.api.emf;

/*
 * This interface is applied on HOEData!
 */
public interface IEMFCollector {

	public int getStoredAmount();
	
	public int getMaxAmount();
	
	/*
	 * Gets free space. Can be negative in a case of overflow!
	 * You can use it to equalize overshoots.
	 */
	public int getFreeSpace();
	
	/*
	 * Injects power. It is assumed it can overflow for a bit
	 */
	public void injectPower(int amount);
	/*
	 * Takes power. It can't overflow. Real amount taken is returned
	 */
	public int ejectPower(int amount);
	
	
}
