package ru.itaros.chemlab.items;

import ru.itaros.hoe.framework.chemistry.ChemicalCompound;
import ru.itaros.hoe.framework.chemistry.ChemicalReaction;
import ru.itaros.hoe.framework.chemistry.StoichiometricCoefficient;
import ru.itaros.hoe.framework.chemistry.registries.ReactionDatabase;

public class ChemLabChemicalItem extends ChemLabItem {

	private ChemicalCompound[] composition;
	
	private ChemicalReaction[] reactiveIndexAsResource;
	
	public ChemLabChemicalItem(String groupname, String name, ChemicalCompound[] composition) {
		super(groupname, name);
		this.composition=composition;
	}

	public void buildReactiveIndex(){
		reactiveIndexAsResource = ReactionDatabase.getInstance().getReactionsForResources(composition);
	}
	
	/*
	 * This method is SLOW AS HELL. Beware!
	 * (Must be superseded by lookup pool)
	 * !!!CHECK -HACK- section!!!
	 */
	public ChemicalReaction getAtmospericCombustionReaction(){
		for(ChemicalReaction r : reactiveIndexAsResource){
			StoichiometricCoefficient[] ress = r.getResources();
			return r;//HACK: You know it doesn't work as intended
			//But this will accelerate things for Dom
		}
		return null;
	}
	
}
