package ru.itaros.hoe.framework.chemistry;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.itaros.hoe.framework.chemistry.registries.Mendeleev;

public class ChemicalCompound {

	public ChemicalCompound(String symbolicName){
		this.symbolicName=symbolicName;
		generateStoichiometry();
	}

	public String symbolicName;
	
	public StoichiometricComposition[] composition;
	
	private void generateStoichiometry() {
		Pattern stachiRegex = Pattern.compile("[A-Z]{1}([a-z]+)?[0-9]?");
		Matcher stachiMatcher = stachiRegex.matcher(symbolicName);
		ArrayList<String> matches = new ArrayList<String>();
		while(stachiMatcher.find()){
			String group = stachiMatcher.group();
			matches.add(group);
		}
		composition=new StoichiometricComposition[matches.size()];
		for(int i = 0; i<matches.size();i++){
			String s = matches.get(i);
			Pattern numberSplit = Pattern.compile("[A-Za-z]+|[0-9]");
			Matcher numberMatcher = numberSplit.matcher(s);
			numberMatcher.find();
			String elementName = numberMatcher.group();
			String elementAmountString;
			if(numberMatcher.find()){
				elementAmountString = numberMatcher.group();
			}else{
				elementAmountString = "1";
			}
			ChemicalElement element = Mendeleev.getInstance().getElementBySymbol(elementName);
			int elementAmount = Integer.parseInt(elementAmountString);
			composition[i]=new StoichiometricComposition(element,elementAmount);
		}
	}

	@Override
	public String toString() {
		String r="";
		for(StoichiometricComposition c : composition){
			r+=c.getElement().getSymbol()+c.getAmount();
		}
		return r+"@"+this.hashCode();
	}
	
	
	
}
