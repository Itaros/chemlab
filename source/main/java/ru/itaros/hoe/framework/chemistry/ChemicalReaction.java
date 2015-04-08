package ru.itaros.hoe.framework.chemistry;

import ru.itaros.hoe.framework.chemistry.registries.CompoundDatabase;

public class ChemicalReaction {

	private StoichiometricCoefficient[] resources,products;
	
	public ChemicalReaction(String equation){
		parseEquation(equation);
	}

	private void parseEquation(String equation) {
		String[] sides = equation.split("->");
		
		String resourcesString = sides[0];
		String productsString = sides[1];
		
		resources = parsePartial(resourcesString);
		products = parsePartial(productsString);
		
	}

	private StoichiometricCoefficient[] parsePartial(String resourcesString) {
		String[] compounds = resourcesString.split("\\+");
		//Cleanup
		for(int i = 0 ; i < compounds.length ; i++){
			compounds[i]=compounds[i].replace(" ","");
		}
		//TODO: extraction of coefficients
		//Fetching from Compounds DB
		StoichiometricCoefficient[] retr = new StoichiometricCoefficient[compounds.length];
		for(int i = 0 ; i < compounds.length ; i++){
			String symbol = compounds[i];
			ChemicalCompound compound = CompoundDatabase.getInstance().getCompoundBySymbol(symbol);
			int localCoefficient = 1;
			retr[i]=new StoichiometricCoefficient(compound, localCoefficient);
		}
		
		return retr;
	}
	

	//Thermodynamics
	private long reactionEnthalpy;
	
	public long getReactionEnthalpy() {
		return reactionEnthalpy;
	}

	public void setReactionEnthalpy(long joulesPerMole) {
		this.reactionEnthalpy = joulesPerMole;
	}

	public void calculateReactionEnthalpy() {
		long enthalpy=0;
		long productsEnthalpy=0;
		long resourcesEnthalpy=0;
		for(StoichiometricCoefficient coeff : products){
			productsEnthalpy+=coeff.getCompound().getFormationEnthalpy()*coeff.getCoefficient();
		}
		for(StoichiometricCoefficient coeff : resources){
			resourcesEnthalpy+=coeff.getCompound().getFormationEnthalpy()*coeff.getCoefficient();
		}		
		//Hess's Law
		enthalpy=productsEnthalpy-resourcesEnthalpy;
		
		setReactionEnthalpy(enthalpy);
	}

	public StoichiometricCoefficient[] getResources() {
		return resources;	
	}
	public StoichiometricCoefficient[] getProducts() {
		return products;	
	}
	
}
