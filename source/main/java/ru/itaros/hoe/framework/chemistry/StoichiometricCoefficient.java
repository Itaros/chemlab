package ru.itaros.hoe.framework.chemistry;

public final class StoichiometricCoefficient {

	private ChemicalCompound compound;
	private int coefficient;
	public ChemicalCompound getCompound() {
		return compound;
	}
	public int getCoefficient() {
		return coefficient;
	}
	public StoichiometricCoefficient(ChemicalCompound compound, int coefficient) {
		super();
		this.compound = compound;
		this.coefficient = coefficient;
	}
	
	
	
}
