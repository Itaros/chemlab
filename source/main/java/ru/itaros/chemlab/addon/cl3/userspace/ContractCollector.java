package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ContractCollector {

	@XmlElement(name="genericItem")
	public UserspaceGenericItemContract[] genericItems;
	@XmlElement(name="rigidProcess")
	public UserspaceRigidProcess[] rigidProcesses;	
	
	@XmlTransient
	public String groupName;
	
}
