package ru.itaros.hoe.framework.chemistry.registries;

import java.util.ArrayList;

import ru.itaros.hoe.framework.chemistry.ChemicalCompound;
import ru.itaros.hoe.framework.chemistry.ChemicalReaction;
import ru.itaros.hoe.framework.chemistry.StoichiometricCoefficient;

public final class ReactionDatabase {

	private static ReactionDatabase me;
	public static ReactionDatabase getInstance(){
		return me;
	}
	
	public ReactionDatabase(){
		me=this;
	}
	
	ArrayList<ChemicalReaction> reactions = new ArrayList<ChemicalReaction>();
	
	
	
	public void register(ChemicalReaction reaction){
		reactions.add(reaction);
	}

	public ChemicalReaction[] getReactionsForResources(ChemicalCompound... compounds) {
		ChemicalReaction[] rtrt;
		ArrayList<ChemicalReaction> compositor = new ArrayList<ChemicalReaction>();
		for(ChemicalReaction reaction : reactions){
			StoichiometricCoefficient[] resses = reaction.getResources();
			boolean containsExcess=false;
			for(ChemicalCompound ccagainst : compounds){
				boolean isPresent=false;
				for(StoichiometricCoefficient sc : resses){
					if(sc.getCompound()==ccagainst){isPresent=true;break;}
				}
				if(!isPresent){
					containsExcess=true;
				}
			}
			
			if(containsExcess){
				/*
				 * This reaction doesn't have compounds present in resources. 
				 * Partial reactions are not supported due to presense 
				 * of nonstoichiometric compounds.
				 * 
				 * Partial reactions should be defined explicitly.
				 */
				continue;
			}else{
				compositor.add(reaction);
				continue;
			}
		}
		rtrt=new ChemicalReaction[compositor.size()];
		rtrt = compositor.toArray(rtrt);
		return rtrt;
	}
	
}
