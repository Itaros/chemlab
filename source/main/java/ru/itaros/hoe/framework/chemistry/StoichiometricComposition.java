package ru.itaros.hoe.framework.chemistry;

public final class StoichiometricComposition {

	public StoichiometricComposition(ChemicalElement element, int amount) {
		super();
		this.element = element;
		this.amount = amount;
	}
	
	private ChemicalElement element;
	private int amount;
	
	public ChemicalElement getElement() {
		return element;
	}
	public int getAmount() {
		return amount;
	}
	
	
	
}
