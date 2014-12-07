package ru.itaros.chemlab.loader;

import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.addon.bc.builder.SchematicChemLabMachine;
import ru.itaros.chemlab.addon.bc.builder.SchematicSyndicationBus;
import ru.itaros.chemlab.blocks.AdvancedComponentBlock;
import ru.itaros.chemlab.blocks.AdvancedComponentBlock.AdvancedComponentBlockType;
import ru.itaros.chemlab.blocks.MachineCasing;
import ru.itaros.chemlab.blocks.MachineItemBlock;
import ru.itaros.chemlab.blocks.StructuralBlock;
import ru.itaros.chemlab.blocks.StructuralBlock.StructuralBlockType;
import ru.itaros.chemlab.blocks.items.AdvancedComponentItemBlock;
import ru.itaros.chemlab.blocks.items.StructuralItemBlock;
import ru.itaros.chemlab.blocks.machines.AirCollector;
import ru.itaros.chemlab.blocks.machines.ArcFurnaceController;
import ru.itaros.chemlab.blocks.machines.AutomaticDrawplate;
import ru.itaros.chemlab.blocks.machines.Biogrinder;
import ru.itaros.chemlab.blocks.machines.Bloomery;
import ru.itaros.chemlab.blocks.machines.CatalyticTank;
import ru.itaros.chemlab.blocks.machines.CentrifugalExtractor;
import ru.itaros.chemlab.blocks.machines.Crusher;
import ru.itaros.chemlab.blocks.machines.DiaphragmalElectrolyzer;
import ru.itaros.chemlab.blocks.machines.EvaporationUnit;
import ru.itaros.chemlab.blocks.machines.FluidCompressor;
import ru.itaros.chemlab.blocks.machines.GasChimney;
import ru.itaros.chemlab.blocks.machines.GravMag;
import ru.itaros.chemlab.blocks.machines.HeatingFurnace;
import ru.itaros.chemlab.blocks.machines.HiResistantMixer;
import ru.itaros.chemlab.blocks.machines.HiTFurnace;
import ru.itaros.chemlab.blocks.machines.Impregnator;
import ru.itaros.chemlab.blocks.machines.MetalFormationMachine;
import ru.itaros.chemlab.blocks.machines.Mixer;
import ru.itaros.chemlab.blocks.machines.Press;
import ru.itaros.chemlab.blocks.machines.QuenchingChamber;
import ru.itaros.chemlab.blocks.machines.ServiceBay;
import ru.itaros.chemlab.blocks.machines.SteamBoiler;
import ru.itaros.chemlab.blocks.machines.SteamExplosionUnit;
import ru.itaros.chemlab.blocks.machines.Turboexpander;
import ru.itaros.chemlab.blocks.machines.Washer;
import ru.itaros.chemlab.blocks.machines.WireCoatingExtruder;
import ru.itaros.chemlab.blocks.machines.syndication.SyndicationBus;
import ru.itaros.chemlab.blocks.machines.syndication.SyndicationCapacitor;
import ru.itaros.chemlab.blocks.machines.syndication.SyndicationEMFGenerator;
import ru.itaros.chemlab.blocks.machines.syndication.SyndicationHub;
import ru.itaros.chemlab.blocks.machines.syndication.SyndicationItemPort;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockLoader {
	
	public static StructuralBlock structblock;
	public static AdvancedComponentBlock advcompblock;
	
	public static MachineCasing casing;
	
	public static Biogrinder biogrinder;
	public static CentrifugalExtractor centriextractor;
	public static Washer washer;
	public static Impregnator impregnator;
	public static Press press;
	public static SteamBoiler steamboiler;
	public static SteamExplosionUnit steamexplosionunit;
	public static Crusher crusher;
	public static DiaphragmalElectrolyzer diaphragmalelectrolyzer;
	public static HiTFurnace furnace;
	public static AirCollector aircollector;
	public static FluidCompressor fluidcompressor;
	public static Turboexpander turboexpander;
	public static EvaporationUnit evaporationunit;
	public static CatalyticTank cattank;
	public static HiResistantMixer hiresistmixer;
	public static ServiceBay servicebay;
	public static GravMag gravmag;
	//public static HVLCFiller hvlcfiller;
	public static GasChimney gaschimney;
	public static Bloomery bloomery;
	public static HeatingFurnace heatingFurnace;
	
	public static SyndicationHub syndicationhub;
	public static SyndicationBus pipes_syndicationbus;
	
	public static SyndicationCapacitor syndication_util_capacitor;
	public static SyndicationEMFGenerator syndication_emfgenerator;
	public static SyndicationItemPort syndication_itemport;
	
	public static Mixer mixer;
	public static AutomaticDrawplate automaticdrawplate;
	public static QuenchingChamber quencher;
	public static MetalFormationMachine metformer;
	
	public static WireCoatingExtruder wcextruder;
	
	public static ArcFurnaceController controller_arcFurnace;
	
	//public static OreHalite oreHalite;
	//public static OreMetal orePlatinum;
	//public static OreMetal orePyrite;
	//public static OreMetal oreMetaAnthracite;
	
	//public static OreMetal oreLimestone;
	//public static OreMetal oreHematite;
	
	//public static OreMetal orePericlase;//MgO
	
	public static void loadBlocks(){
		
		structblock = new StructuralBlock(new StructuralBlockType[]{StructuralBlockType.HeatResistantBricks,StructuralBlockType.HeatResistantConcrete});
		GameRegistry.registerBlock(structblock, StructuralItemBlock.class,structblock.getUnlocalizedName());
		
		advcompblock = new AdvancedComponentBlock(new AdvancedComponentBlockType[]{AdvancedComponentBlockType.ArcFurnaceElectrodes});
		GameRegistry.registerBlock(advcompblock,AdvancedComponentItemBlock.class,advcompblock.getUnlocalizedName());
				
		
		casing = new MachineCasing();
		GameRegistry.registerBlock(casing,casing.getUnlocalizedName());
		
		loadAsbestosMinerals();
		
		//Machines
		biogrinder = new Biogrinder();
		GameRegistry.registerBlock(biogrinder ,MachineItemBlock.class, biogrinder.getUnlocalizedName());
		
		centriextractor = new CentrifugalExtractor();
		GameRegistry.registerBlock(centriextractor ,MachineItemBlock.class, centriextractor.getUnlocalizedName());
		
		washer = new Washer();
		GameRegistry.registerBlock(washer ,MachineItemBlock.class, washer.getUnlocalizedName());
		
		impregnator = new Impregnator();
		GameRegistry.registerBlock(impregnator ,MachineItemBlock.class, impregnator.getUnlocalizedName());
			
		press = new Press();
		GameRegistry.registerBlock(press ,MachineItemBlock.class, press.getUnlocalizedName());
			
		steamboiler = new SteamBoiler();
		GameRegistry.registerBlock(steamboiler,MachineItemBlock.class, steamboiler.getUnlocalizedName());
		
		steamexplosionunit = new SteamExplosionUnit();
		GameRegistry.registerBlock(steamexplosionunit,MachineItemBlock.class, steamexplosionunit.getUnlocalizedName());
		
		crusher = new Crusher();
		GameRegistry.registerBlock(crusher,MachineItemBlock.class, crusher.getUnlocalizedName());
		
		diaphragmalelectrolyzer = new DiaphragmalElectrolyzer();
		GameRegistry.registerBlock(diaphragmalelectrolyzer,MachineItemBlock.class, diaphragmalelectrolyzer.getUnlocalizedName());
		
		furnace = new HiTFurnace();
		GameRegistry.registerBlock(furnace, MachineItemBlock.class, furnace.getUnlocalizedName());
		
		aircollector = new AirCollector();
		GameRegistry.registerBlock(aircollector,MachineItemBlock.class, aircollector.getUnlocalizedName());
		
		fluidcompressor = new FluidCompressor();
		GameRegistry.registerBlock(fluidcompressor,MachineItemBlock.class, fluidcompressor.getUnlocalizedName());
		
		turboexpander= new Turboexpander();
		GameRegistry.registerBlock(turboexpander, MachineItemBlock.class, turboexpander.getUnlocalizedName());
		
		evaporationunit = new EvaporationUnit();
		GameRegistry.registerBlock(evaporationunit, MachineItemBlock.class, evaporationunit.getUnlocalizedName());
		
		cattank = new CatalyticTank();
		GameRegistry.registerBlock(cattank,MachineItemBlock.class, cattank.getUnlocalizedName());
		
		hiresistmixer = new HiResistantMixer();
		GameRegistry.registerBlock(hiresistmixer, MachineItemBlock.class, hiresistmixer.getUnlocalizedName());
		
		servicebay = new ServiceBay();
		GameRegistry.registerBlock(servicebay, MachineItemBlock.class, servicebay.getUnlocalizedName());
		
		gravmag = new GravMag();
		GameRegistry.registerBlock(gravmag, MachineItemBlock.class, gravmag.getUnlocalizedName());
		
		//hvlcfiller = new HVLCFiller();
		//GameRegistry.registerBlock(hvlcfiller,MachineItemBlock.class, hvlcfiller.getUnlocalizedNameRaw());
		
		gaschimney = new GasChimney();
		GameRegistry.registerBlock(gaschimney,MachineItemBlock.class, gaschimney.getUnlocalizedName());
		
		mixer = new Mixer();
		GameRegistry.registerBlock(mixer,MachineItemBlock.class, mixer.getUnlocalizedName());
		
		automaticdrawplate = new AutomaticDrawplate();
		GameRegistry.registerBlock(automaticdrawplate,MachineItemBlock.class, automaticdrawplate.getUnlocalizedName());
		
		quencher = new QuenchingChamber();
		GameRegistry.registerBlock(quencher,MachineItemBlock.class, quencher.getUnlocalizedName());
		
		metformer = new MetalFormationMachine();
		GameRegistry.registerBlock(metformer,MachineItemBlock.class, metformer.getUnlocalizedName());
		
		wcextruder = new WireCoatingExtruder();
		GameRegistry.registerBlock(wcextruder,MachineItemBlock.class, wcextruder.getUnlocalizedName());
		
		
		controller_arcFurnace  = new ArcFurnaceController();
		GameRegistry.registerBlock(controller_arcFurnace,controller_arcFurnace.getUnlocalizedName());
		
		bloomery  = new Bloomery();
		GameRegistry.registerBlock(bloomery,bloomery.getUnlocalizedName());
		
		heatingFurnace = new HeatingFurnace();
		GameRegistry.registerBlock(heatingFurnace,heatingFurnace.getUnlocalizedName());
		
		//GameRegistry.registerBlock(,.getUnlocalizedNameRaw());
		
		
		syndicationhub = new SyndicationHub();
		GameRegistry.registerBlock(syndicationhub,MachineItemBlock.class, syndicationhub.getUnlocalizedName());
		
		pipes_syndicationbus = new SyndicationBus();
		GameRegistry.registerBlock(pipes_syndicationbus,pipes_syndicationbus.getUnlocalizedName());
		
		syndication_util_capacitor = new SyndicationCapacitor();
		GameRegistry.registerBlock(syndication_util_capacitor,MachineItemBlock.class, syndication_util_capacitor.getUnlocalizedName());
		
		syndication_emfgenerator = new SyndicationEMFGenerator();
		GameRegistry.registerBlock(syndication_emfgenerator,MachineItemBlock.class, syndication_emfgenerator.getUnlocalizedName());
		
		syndication_itemport = new SyndicationItemPort();
		GameRegistry.registerBlock(syndication_itemport,MachineItemBlock.class, syndication_itemport.getUnlocalizedName());
		
		//Ores
		
		//oreHalite = new OreHalite();
		//GameRegistry.registerBlock(oreHalite, oreHalite.getUnlocalizedName());
		
		//orePlatinum = new OreMetal("Platinum");
		//GameRegistry.registerBlock(orePlatinum,orePlatinum.getUnlocalizedName());
		//OreDictionary.registerOre("orePlatinum", orePlatinum);
		
		
		//orePyrite = new OreMetal("Pyrite");
		//GameRegistry.registerBlock(orePyrite,orePyrite.getUnlocalizedName());
		//OreDictionary.registerOre("orePyrite", orePyrite);
		
		//oreMetaAnthracite = new OreMetal("MetaAnthracite");
		//GameRegistry.registerBlock(oreMetaAnthracite,oreMetaAnthracite.getUnlocalizedName());
		//OreDictionary.registerOre("oreMetaAnthracite", oreMetaAnthracite);
		
		//oreLimestone = new OreMetal("Limestone");
		//GameRegistry.registerBlock(oreLimestone, oreLimestone.getUnlocalizedName());
		//OreDictionary.registerOre("oreLimestone",oreLimestone);
		
		//oreHematite = new OreMetal("Hematite");
		//GameRegistry.registerBlock(oreHematite,oreHematite.getUnlocalizedName());
		//OreDictionary.registerOre("oreHematite", oreHematite);
		
		//orePericlase = new OreMetal("Periclase");
		//GameRegistry.registerBlock(orePericlase,orePericlase.getUnlocalizedName());
		//OreDictionary.registerOre("orePericlase", orePericlase);
		
		
		registerBCBuilderSchematics();
		
	}


	private static void registerBCBuilderSchematics() {
		Block[] hoeblocks = 
			{
				biogrinder,centriextractor,washer,impregnator,
				press,steamboiler,steamexplosionunit,crusher,
				diaphragmalelectrolyzer,furnace,aircollector,
				fluidcompressor,turboexpander,evaporationunit,
				cattank,hiresistmixer,servicebay,//hvlcfiller,
				gaschimney,syndicationhub,
				syndication_util_capacitor,
				syndication_emfgenerator,syndication_itemport,
				mixer,automaticdrawplate,quencher,
				metformer,wcextruder
			};
		SchematicChemLabMachine.init(hoeblocks);
		SchematicSyndicationBus.init(pipes_syndicationbus);
	}


	//public static AsbestosMineral  asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite,asbestos_serpentite;
	
	private static void loadAsbestosMinerals() {
		
		//asbestos_crocidolite 	= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Crocidolite);
		//asbestos_tremolite		= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Tremolite);
		//asbestos_anthophyllite	= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Anthophyllite);
		//asbestos_actinolite		= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Actinolite);
		//asbestos_serpentite		= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Serpentinite);
		
		//HelperRegisterBlocks(asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite,asbestos_serpentite);
		//HelperRegisterAllinOreDict("oreAsbestos",asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite,asbestos_serpentite);
		//HelperRegisterAllinOreDict("oreSerpentineAsbestos",asbestos_serpentite);
		//HelperRegisterAllinOreDict("oreAmphiboleAsbestos",asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite);
	}
	
	private static void HelperRegisterAllinOreDict(String oredictname,Block... items){
		for(Block b:items){
			OreDictionary.registerOre(oredictname, b);
		}
	}
	private static void HelperRegisterBlocks(Block... blocks){
		for(Block b:blocks){
			HelperRegisterBlock(b);
		}
	}
	private static void HelperRegisterBlock(Block b){
		GameRegistry.registerBlock(b, b.getUnlocalizedName());
	}
	
	
}
