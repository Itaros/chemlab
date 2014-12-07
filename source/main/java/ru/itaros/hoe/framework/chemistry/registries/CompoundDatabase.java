package ru.itaros.hoe.framework.chemistry.registries;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import ru.itaros.hoe.framework.chemistry.ChemicalCompound;

public final class CompoundDatabase {

	private static CompoundDatabase me;
	public static CompoundDatabase getInstance(){
		return me;
	}
	
	public CompoundDatabase(){
		me=this;
	}
	
	private ArrayList<ChemicalCompound> compounds = new ArrayList<ChemicalCompound>();
	private Dictionary<String,ChemicalCompound> symbolicLookup = new Hashtable<String,ChemicalCompound>();
	
	
	public void addCompound(ChemicalCompound cc){
		compounds.add(cc);
		registerInLookupTables(cc);
	}
	
	private void registerInLookupTables(ChemicalCompound cc) {
		symbolicLookup.put(cc.symbolicName, cc);
	}

	/*
	 * Returns compound by symbol like O2 or H2S2O8(as H2(SO4)2)
	 */
	public ChemicalCompound getCompoundBySymbol(String symbol){
		ChemicalCompound cc = symbolicLookup.get(symbol);
		return cc;
	}
	
	
}
