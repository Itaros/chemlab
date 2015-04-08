package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserspaceRigidProcess extends UserspaceContract {

	public String IOPackage;
	
	public UserspaceLink[] in;
	public UserspaceLink[] out;
	
	public int power;
	public int time;
	
}
