package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public abstract class UserspaceContract extends UserspaceNode {

	public String[] oreDict;
	
	public UserspaceTooltip tooltip;
	
	public void registerClientData(){
		if(tooltip!=null){
			tooltip.register();
		}
	}
	
}
