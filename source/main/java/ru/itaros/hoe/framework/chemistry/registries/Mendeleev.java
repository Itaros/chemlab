package ru.itaros.hoe.framework.chemistry.registries;

import java.util.Dictionary;
import java.util.Hashtable;

import ru.itaros.hoe.framework.chemistry.ChemicalElement;

public final class Mendeleev {

	private static Mendeleev me;
	public static Mendeleev getInstance(){
		return me;
	}
	
	private ChemicalElement[] elements;
	private Dictionary<String,ChemicalElement> symbolicLookup = new Hashtable<String,ChemicalElement>();
	
	public Mendeleev(){
		me=this;
		constructTable();
		buildLookup();
	}

	private void buildLookup() {
		for(ChemicalElement element : elements){
			symbolicLookup.put(element.getSymbol(), element);
		}
	}

	private void constructTable() {
		
		elements = new ChemicalElement[118+1];
		
		elements[0]=new ChemicalElement("*","Undefined");
		elements[1]=new ChemicalElement("H","Hydrogen");
		elements[2]=new ChemicalElement("He","Helium");
		elements[3]=new ChemicalElement("Li","Lithium");
		elements[4]=new ChemicalElement("Be","Beryllium");
		elements[5]=new ChemicalElement("B","Boron");
		elements[6]=new ChemicalElement("C","Carbon");
		elements[7]=new ChemicalElement("N","Nitrogen");
		elements[8]=new ChemicalElement("O","Oxygen");
		elements[9]=new ChemicalElement("F","Fluorine");
		elements[10]=new ChemicalElement("Ne","Neon");
		elements[11]=new ChemicalElement("Na","Sodium");
		elements[12]=new ChemicalElement("Mg","Magnesium");
		elements[13]=new ChemicalElement("Al","Aluminium");
		elements[14]=new ChemicalElement("Si","Silicon");
		elements[15]=new ChemicalElement("P","Phosphorus");
		elements[16]=new ChemicalElement("S","Sulfur");
		elements[17]=new ChemicalElement("Cl","Chlorine");
		elements[18]=new ChemicalElement("Ar","Argon");
		elements[19]=new ChemicalElement("K","Potassium");
		elements[20]=new ChemicalElement("Ca","Calcium");
		elements[21]=new ChemicalElement("Sc","Scandium");
		elements[22]=new ChemicalElement("Ti","Titanium");
		elements[23]=new ChemicalElement("V","Vanadium");
		elements[24]=new ChemicalElement("Cr","Chromium");
		elements[25]=new ChemicalElement("Mn","Manganese");
		elements[26]=new ChemicalElement("Fe","Iron");
		elements[27]=new ChemicalElement("Co","Cobalt");
		elements[28]=new ChemicalElement("Ni","Nickel");
		elements[29]=new ChemicalElement("Cu","Copper");
		elements[30]=new ChemicalElement("Zn","Zinc");
		elements[31]=new ChemicalElement("Ga","Gallium");
		elements[32]=new ChemicalElement("Ge","Germanium");
		elements[33]=new ChemicalElement("As","Arsenic");
		elements[34]=new ChemicalElement("Se","Selenium");
		elements[35]=new ChemicalElement("Br","Bromine");
		elements[36]=new ChemicalElement("Kr","Krypton");
		elements[37]=new ChemicalElement("Rb","Rubidium");
		elements[38]=new ChemicalElement("Sr","Strontium");
		elements[39]=new ChemicalElement("Y","Yttrium");
		elements[40]=new ChemicalElement("Zr","Zirconium");
		elements[41]=new ChemicalElement("Nb","Niobium");
		elements[42]=new ChemicalElement("Mo","Molybdenum");
		elements[43]=new ChemicalElement("Tc","Technetium");
		elements[44]=new ChemicalElement("Ru","Ruthenium");
		elements[45]=new ChemicalElement("Rh","Rhodium");
		elements[46]=new ChemicalElement("Pd","Palladium");
		elements[47]=new ChemicalElement("Ag","Silver");
		elements[48]=new ChemicalElement("Cd","Cadmium");
		elements[49]=new ChemicalElement("In","Indium");
		elements[50]=new ChemicalElement("Sn","Tin");
		elements[51]=new ChemicalElement("Sb","Antimony");
		elements[52]=new ChemicalElement("Te","Tellurium");
		elements[53]=new ChemicalElement("I","Iodine");
		elements[54]=new ChemicalElement("Xe","Xenon");
		elements[55]=new ChemicalElement("Cs","Caesium");
		elements[56]=new ChemicalElement("Ba","Barium");
		elements[57]=new ChemicalElement("La","Lanthanum");
		elements[58]=new ChemicalElement("Ce","Cerium");
		elements[59]=new ChemicalElement("Pr","Praseodymium");
		elements[60]=new ChemicalElement("Nd","Neodymium");
		elements[61]=new ChemicalElement("Pm","Promethium");
		elements[62]=new ChemicalElement("Sm","Samarium");
		elements[63]=new ChemicalElement("Eu","Europium");
		elements[64]=new ChemicalElement("Gd","Gadolinium");
		elements[65]=new ChemicalElement("Tb","Terbium");
		elements[66]=new ChemicalElement("Dy","Dysprosium");
		elements[67]=new ChemicalElement("Ho","Holmium");
		elements[68]=new ChemicalElement("Er","Erbium");
		elements[69]=new ChemicalElement("Tm","Thulium");
		elements[70]=new ChemicalElement("Yb","Ytterbium");
		elements[71]=new ChemicalElement("Lu","Lutetium");
		elements[72]=new ChemicalElement("Hf","Hafnium");
		elements[73]=new ChemicalElement("Ta","Tantalum");
		elements[74]=new ChemicalElement("W","Tungsten");
		elements[75]=new ChemicalElement("Re","Rhenium");
		elements[76]=new ChemicalElement("Os","Osmium");
		elements[77]=new ChemicalElement("Ir","Iridium");
		elements[78]=new ChemicalElement("Pt","Platinum");
		elements[79]=new ChemicalElement("Au","Gold");
		elements[80]=new ChemicalElement("Hg","Mercury");
		elements[81]=new ChemicalElement("Tl","Thallium");
		elements[82]=new ChemicalElement("Pb","Lead");
		elements[83]=new ChemicalElement("Bi","Bismuth");
		elements[84]=new ChemicalElement("Po","Polonium");
		elements[85]=new ChemicalElement("At","Astatine");
		elements[86]=new ChemicalElement("Rn","Radon");
		elements[87]=new ChemicalElement("Fr","Francium");
		elements[88]=new ChemicalElement("Ra","Radium");
		elements[89]=new ChemicalElement("Ac","Actinium");
		elements[90]=new ChemicalElement("Th","Thorium");
		elements[91]=new ChemicalElement("Pa","Protactinium");
		elements[92]=new ChemicalElement("U","Uranium");
		elements[93]=new ChemicalElement("Np","Neptunium");
		elements[94]=new ChemicalElement("Pu","Plutonium");
		elements[95]=new ChemicalElement("Am","Americium");
		elements[96]=new ChemicalElement("Cm","Curium");
		elements[97]=new ChemicalElement("Bk","Berkelium");
		elements[98]=new ChemicalElement("Cf","Californium");
		elements[99]=new ChemicalElement("Es","Einsteinium");
		elements[100]=new ChemicalElement("Fm","Fermium");
		elements[101]=new ChemicalElement("Md","Mendelevium");
		elements[102]=new ChemicalElement("No","Nobelium");
		elements[103]=new ChemicalElement("Lr","Lawrencium");
		elements[104]=new ChemicalElement("Rf","Rutherfordium");
		elements[105]=new ChemicalElement("Db","Dubnium");
		elements[106]=new ChemicalElement("Sg","Seaborgium");
		elements[107]=new ChemicalElement("Bh","Bohrium");
		elements[108]=new ChemicalElement("Hs","Hassium");
		elements[109]=new ChemicalElement("Mt","Meitnerium");
		elements[110]=new ChemicalElement("Ds","Darmstadtium");
		elements[111]=new ChemicalElement("Rg","Roentgenium");
		elements[112]=new ChemicalElement("Cn","Copernicium");
		elements[113]=new ChemicalElement("Uut","Ununtrium");
		elements[114]=new ChemicalElement("Fl","Flerovium");
		elements[115]=new ChemicalElement("Uup","Ununpentium");
		elements[116]=new ChemicalElement("Lv","Livermorium");
		elements[117]=new ChemicalElement("Uus","Ununseptium");
		elements[118]=new ChemicalElement("Uuo","Ununoctium");

		
	}

	public ChemicalElement getElementBySymbol(String symbol) {
		return symbolicLookup.get(symbol);
	}
	
	
}
