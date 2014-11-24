package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class UserspaceContract {

	public String nodeName;
	
	public String[] oreDict;
	
}
