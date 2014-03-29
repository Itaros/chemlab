package ru.itaros.api.hoe.exceptions;

/*
 * Influenced by #AppliedEnergistics channel
 */
public class HOEBaconNotWalrusException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7720113718263105292L;

	@Override
	public String getMessage() {
		return "HOEnvironment IS NOT INSTALLED!";
	}

}
