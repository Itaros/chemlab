package ru.itaros.chemlab.addon.cl3.userspace;

import ru.itaros.hoe.framework.chemistry.ChemicalCompound;
import ru.itaros.hoe.framework.chemistry.registries.CompoundDatabase;

public class UserspaceChemicalItemContract extends UserspaceGenericItemContract {

	public UserspaceCompositionNode[] composition;

	public ChemicalCompound[] getCompounds() {
		ChemicalCompound[] rtr = new ChemicalCompound[composition.length];
		for(int i = 0 ; i <  composition.length ; i ++){
			UserspaceCompositionNode ucn = composition[i];
			rtr[i]=CompoundDatabase.getInstance().getCompoundBySymbol(ucn.compound);
		}
		return rtr;
	}
	
	
}
