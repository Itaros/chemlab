package ru.itaros.chemlab.loader;

import net.minecraft.block.Block;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.addon.bc.builder.SchematicChemLabMachine;
import ru.itaros.chemlab.addon.bc.builder.SchematicSyndicationBus;
import ru.itaros.chemlab.blocks.MachineCasing;
import ru.itaros.chemlab.blocks.MachineItemBlock;
import ru.itaros.chemlab.blocks.machines.*;
import ru.itaros.chemlab.blocks.machines.syndication.*;
import ru.itaros.chemlab.blocks.ore.AsbestosMineral;
import ru.itaros.chemlab.blocks.ore.OreHalite;
import ru.itaros.chemlab.blocks.ore.OreMetal;

public class BlockLoader {
	
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
	public static HVLCFiller hvlcfiller;
	public static GasChimney gaschimney;
	
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
	
	public static OreHalite oreHalite;
	public static OreMetal orePlatinum;
	public static OreMetal orePyrite;
	public static OreMetal oreMetaAnthracite;
	
	public static OreMetal oreLimestone;
	public static OreMetal oreHematite;
	
	public static OreMetal orePericlase;//MgO
	
	public static void loadBlocks(){
		
		casing = new MachineCasing();
		GameRegistry.registerBlock(casing,casing.getUnlocalizedName());
		
		loadAsbestosMinerals();
		
		//Machines
		biogrinder = new Biogrinder();
		GameRegistry.registerBlock(biogrinder ,MachineItemBlock.class, biogrinder.getUnlocalizedNameRaw());
		
		centriextractor = new CentrifugalExtractor();
		GameRegistry.registerBlock(centriextractor ,MachineItemBlock.class, centriextractor.getUnlocalizedNameRaw());
		
		washer = new Washer();
		GameRegistry.registerBlock(washer ,MachineItemBlock.class, washer.getUnlocalizedNameRaw());
		
		impregnator = new Impregnator();
		GameRegistry.registerBlock(impregnator ,MachineItemBlock.class, impregnator.getUnlocalizedNameRaw());
			
		press = new Press();
		GameRegistry.registerBlock(press ,MachineItemBlock.class, press.getUnlocalizedNameRaw());
			
		steamboiler = new SteamBoiler();
		GameRegistry.registerBlock(steamboiler,MachineItemBlock.class, steamboiler.getUnlocalizedNameRaw());
		
		steamexplosionunit = new SteamExplosionUnit();
		GameRegistry.registerBlock(steamexplosionunit,MachineItemBlock.class, steamexplosionunit.getUnlocalizedNameRaw());
		
		crusher = new Crusher();
		GameRegistry.registerBlock(crusher,MachineItemBlock.class, crusher.getUnlocalizedNameRaw());
		
		diaphragmalelectrolyzer = new DiaphragmalElectrolyzer();
		GameRegistry.registerBlock(diaphragmalelectrolyzer,MachineItemBlock.class, diaphragmalelectrolyzer.getUnlocalizedNameRaw());
		
		furnace = new HiTFurnace();
		GameRegistry.registerBlock(furnace, MachineItemBlock.class, furnace.getUnlocalizedNameRaw());
		
		aircollector = new AirCollector();
		GameRegistry.registerBlock(aircollector,MachineItemBlock.class, aircollector.getUnlocalizedNameRaw());
		
		fluidcompressor = new FluidCompressor();
		GameRegistry.registerBlock(fluidcompressor,MachineItemBlock.class, fluidcompressor.getUnlocalizedNameRaw());
		
		turboexpander= new Turboexpander();
		GameRegistry.registerBlock(turboexpander, MachineItemBlock.class, turboexpander.getUnlocalizedNameRaw());
		
		evaporationunit = new EvaporationUnit();
		GameRegistry.registerBlock(evaporationunit, MachineItemBlock.class, evaporationunit.getUnlocalizedNameRaw());
		
		cattank = new CatalyticTank();
		GameRegistry.registerBlock(cattank,MachineItemBlock.class, cattank.getUnlocalizedNameRaw());
		
		hiresistmixer = new HiResistantMixer();
		GameRegistry.registerBlock(hiresistmixer, MachineItemBlock.class, hiresistmixer.getUnlocalizedNameRaw());
		
		servicebay = new ServiceBay();
		GameRegistry.registerBlock(servicebay, MachineItemBlock.class, servicebay.getUnlocalizedNameRaw());
		
		hvlcfiller = new HVLCFiller();
		GameRegistry.registerBlock(hvlcfiller,MachineItemBlock.class, hvlcfiller.getUnlocalizedNameRaw());
		
		gaschimney = new GasChimney();
		GameRegistry.registerBlock(gaschimney,MachineItemBlock.class, gaschimney.getUnlocalizedNameRaw());
		
		mixer = new Mixer();
		GameRegistry.registerBlock(mixer,MachineItemBlock.class, mixer.getUnlocalizedNameRaw());
		
		automaticdrawplate = new AutomaticDrawplate();
		GameRegistry.registerBlock(automaticdrawplate,MachineItemBlock.class, automaticdrawplate.getUnlocalizedNameRaw());
		
		quencher = new QuenchingChamber();
		GameRegistry.registerBlock(quencher,MachineItemBlock.class, quencher.getUnlocalizedNameRaw());
		
		metformer = new MetalFormationMachine();
		GameRegistry.registerBlock(metformer,MachineItemBlock.class, metformer.getUnlocalizedNameRaw());
		
		wcextruder = new WireCoatingExtruder();
		GameRegistry.registerBlock(wcextruder,MachineItemBlock.class, wcextruder.getUnlocalizedNameRaw());
		
		//GameRegistry.registerBlock(,.getUnlocalizedNameRaw());
		
		
		syndicationhub = new SyndicationHub();
		GameRegistry.registerBlock(syndicationhub,MachineItemBlock.class, syndicationhub.getUnlocalizedNameRaw());
		
		pipes_syndicationbus = new SyndicationBus();
		GameRegistry.registerBlock(pipes_syndicationbus,pipes_syndicationbus.getUnlocalizedName());
		
		syndication_util_capacitor = new SyndicationCapacitor();
		GameRegistry.registerBlock(syndication_util_capacitor,MachineItemBlock.class, syndication_util_capacitor.getUnlocalizedName());
		
		syndication_emfgenerator = new SyndicationEMFGenerator();
		GameRegistry.registerBlock(syndication_emfgenerator,MachineItemBlock.class, syndication_emfgenerator.getUnlocalizedName());
		
		syndication_itemport = new SyndicationItemPort();
		GameRegistry.registerBlock(syndication_itemport,MachineItemBlock.class, syndication_itemport.getUnlocalizedName());
		
		//Ores
		
		oreHalite = new OreHalite();
		GameRegistry.registerBlock(oreHalite, oreHalite.getUnlocalizedName());
		
		orePlatinum = new OreMetal("Platinum");
		GameRegistry.registerBlock(orePlatinum,orePlatinum.getUnlocalizedName());
		OreDictionary.registerOre("orePlatinum", orePlatinum);
		
		
		orePyrite = new OreMetal("Pyrite");
		GameRegistry.registerBlock(orePyrite,orePyrite.getUnlocalizedName());
		OreDictionary.registerOre("orePyrite", orePyrite);
		
		oreMetaAnthracite = new OreMetal("MetaAnthracite");
		GameRegistry.registerBlock(oreMetaAnthracite,oreMetaAnthracite.getUnlocalizedName());
		OreDictionary.registerOre("oreMetaAnthracite", oreMetaAnthracite);
		
		oreLimestone = new OreMetal("Limestone");
		GameRegistry.registerBlock(oreLimestone, oreLimestone.getUnlocalizedName());
		OreDictionary.registerOre("oreLimestone",oreLimestone);
		
		oreHematite = new OreMetal("Hematite");
		GameRegistry.registerBlock(oreHematite,oreHematite.getUnlocalizedName());
		OreDictionary.registerOre("oreHematite", oreHematite);
		
		orePericlase = new OreMetal("Periclase");
		GameRegistry.registerBlock(orePericlase,orePericlase.getUnlocalizedName());
		OreDictionary.registerOre("orePericlase", orePericlase);
		
		
		registerBCBuilderSchematics();
		
	}


	private static void registerBCBuilderSchematics() {
		Block[] hoeblocks = 
			{
				biogrinder,centriextractor,washer,impregnator,
				press,steamboiler,steamexplosionunit,crusher,
				diaphragmalelectrolyzer,furnace,aircollector,
				fluidcompressor,turboexpander,evaporationunit,
				cattank,hiresistmixer,servicebay,hvlcfiller,
				gaschimney,syndicationhub,
				syndication_util_capacitor,
				syndication_emfgenerator,syndication_itemport,
				mixer,automaticdrawplate,quencher,
				metformer,wcextruder
			};
		SchematicChemLabMachine.init(hoeblocks);
		SchematicSyndicationBus.init(pipes_syndicationbus);
	}


	public static AsbestosMineral  asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite,asbestos_serpentite;
	
	private static void loadAsbestosMinerals() {
		
		asbestos_crocidolite 	= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Crocidolite);
		asbestos_tremolite		= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Tremolite);
		asbestos_anthophyllite	= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Anthophyllite);
		asbestos_actinolite		= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Actinolite);
		asbestos_serpentite		= new AsbestosMineral(AsbestosMineral.AsbestosMineralType.Serpentinite);
		
		HelperRegisterBlocks(asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite,asbestos_serpentite);
		HelperRegisterAllinOreDict("oreAsbestos",asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite,asbestos_serpentite);
		HelperRegisterAllinOreDict("oreSerpentineAsbestos",asbestos_serpentite);
		HelperRegisterAllinOreDict("oreAmphiboleAsbestos",asbestos_crocidolite,asbestos_tremolite,asbestos_anthophyllite,asbestos_actinolite);
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
