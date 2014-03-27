package ru.itaros.api.hoe.exceptions;

public class HOENoSuchDataExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8161647044925261632L;

	@Override
	public String getMessage() {
		return "This HOEData doesn't exist in this apartment. Are you trying to pass a sided child, not host?";
	}

	
	
	
}
