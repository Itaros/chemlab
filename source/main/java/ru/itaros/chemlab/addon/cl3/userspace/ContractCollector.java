package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContractCollector {

	@XmlElement(name="genericItem")
	public UserspaceGenericItemContract[] genericItems;
	
}
