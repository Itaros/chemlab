package ru.itaros.chemlab.addon.cl3.userspace;

public class UserspaceLinkageException extends RuntimeException {

	public UserspaceLinkageException(String linkString) {
		super("Unable to find suitable candidate for: "+linkString);
	}

	public UserspaceLinkageException(Exception e) {
		super(e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4971971047580000247L;

}
