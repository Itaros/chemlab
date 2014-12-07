package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ContractCollector {

	@XmlElement(name="genericItem")
	public UserspaceGenericItemContract[] genericItems;
	@XmlElement(name="genericHOEFluid")
	public UserspaceGenericHOEFluid[] genericFluids;
	
	@XmlElement(name="rigidProcess")
	public UserspaceRigidProcess[] rigidProcesses;	
	
	@XmlElement(name="gridCrafting")
	public UserspaceGridCrafting[] gridCraftings;		
	
	@XmlElement(name="HOEChemicalCompound")
	public UserspaceCompound[] hoeChemicalCompounds;
	
	@XmlElement(name="HOEChemicalReaction")
	public UserspaceReaction[] hoeChemicalReactions;	
	
	@XmlTransient
	public String groupName;
	
}
