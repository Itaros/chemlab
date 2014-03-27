package ru.itaros.api.hoe;

import ru.itaros.api.hoe.exceptions.HOENoSuchDataExistsException;
import ru.itaros.api.hoe.internal.HOEData;

/*
 * CONVENIENCE ACCESS INTERFACE! DO NOT IMPLEMENT!
 */
public interface IHOEDataLink {
	//public HOEData getCell(long cell);
	
	/*
	 * Creates new cell in a job and returns its HOST.
	 * You read and write using child from getChild();
	 * Those calls are automatically processed by job handler
	 */
	public HOEData generateCell();
	
	/*
	 * This method remove a cell. You need to provide a HOST.
	 * Child data will fail and will bloat execution container.
	 */
	public void deleteCell(HOEData data) throws HOENoSuchDataExistsException;
	
}
