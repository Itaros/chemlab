package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.zip.ZipFile;

import javax.xml.bind.JAXBException;

public class UserspaceLinkageException extends RuntimeException {

	public UserspaceLinkageException(String linkString) {
		super("Unable to find suitable candidate for: "+linkString);
	}

	public UserspaceLinkageException(Exception e) {
		super(e);
	}

	public UserspaceLinkageException(String string, Exception e) {
		super("Unable to find suitable candidate for: "+string,e);
	}

	public UserspaceLinkageException(JAXBException e, ZipFile zf) {
		super("JAXB Parsing exception in "+zf==null?"unknown":zf.getName(),e);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4971971047580000247L;

}
