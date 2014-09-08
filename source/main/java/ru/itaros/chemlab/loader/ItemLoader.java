package ru.itaros.chemlab.loader;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.ChemLabItem;
import ru.itaros.chemlab.items.ChemLabWireItem;
import ru.itaros.chemlab.items.CrushedOre;
import ru.itaros.chemlab.items.DegradableItem;
import ru.itaros.chemlab.items.Dust;
import ru.itaros.chemlab.items.HVLCIndex;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.items.HiVolumeLiquidCellEmpty;
import ru.itaros.chemlab.items.IOMultitool;
import ru.itaros.chemlab.items.IronScraps;
import ru.itaros.chemlab.items.ItemPortApplianceItem;
import ru.itaros.chemlab.items.PipeWrench;
import ru.itaros.chemlab.items.Programmer;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.tiles.ioconfig.PortType;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemLoader {

	public static Programmer programmer;
	public static PipeWrench wrench;
	public static IOMultitool iomultitool;
	
	public static HiVolumeLiquidCellEmpty emptyhvlc;
	
	public static ChemLabItem woodchips;
	public static ChemLabItem woodchipclump;
	
	public static ChemLabItem lignocelluloseflakes;
	public static ChemLabItem purelignocelluloseflakes;
	public static ChemLabItem impregnatedlignocelluloseflakes;
	public static ChemLabItem impregnatedwoodfiberspellet;
	public static ChemLabItem explodedwoodfibers;
	public static ChemLabItem washedlignocellulose;
	public static ChemLabItem impregnatedlignocellulose;
	public static ChemLabItem pressedlignocellulose;
	public static ChemLabItem decomposedlignocellulose;
	
	public static DegradableItem asbestos_diaphragm;
	public static DegradableItem graphite_anode;
	
	public static DegradableItem platinum_catalization_grid;
	
	public static ChemLabItem ferricoxide;
	public static ChemLabItem amorphousGraphite;
	
	public static ChemLabItem wroughtiron;
	public static ChemLabItem pigiron;
	public static ChemLabItem sulfuricatedpigiron;
	
	public static ChemLabItem slag;
	
	public static ChemLabItem magnesium;
	
	public static ChemLabItem magnesiumsulfide;
	
	public static IronScraps scraps_iron;
	public static ChemLabItem scraps_wrought;
	
	//general components
	public static ChemLabItem screw,pcbpad;
	public static ChemLabItem frame,controlinterface;
	public static ItemPortApplianceItem panel;
	public static ChemLabItem mjconversionunit;
	public static ChemLabItem centralassembly;
	
	public static ChemLabItem powercable;
	//IPAIs
	public static ItemPortApplianceItem ipai_items;
	public static ItemPortApplianceItem ipai_fluids;
	//specialized components
	public static ChemLabItem heatingelement;
	public static ChemLabItem pressurenozzle;
	
	public static ChemLabItem microtube;
	public static ChemLabItem multicompartment;
	
	public static ChemLabItem fluidmixer;
	public static ChemLabItem fluidchamber;
	
	public static ChemLabItem ioportexpansionconnector;
	public static ChemLabItem iteminterface;
	
	public static ChemLabItem presshead;
	public static ChemLabItem actuator;
	
	public static ChemLabItem replacablesleigh;
	public static ChemLabItem grindinggear;
	
	public static ChemLabItem pumpcasing,pumpmotor,pumphelicalrotor;
	public static ChemLabItem airpump;
	
	public static ChemLabItem electrodeconnector;
	
	public static ChemLabItem iomultitoolcomponent;
	public static ChemLabItem diamonddrawplateassembly;
	
	public static ChemLabItem syndicationcpuunit;
	public static ChemLabItem syndicationcpuchip;
	public static ChemLabItem multiportconnector;
	public static ChemLabItem gatecontainment;
	
	//Wire industry
	public static ChemLabItem rod_wroughtIron;
	public static ChemLabItem rod_iron;
	
	public static ChemLabWireItem rod_swg_brittle_wroughtIron;
	public static ChemLabWireItem rod_swg_brittle_iron;	
	
	public static ChemLabWireItem rod_swg_hot_wroughtIron;
	public static ChemLabWireItem rod_swg_hot_iron;		
	
	public static ChemLabWireItem rod_swg_wroughtIron;
	public static ChemLabWireItem rod_swg_iron;			
	
	public static void loadItems(){
		
		//COMPONENTS
		screw=new ChemLabItem("component.screw");
		pcbpad=new ChemLabItem("component.pcbpad");
		frame=new ChemLabItem("component.frame");
		panel=new ItemPortApplianceItem("component.panel",null);
		controlinterface=new ChemLabItem("component.controlinterface");
		mjconversionunit=new ChemLabItem("component.mjconversionunit");
		centralassembly=new ChemLabItem("component.centralassembly");
		GameRegistry.registerItem(screw,screw.getUnlocalizedName());
		GameRegistry.registerItem(pcbpad,pcbpad.getUnlocalizedName());
		GameRegistry.registerItem(frame,frame.getUnlocalizedName());
		GameRegistry.registerItem(panel,panel.getUnlocalizedName());
		GameRegistry.registerItem(controlinterface,controlinterface.getUnlocalizedName());
		GameRegistry.registerItem(mjconversionunit,mjconversionunit.getUnlocalizedName());
		GameRegistry.registerItem(centralassembly,centralassembly.getUnlocalizedName());
		
		powercable=new ChemLabItem("component.powercable");
		GameRegistry.registerItem(powercable,powercable.getUnlocalizedName());
		
		//IPAIs
		ipai_items= new ItemPortApplianceItem("ipai.items",PortType.ITEM);
		ipai_fluids= new ItemPortApplianceItem("ipai.fluids",PortType.FLUID);
		GameRegistry.registerItem(ipai_items,ipai_items.getUnlocalizedName());
		GameRegistry.registerItem(ipai_fluids,ipai_fluids.getUnlocalizedName());
		
		//SPECIALIZED COMPONENTS
		heatingelement=new ChemLabItem("component.heatingelement");
		pressurenozzle=new ChemLabItem("component.pressurenozzle");
		GameRegistry.registerItem(heatingelement,heatingelement.getUnlocalizedName());
		GameRegistry.registerItem(pressurenozzle,pressurenozzle.getUnlocalizedName());
		
		microtube=new ChemLabItem("component.microtube");
		multicompartment=new ChemLabItem("component.multicompartment");
		GameRegistry.registerItem(microtube,microtube.getUnlocalizedName());
		GameRegistry.registerItem(multicompartment,multicompartment.getUnlocalizedName());
		
		fluidmixer=new ChemLabItem("component.fluidmixer");
		fluidchamber=new ChemLabItem("component.fluidchamber");
		GameRegistry.registerItem(fluidmixer,fluidmixer.getUnlocalizedName());
		GameRegistry.registerItem(fluidchamber,fluidchamber.getUnlocalizedName());
		
		presshead=new ChemLabItem("component.presshead");
		actuator=new ChemLabItem("component.actuator");
		GameRegistry.registerItem(presshead,presshead.getUnlocalizedName());
		GameRegistry.registerItem(actuator,actuator.getUnlocalizedName());
		
		ioportexpansionconnector=new ChemLabItem("component.ioportexpansionconnector");
		iteminterface=new ChemLabItem("component.iteminterface");
		GameRegistry.registerItem(ioportexpansionconnector,ioportexpansionconnector.getUnlocalizedName());
		GameRegistry.registerItem(iteminterface,iteminterface.getUnlocalizedName());

		replacablesleigh=new ChemLabItem("component.replacablesleigh");
		grindinggear=new ChemLabItem("component.grindinggear");
		GameRegistry.registerItem(replacablesleigh,replacablesleigh.getUnlocalizedName());
		GameRegistry.registerItem(grindinggear,grindinggear.getUnlocalizedName());
		
		
		pumpcasing=new ChemLabItem("component.pumpcasing");
		pumpmotor=new ChemLabItem("component.pumpmotor");
		pumphelicalrotor=new ChemLabItem("component.pumphelicalrotor");
		GameRegistry.registerItem(pumpcasing,pumpcasing.getUnlocalizedName());
		GameRegistry.registerItem(pumpmotor,pumpmotor.getUnlocalizedName());
		GameRegistry.registerItem(pumphelicalrotor,pumphelicalrotor.getUnlocalizedName());
		
		airpump=new ChemLabItem("component.airpump");
		GameRegistry.registerItem(airpump,airpump.getUnlocalizedName());
		
		electrodeconnector=new ChemLabItem("component.electrodeconnector");
		GameRegistry.registerItem(electrodeconnector,electrodeconnector.getUnlocalizedName());
		
		iomultitoolcomponent=new ChemLabItem("component.iomultitool");
		GameRegistry.registerItem(iomultitoolcomponent,iomultitoolcomponent.getUnlocalizedName());
			
		
		syndicationcpuunit = new ChemLabItem("component.syndication.cpu.unit");
		GameRegistry.registerItem(syndicationcpuunit,syndicationcpuunit.getUnlocalizedName());
		syndicationcpuchip = new ChemLabItem("component.syndication.cpu.chip");
		GameRegistry.registerItem(syndicationcpuchip,syndicationcpuchip.getUnlocalizedName());
		multiportconnector = new ChemLabItem("component.syndication.multiport");
		GameRegistry.registerItem(multiportconnector,multiportconnector.getUnlocalizedName());	
		gatecontainment  = new ChemLabItem("component.syndication.gatecontainment");
		GameRegistry.registerItem(gatecontainment,gatecontainment.getUnlocalizedName());	
		
		diamonddrawplateassembly = new ChemLabItem("component.diamonddrawplateassembly");
		GameRegistry.registerItem(diamonddrawplateassembly,diamonddrawplateassembly.getUnlocalizedName());
		
		//TOOLS
		programmer = new Programmer();
		GameRegistry.registerItem(programmer,programmer.getUnlocalizedName());
		
		wrench = new PipeWrench();
		GameRegistry.registerItem(wrench,wrench.getUnlocalizedName());
		
		iomultitool = new IOMultitool();
		GameRegistry.registerItem(iomultitool,iomultitool.getUnlocalizedName());
		
		//GENERICS
		
		woodchips = new ChemLabItem("woodchips");
		GameRegistry.registerItem(woodchips, woodchips.getUnlocalizedName());
		
		woodchipclump = new ChemLabItem("woodchipclump");
		GameRegistry.registerItem(woodchipclump, woodchipclump.getUnlocalizedName());	
		
		lignocelluloseflakes =new ChemLabItem("lignocelluloseflakes");
		GameRegistry.registerItem(lignocelluloseflakes,lignocelluloseflakes.getUnlocalizedName());	
		
		purelignocelluloseflakes = new ChemLabItem("purelignocelluloseflakes");
		GameRegistry.registerItem(purelignocelluloseflakes,purelignocelluloseflakes.getUnlocalizedName());	
		
		impregnatedlignocelluloseflakes = new ChemLabItem("impregnatedlignocelluloseflakes");
		GameRegistry.registerItem(impregnatedlignocelluloseflakes,impregnatedlignocelluloseflakes.getUnlocalizedName());	
				
		impregnatedwoodfiberspellet = new ChemLabItem("impregnatedwoodfiberspellet");
		GameRegistry.registerItem(impregnatedwoodfiberspellet,impregnatedwoodfiberspellet.getUnlocalizedName());	
			
		explodedwoodfibers = new ChemLabItem("explodedwoodfibers");
		GameRegistry.registerItem(explodedwoodfibers,explodedwoodfibers.getUnlocalizedName());
			
		washedlignocellulose = new ChemLabItem("washedlignocellulose");
		GameRegistry.registerItem(washedlignocellulose,washedlignocellulose.getUnlocalizedName());
			
		impregnatedlignocellulose = new ChemLabItem("impregnatedlignocellulose");
		GameRegistry.registerItem(impregnatedlignocellulose,impregnatedlignocellulose.getUnlocalizedName());
		
		pressedlignocellulose = new ChemLabItem("pressedlignocellulose");
		GameRegistry.registerItem(pressedlignocellulose,pressedlignocellulose.getUnlocalizedName());
		
		decomposedlignocellulose = new ChemLabItem("decomposedlignocellulose");
		GameRegistry.registerItem(decomposedlignocellulose,decomposedlignocellulose.getUnlocalizedName());
		
		
		
		emptyhvlc = new HiVolumeLiquidCellEmpty();
		GameRegistry.registerItem(emptyhvlc,emptyhvlc.getUnlocalizedName());
		
		
		ferricoxide = new ChemLabItem("ferricoxide");
		GameRegistry.registerItem(ferricoxide,ferricoxide.getUnlocalizedName());
		
		amorphousGraphite = new ChemLabItem("graphite.amorphous");
		GameRegistry.registerItem(amorphousGraphite,amorphousGraphite.getUnlocalizedName());
		
		
		if(TierLoader.L0_WroughtIron.isEnabled()){
			wroughtiron = new ChemLabItem("ingot.iron.wrought");
			GameRegistry.registerItem(wroughtiron,wroughtiron.getUnlocalizedName());
			TierLoader.L0_WroughtIron.setTargetItem(wroughtiron);
		}
		TierLoader.L0_WroughtIron.setSourceItem(new ItemStack(Items.iron_ingot,1));
		
		pigiron = new ChemLabItem("ingot.iron.pig");
		GameRegistry.registerItem(pigiron,pigiron.getUnlocalizedName());
		
		sulfuricatedpigiron = new ChemLabItem("ingot.iron.pig_sulfuricated");
		GameRegistry.registerItem(sulfuricatedpigiron,sulfuricatedpigiron.getUnlocalizedName());
		
		slag = new ChemLabItem("slag");
		GameRegistry.registerItem(slag, slag.getUnlocalizedName());
		
		magnesium = new ChemLabItem("magnesium");
		GameRegistry.registerItem(magnesium,magnesium.getUnlocalizedName());
		
		magnesiumsulfide = new ChemLabItem("magnesiumsulfide");
		GameRegistry.registerItem(magnesiumsulfide,magnesiumsulfide.getUnlocalizedName());
		
		if(TierLoader.L0_WroughtIron.isEnabled()){
			rod_wroughtIron = new ChemLabItem("rod.wrought");
			GameRegistry.registerItem(rod_wroughtIron,rod_wroughtIron.getUnlocalizedName());
			rod_swg_brittle_wroughtIron = new ChemLabWireItem("wire.wrought.brittle",10);
			GameRegistry.registerItem(rod_swg_brittle_wroughtIron,rod_swg_brittle_wroughtIron.getUnlocalizedName());
			rod_swg_hot_wroughtIron = new ChemLabWireItem("wire.wrought.hot",10);
			GameRegistry.registerItem(rod_swg_hot_wroughtIron,rod_swg_hot_wroughtIron.getUnlocalizedName());
			
			rod_swg_wroughtIron = new ChemLabWireItem("wire.wrought",10);
			GameRegistry.registerItem(rod_swg_wroughtIron,rod_swg_wroughtIron.getUnlocalizedName());	
		
			scraps_wrought = new ChemLabItem("scraps.wrought");
			GameRegistry.registerItem(scraps_wrought,scraps_wrought.getUnlocalizedName());
		}
		
		rod_iron= new ChemLabItem("rod.iron");
		GameRegistry.registerItem(rod_iron,rod_iron.getUnlocalizedName());
		rod_swg_brittle_iron = new ChemLabWireItem("wire.iron.brittle",16);
		GameRegistry.registerItem(rod_swg_brittle_iron,rod_swg_brittle_iron.getUnlocalizedName());
		rod_swg_hot_iron = new ChemLabWireItem("wire.iron.hot",16);
		GameRegistry.registerItem(rod_swg_hot_iron,rod_swg_hot_iron.getUnlocalizedName());
				
		rod_swg_iron = new ChemLabWireItem("wire.iron",10);
		GameRegistry.registerItem(rod_swg_iron,rod_swg_iron.getUnlocalizedName());		
		
		scraps_iron = new IronScraps("scraps.iron");
		GameRegistry.registerItem(scraps_iron,scraps_iron.getUnlocalizedName());
		
		
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
		
		checkAndAddDust(d,"iron",-1);
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
		HVLCIndex[] index = new HVLCIndex[all.length];
		int j = -1;
		for(HOEFluid f : all){
			j++;
			HVLCIndex i = new HVLCIndex(f);
			index[j]=i;
		}
		HiVolumeLiquidCell hvlc = new HiVolumeLiquidCell(index);
		GameRegistry.registerItem(hvlc, hvlc.getUnlocalizedName());		
	}
}
