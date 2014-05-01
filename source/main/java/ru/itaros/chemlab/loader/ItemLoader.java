package ru.itaros.chemlab.loader;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.items.*;
import ru.itaros.chemlab.items.ore.CrushedOre;
import ru.itaros.chemlab.items.ore.Dust;
import ru.itaros.chemlab.items.refactorable.DecomposedLignocellulose;
import ru.itaros.chemlab.items.refactorable.ExplodedWoodFibers;
import ru.itaros.chemlab.items.refactorable.ImpregnatedLignocellulose;
import ru.itaros.chemlab.items.refactorable.ImpregnatedWoodFibers;
import ru.itaros.chemlab.items.refactorable.ImpregnatedWoodFibersPellet;
import ru.itaros.chemlab.items.refactorable.PressedLignocellulose;
import ru.itaros.chemlab.items.refactorable.PureWoodFiberFlakes;
import ru.itaros.chemlab.items.refactorable.WashedLignocellulose;
import ru.itaros.chemlab.items.refactorable.WoodFiberFlakes;
import ru.itaros.chemlab.items.refactorable.WoodchipClump;
import ru.itaros.chemlab.items.refactorable.Woodchips;
import ru.itaros.toolkit.hoe.facilities.fluid.HOEFluid;

public class ItemLoader {

	public static Programmer programmer;
	public static PipeWrench wrench;
	
	public static HiVolumeLiquidCellEmpty emptyhvlc;
	
	public static Woodchips woodchips;
	public static WoodchipClump woodchipclump;
	
	public static WoodFiberFlakes lignocelluloseflakes;
	public static PureWoodFiberFlakes purelignocelluloseflakes;
	public static ImpregnatedWoodFibers impregnatedlignocelluloseflakes;
	public static ImpregnatedWoodFibersPellet impregnatedwoodfiberspellet;
	public static ExplodedWoodFibers explodedwoodfibers;
	public static WashedLignocellulose washedlignocellulose;
	public static ImpregnatedLignocellulose impregnatedlignocellulose;
	public static PressedLignocellulose pressedlignocellulose;
	public static DecomposedLignocellulose decomposedlignocellulose;
	
	public static DegradableItem asbestos_diaphragm;
	public static DegradableItem graphite_anode;
	
	public static DegradableItem platinum_catalization_grid;
	
	public static ChemLabItem ferricoxide;
	public static ChemLabItem amorphousGraphite;
	
	public static ChemLabItem carbonizediron;
	public static ChemLabItem carbonizedsulfuricatediron;
	
	public static ChemLabItem slag;
	
	public static ChemLabItem magnesium;
	
	public static ChemLabItem magnesiumsulfide;
	
	public static void loadItems(){
		programmer = new Programmer();
		GameRegistry.registerItem(programmer,programmer.getUnlocalizedName());
		
		wrench = new PipeWrench();
		GameRegistry.registerItem(wrench,wrench.getUnlocalizedName());
		
		woodchips = new Woodchips();
		GameRegistry.registerItem(woodchips, woodchips.getUnlocalizedName());
		
		woodchipclump = new WoodchipClump();
		GameRegistry.registerItem(woodchipclump, woodchipclump.getUnlocalizedName());	
		
		lignocelluloseflakes = new WoodFiberFlakes();
		GameRegistry.registerItem(lignocelluloseflakes,lignocelluloseflakes.getUnlocalizedName());	
		
		purelignocelluloseflakes = new PureWoodFiberFlakes();
		GameRegistry.registerItem(purelignocelluloseflakes,purelignocelluloseflakes.getUnlocalizedName());	
		
		impregnatedlignocelluloseflakes = new ImpregnatedWoodFibers();
		GameRegistry.registerItem(impregnatedlignocelluloseflakes,impregnatedlignocelluloseflakes.getUnlocalizedName());	
				
		impregnatedwoodfiberspellet = new ImpregnatedWoodFibersPellet();
		GameRegistry.registerItem(impregnatedwoodfiberspellet,impregnatedwoodfiberspellet.getUnlocalizedName());	
			
		explodedwoodfibers = new ExplodedWoodFibers();
		GameRegistry.registerItem(explodedwoodfibers,explodedwoodfibers.getUnlocalizedName());
			
		washedlignocellulose = new WashedLignocellulose();
		GameRegistry.registerItem(washedlignocellulose,washedlignocellulose.getUnlocalizedName());
			
		impregnatedlignocellulose = new ImpregnatedLignocellulose();
		GameRegistry.registerItem(impregnatedlignocellulose,impregnatedlignocellulose.getUnlocalizedName());
		
		pressedlignocellulose = new PressedLignocellulose();
		GameRegistry.registerItem(pressedlignocellulose,pressedlignocellulose.getUnlocalizedName());
		
		decomposedlignocellulose = new DecomposedLignocellulose();
		GameRegistry.registerItem(decomposedlignocellulose,decomposedlignocellulose.getUnlocalizedName());
		
		
		
		emptyhvlc = new HiVolumeLiquidCellEmpty();
		GameRegistry.registerItem(emptyhvlc,emptyhvlc.getUnlocalizedName());
		
		
		ferricoxide = new ChemLabItem("ferricoxide");
		GameRegistry.registerItem(ferricoxide,ferricoxide.getUnlocalizedName());
		
		amorphousGraphite = new ChemLabItem("graphite.amorphous");
		GameRegistry.registerItem(amorphousGraphite,amorphousGraphite.getUnlocalizedName());
		
		carbonizediron = new ChemLabItem("ingot.iron.carbonized");
		GameRegistry.registerItem(carbonizediron,carbonizediron.getUnlocalizedName());
		
		carbonizedsulfuricatediron = new ChemLabItem("ingot.iron.carbonized_sulfuricated");
		GameRegistry.registerItem(carbonizedsulfuricatediron,carbonizedsulfuricatediron.getUnlocalizedName());
		
		slag = new ChemLabItem("slag");
		GameRegistry.registerItem(slag, slag.getUnlocalizedName());
		
		magnesium = new ChemLabItem("magnesium");
		GameRegistry.registerItem(magnesium,magnesium.getUnlocalizedName());
		
		magnesiumsulfide = new ChemLabItem("magnesiumsulfide");
		GameRegistry.registerItem(magnesiumsulfide,magnesiumsulfide.getUnlocalizedName());
		
		loadAutoItems();
		
		asbestosDiahpragmRegistration();
		electrodesRegistration();
		
		platinumCatalyserRegistration();
		
	}

	

	private static void platinumCatalyserRegistration() {
		platinum_catalization_grid = new DegradableItem("platinum_catalization_grid", 64*2);
		GameRegistry.registerItem(platinum_catalization_grid,platinum_catalization_grid.getUnlocalizedName());
		
		GameRegistry.addShapedRecipe(new ItemStack(platinum_catalization_grid),
				"0X0",
				"XYX",
				"0X0",
				'X',OreDictionary.getOres("dustPlatinum").get(0),
				'Y',OreDictionary.getOres("ingotIron").get(0)
				);
	}



	private static void electrodesRegistration(){
		graphite_anode = new DegradableItem("graphite_anode", 500);
		GameRegistry.registerItem(graphite_anode,graphite_anode.getUnlocalizedName());
		
		
		
		
	}
	
	
	private static void asbestosDiahpragmRegistration() {
		asbestos_diaphragm=new DegradableItem("asbestos-diaphragm", 100);
		GameRegistry.registerItem(asbestos_diaphragm,asbestos_diaphragm.getUnlocalizedName());
		
		GameRegistry.addShapedRecipe(new ItemStack(asbestos_diaphragm), 
				"XXX",
				"X0X",
				"XXX",
				'X',OreDictionary.getOres("crushedSerpentineAsbestos").get(0));
		
	}





	private static void loadAutoItems() {
		//This method is unsafe.
		//Waiting for Player to fix FML skipper
		
		crushedOreAutoloader();
		
		dustsAutoloader();
		
		hiVolumeLiquidCellAutoloader();
		
		
		
	}
	
	

	private static void dustsAutoloader(){
		ArrayList<Dust> d = new ArrayList<Dust>();
		
		d.add(new Dust("sand",4));
		d.add(new Dust("stone",-1));
		
		checkAndAddDust(d,"gold",-1);
		checkAndAddDust(d,"platinum",-1);
		checkAndAddDust(d,"pyrite",-1);
		
		for(Dust i:d){
			GameRegistry.registerItem(i,i.getUnlocalizedName());
			OreDictionary.registerOre(i.getOredictName(), i);
		}
	}
	
	private static void checkAndAddDust(List<Dust> l, String subname, int backcraft){
		String dm = subname.substring(0, 1).toUpperCase() + subname.substring(1);
		String dict = "dust"+dm;
		
		if(OreDictionary.getOres(dict).size()==0){
			l.add(new Dust(subname,backcraft));
		}
		
	}
	
	public static final String[] knownOres = {
		//VANILLA
		"oreIron",
		"oreGold",
		"oreCoal",
		
		//CHEMLAB
		"oreSerpentineAsbestos",
		"oreAmphiboleAsbestos",
		"orePlatinum",
		"orePyrite",
		"oreMetaAnthracite",
		"oreLimestone",
		"oreHematite",
		"orePericlase"
	};	

	private static void crushedOreAutoloader(){
		for(String orename:knownOres){
			CrushedOre crushed = new CrushedOre(orename);
			GameRegistry.registerItem(crushed,crushed.getUnlocalizedName());
			OreDictionary.registerOre(crushed.getDictNameAsCrushed(), crushed);
		}
	}
	
	private static void hiVolumeLiquidCellAutoloader() {
		HOEFluid[] all = HOEFluid.getFluidRegistry().all();
		for(HOEFluid f : all){
			HiVolumeLiquidCell hvlc = new HiVolumeLiquidCell(f);
			GameRegistry.registerItem(hvlc, hvlc.getUnlocalizedName());
		}
		
	}
}
