package ru.itaros.chemlab.loader.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import buildcraft.BuildCraftCore;
import buildcraft.BuildCraftSilicon;
import buildcraft.BuildCraftTransport;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaCraftingRecipes {

	public static void load() {
		
		if(TierLoader.L0_WroughtIron.isEnabled()){
			//Buildcraft helper recipes
			GameRegistry.addShapedRecipe(new ItemStack(BuildCraftCore.ironGearItem),
					"SIS",
					"IWI",
					"SIS",
					'W', new ItemStack(BuildCraftCore.stoneGearItem),
					'I', TierLoader.L0_WroughtIron.getTargetItem(),//That is only wrought recipe
					'S', new ItemStack(ItemLoader.screw)
					);
			
			//Vanilla piston helper recipe
			ShapedOreRecipe pistonHelperRecipe = new ShapedOreRecipe(new ItemStack(Blocks.piston),
					"WWW",
					"CIC",
					"CRC",
					'I',TierLoader.L0_WroughtIron.getTargetItem(),
					'C',new ItemStack(Blocks.cobblestone),
					'R',new ItemStack(Items.redstone),
					'W',"plankWood");
			CraftingManager.getInstance().getRecipeList().add(pistonHelperRecipe);
		}
		
		//Tools
		
		//~~~PROGRAMMER~~~
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.programmer),
				"SSS",
				"IPI",
				"GGG",
				'S',new ItemStack(ItemLoader.screw),
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'P',new ItemStack(ItemLoader.pcbpad),
				'G',new ItemStack(Items.gold_nugget)
				);
		
		//~~~WRENCH~~~
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.wrench),
				"0II",
				"00I",
				"00I",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.wrench),
				"II0",
				"I00",
				"I00",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);
				
		//~~~HVLC~~~
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.emptyhvlc,16),
				"ICI",
				"ICI",
				"INI",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'C',new ItemStack(ItemLoader.fluidchamber),
				'N',new ItemStack(ItemLoader.pressurenozzle)
		);
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.emptyhvlc,32),
				"ICI",
				"ICI",
				"INI",
				'I',new ItemStack(Items.iron_ingot),
				'C',new ItemStack(ItemLoader.fluidchamber),
				'N',new ItemStack(ItemLoader.pressurenozzle)
		);		
		
		
		//Basic modules
		
		
		//CONTROL INTERFACE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.controlinterface), 
				"000",
				"BBB",
				"PPP",
				'P',new ItemStack(ItemLoader.pcbpad),
				'B',new ItemStack(Blocks.stone_button));
		
		
		//MACHINE CORE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.centralassembly), 
				"SCS",
				"SPS",
				"SMS",
				'M',new ItemStack(ItemLoader.mjconversionunit),
				'C',new ItemStack(ItemLoader.controlinterface),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(ItemLoader.pcbpad));		
		
		//MACHINE CASING
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.casing), 
				"PFP",
				"FCF",
				"PFP",
				'F',new ItemStack(ItemLoader.frame),
				'P',new ItemStack(ItemLoader.panel),
				'C',new ItemStack(ItemLoader.centralassembly));
		
		
		//Basic modules components
		//MACHINE FRAME - IRON
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.frame),
				"IIS",
				"IS0",
				"S00",
				'S',new ItemStack(ItemLoader.screw),
				'I',new ItemStack(Items.iron_ingot)
				);
		//MACHINE FRAME - WROUGHT IRON
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.frame),
				"IIS",
				"ISI",
				"SII",
				'S',new ItemStack(ItemLoader.screw),
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);		
		
		if(TierLoader.L0_WroughtIron.isEnabled()){
			//SCREW - WROUGHT IRON
			GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.screw,16),
					"III",
					"0I0",
					"0I0",
					'I',new ItemStack(ItemLoader.wroughtiron)
					);	
		}
		//SCREW - IRON
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.screw,16),
				"III",
				"0I0",
				"0I0",
				'I',new ItemStack(Items.iron_ingot)
				);			
		
		//PANEL - IRON
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.panel,4),
				"III",
				"III",
				"III",
				'I',new ItemStack(Items.iron_ingot)
				);		
		if(TierLoader.L0_WroughtIron.isEnabled()){
			//PANEL - WROUGHT IRON
			GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.panel),
					"III",
					"III",
					"III",
					'I',new ItemStack(ItemLoader.wroughtiron)
					);				
		}
		
		//PCBPAD - TEMPORARY
//		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pcbpad),
//				"000",
//				"IGI",
//				"WWW",
//				'I',new ItemStack(ItemLoader.wroughtiron),
//				'G',new ItemStack(Items.gold_nugget),
//				'W',OreDictionary.getOres("slabWood")
//				);			
		
		ShapedOreRecipe PCB = new ShapedOreRecipe(new ItemStack(ItemLoader.pcbpad),
				"   ",
				"IGI",
				"WWW",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'G',new ItemStack(Items.gold_nugget),
				'W',"slabWood");
		CraftingManager.getInstance().getRecipeList().add(PCB);
		
		
		//MJ POWERCONV - wrought iron
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.mjconversionunit),
				"IRI",
				"SGS",
				"III",
				'G',new ItemStack(BuildCraftCore.stoneGearItem),
				'S',new ItemStack(ItemLoader.screw),
				'R',new ItemStack(Blocks.redstone_torch),
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);		
		//MJ POWERCONV - iron
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.mjconversionunit),
				"IRI",
				"SGS",
				"III",
				'G',new ItemStack(BuildCraftCore.stoneGearItem),
				'S',new ItemStack(ItemLoader.screw),
				'R',new ItemStack(Blocks.redstone_torch),
				'I',new ItemStack(Items.iron_ingot)
				);				
		
		//DIAMOND DRAWPLATE ASSEMBLY
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.diamonddrawplateassembly),
				"IDI",
				"SGS",
				"IDI",
				'G',new ItemStack(BuildCraftCore.stoneGearItem),
				'S',new ItemStack(ItemLoader.screw),
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'D',new ItemStack(Items.diamond)
				);				
		
		//SYNDICATION CPU UNIT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.syndicationcpuunit),
				"EPI",
				"PCP",
				"IPE",
				'C',new ItemStack(ItemLoader.syndicationcpuchip),
				'I',new ItemStack(ItemLoader.controlinterface),
				'E',new ItemStack(ItemLoader.ioportexpansionconnector),
				'P',new ItemStack(ItemLoader.pcbpad)
				);			
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.syndicationcpuunit),
				"IPE",
				"PCP",
				"EPI",
				'C',new ItemStack(ItemLoader.syndicationcpuchip),
				'I',new ItemStack(ItemLoader.controlinterface),
				'E',new ItemStack(ItemLoader.ioportexpansionconnector),
				'P',new ItemStack(ItemLoader.pcbpad)
				);		
		
		//GATE CONTAINMENT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.gatecontainment),
				"SMS",
				"MPM",
				"SCS",
				'P',new ItemStack(Items.ender_pearl),
				'S',new ItemStack(ItemLoader.screw),
				'C',new ItemStack(ItemLoader.pcbpad),
				'M',new ItemStack(ItemLoader.panel)
				);			
		
		//MULTIPORT CONNECTOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.multiportconnector),
				"SPS",
				"WGW",
				"SPS",
				'G',new ItemStack(ItemLoader.gatecontainment),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(BuildCraftTransport.pipeItemsStone),
				'W',new ItemStack(ItemLoader.powercable)
				);			
		
		
		//IO MULTITOOL COMPONENT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.iomultitoolcomponent),
				"0S0",
				"0S0",
				"0B0",
				'S',new ItemStack(Items.stick),
				'B',new ItemStack(Blocks.stone_button)
				);		
		
		//IO MULTITOOL
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.iomultitool),
				"00C",
				"0R0",
				"000",
				'C',new ItemStack(BuildCraftSilicon.redstoneChipset,1,2),
				'R',new ItemStack(ItemLoader.iomultitoolcomponent)
				);		
		
		
		//CHEATING: SS CPU
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.syndicationcpuchip),
				"LCL",
				"CQC",
				"LCL",
				'Q',new ItemStack(Items.quartz),
				'C',new ItemStack(ItemLoader.powercable),
				'L',new ItemStack(BuildCraftSilicon.redstoneChipset,1,3)
				);			
		
		
		loadMachinesRecipes();
		
		
	}

	private static void loadMachinesRecipes() {
		//SPECIAL COMPONENTS
		//EJECTOR NOZZLE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pressurenozzle,2),
				"I0I",
				"0I0",
				"0I0",
				'I',new ItemStack(Items.iron_ingot)
				);	
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pressurenozzle),
				"I0I",
				"0I0",
				"0I0",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);	
		//HEATING ELEMENT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.heatingelement),
				"CCI",
				"IGI",
				"ICC",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'C',new ItemStack(Items.clay_ball),
				'G',new ItemStack(BuildCraftCore.ironGearItem)
				);		
		//=====STEAM BOILER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.steamboiler),
				"PNP",
				"PCP",
				"HHH",
				'C',new ItemStack(BlockLoader.casing),
				'P',new ItemStack(ItemLoader.panel),
				'H',new ItemStack(ItemLoader.heatingelement),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);	
		
		//MICROTUBE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.microtube,5),
				"I0I",
				"I0I",
				"I0I",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);			
		
		//MULTICOMPARTMENT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.multicompartment),
				"ITI",
				"TTT",
				"ITI",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'T',new ItemStack(ItemLoader.microtube)
				);			
		//=====STEAM EXPLOSION UNIT=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.steamexplosionunit),
				"SNS",
				"MCM",
				"SNS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'M',new ItemStack(ItemLoader.multicompartment),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);	
		//=====TURBOEXPANDER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.turboexpander),
				"SNS",
				"GCG",
				"SNS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'N',new ItemStack(ItemLoader.pressurenozzle),
				'G',new ItemStack(BuildCraftCore.stoneGearItem)
				);			
		
		//FLUID CHAMBER
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.fluidchamber),
				"I0I",
				"I0I",
				"INI",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);	
		//FLUID MIXER
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.fluidmixer),
				"I0I",
				"0G0",
				"I0I",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'G',new ItemStack(BuildCraftCore.stoneGearItem)
				);	
		
		//=====WASHER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.washer),
				"SMS",
				"SCS",
				"SLS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'L',new ItemStack(ItemLoader.fluidchamber),
				'M',new ItemStack(ItemLoader.fluidmixer)
				);			
		
		//PRESS HEAD
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.presshead),
				"0I0",
				"III",
				"WWW",
				'W',new ItemStack(Blocks.stone),
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);			
		
//		//ACTUATOR
//		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.fluidchamber),
//				"SSS",
//				"IMI",
//				"SSS",
//				'I',new ItemStack(Items.iron_ingot),
//				'M',new ItemStack(ItemLoader.mjconversionunit),
//				'S',new ItemStack(ItemLoader.screw)
//				);	
		
		//=====PRESS=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.press),
				"AHA",
				"SCS",
				"AHA",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'H',new ItemStack(ItemLoader.presshead),
				'A',new ItemStack(ItemLoader.actuator)
				);	
		//=====IMPREGNATOR=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.impregnator),
				"AHA",
				"SCS",
				"SLS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'H',new ItemStack(ItemLoader.presshead),
				'A',new ItemStack(ItemLoader.actuator),
				'L',new ItemStack(ItemLoader.fluidchamber)
				);
		
		//=====HI-RESISTANT MIXER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.hiresistmixer),
				"PMP",
				"PCP",
				"PLP",
				'C',new ItemStack(BlockLoader.casing),
				'P',new ItemStack(ItemLoader.panel),
				'M',new ItemStack(ItemLoader.fluidmixer),
				'L',new ItemStack(ItemLoader.fluidchamber)
				);	
		//=====FURNACE=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.furnace),
				"PSP",
				"PCP",
				"HHH",
				'C',new ItemStack(BlockLoader.casing),
				'P',new ItemStack(ItemLoader.panel),
				'H',new ItemStack(ItemLoader.heatingelement),
				'S',new ItemStack(ItemLoader.screw)
				);			

		//=====FLUID COMPRESSOR=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.fluidcompressor),
				"PPP",
				"SCS",
				"SLS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'L',new ItemStack(ItemLoader.fluidchamber),
				'P',new ItemStack(ItemLoader.presshead)
				);		
		
		//=====EVAPORATION UNIT=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.evaporationunit),
				"SNS",
				"SCS",
				"LLL",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'L',new ItemStack(ItemLoader.fluidchamber),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);		
		
		//ITEM INTERFACE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.iteminterface),
				"SMS",
				"CCC",
				"PPP",
				'S',new ItemStack(ItemLoader.screw),
				'M',new ItemStack(ItemLoader.mjconversionunit),
				'C',new ItemStack(ItemLoader.controlinterface),
				'P',new ItemStack(ItemLoader.pcbpad)
				);			
		
		//IO PORT EXPANSION CONNECTOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.ioportexpansionconnector),
				"RSR",
				"GCG",
				"PSP",
				'S',new ItemStack(ItemLoader.screw),
				'R',new ItemStack(Blocks.redstone_torch),
				'C',new ItemStack(ItemLoader.centralassembly),
				'P',new ItemStack(ItemLoader.pcbpad),
				'G',new ItemStack(Items.gold_ingot)
				);				
		
		
		//=====SERVICE BAY=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.servicebay),
				"SIS",
				"GCG",
				"SGS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'I',new ItemStack(ItemLoader.ioportexpansionconnector),
				'G',new ItemStack(ItemLoader.iteminterface)
				);				
		
		//ELECTRODE CONNECTOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.electrodeconnector,10),
				"SII",
				"00G",
				"SII",
				'S',new ItemStack(ItemLoader.screw),
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'G',new ItemStack(Items.gold_ingot)
				);
		
		
		//=====DIAPHRAGMAL ELECTROLYZER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.diaphragmalelectrolyzer),
				"NNN",
				"RCR",
				"SFS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'F',new ItemStack(ItemLoader.fluidchamber),
				'R',new ItemStack(ItemLoader.electrodeconnector),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);		
		
		//GRINDING GEAR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.grindinggear),
				"00I",
				"SGS",
				"I00",
				'S',new ItemStack(ItemLoader.screw),
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'G',new ItemStack(BuildCraftCore.ironGearItem));
		
		
		//PNEUMATIC ACTUATOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.actuator),
				"SSS",
				"IMG",
				"SSS",
				'S', new ItemStack(ItemLoader.screw),
				'I', TierLoader.L0_WroughtIron.getTargetItem(),
				'M', new ItemStack(ItemLoader.mjconversionunit),
				'G', new ItemStack(BuildCraftCore.stoneGearItem)
				);
		
		
		//=====CRUSHER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.crusher),
				"NNN",
				"ACA",
				"SPS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'N',new ItemStack(ItemLoader.grindinggear),
				'A',new ItemStack(ItemLoader.actuator),
				'P',new ItemStack(ItemLoader.panel)
				);			
		
		//=====CENTRIFUGAL EXTRACTOR=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.centriextractor),
				"ASA",
				"FCF",
				"ASA",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'F',new ItemStack(ItemLoader.fluidchamber),
				'A',new ItemStack(ItemLoader.actuator)
				);		
		
		//REPLACABLE SLEIGHT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.replacablesleigh),
				"III",
				"000",
				"III",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.replacablesleigh,3),
				"III",
				"000",
				"III",
				'I',new ItemStack(Items.iron_ingot)
				);		
		
		
		//=====CATALYTIC TANK=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.cattank),
				"TST",
				"MCM",
				"FFF",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'F',new ItemStack(ItemLoader.fluidchamber),
				'M',new ItemStack(ItemLoader.fluidmixer),
				'T',new ItemStack(ItemLoader.replacablesleigh)
				);			
		
		//=====BIOGRINDER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.biogrinder),
				"HNH",
				"ACA",
				"SPS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'N',new ItemStack(ItemLoader.grindinggear),
				'A',new ItemStack(ItemLoader.actuator),
				'P',new ItemStack(ItemLoader.panel),
				'H',new ItemStack(ItemLoader.presshead)
				);
		
		//PUMP
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.airpump),
				"SSS",
				"CRM",
				"SSS",
				'C',new ItemStack(ItemLoader.pumpcasing),
				'R',new ItemStack(ItemLoader.pumphelicalrotor),
				'M',new ItemStack(ItemLoader.pumpmotor),
				'S',new ItemStack(ItemLoader.screw)
		);
		//PUMP MOTOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pumpmotor),
				"S0S",
				"GMG",
				"S0S",
				'S',new ItemStack(ItemLoader.screw),
				'M',new ItemStack(ItemLoader.mjconversionunit),
				'G',new ItemStack(BuildCraftCore.ironGearItem)
				);
		//PUMP CASING
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pumpcasing),
				"III",
				"GGG",
				"III",
				'I',TierLoader.L0_WroughtIron.getTargetItem(),
				'G',new ItemStack(BuildCraftCore.stoneGearItem)
				);
		//HELICAL ROTOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pumphelicalrotor),
				"I0I",
				"III",
				"0I0",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pumphelicalrotor),
				"0I0",
				"III",
				"I0I",
				'I',TierLoader.L0_WroughtIron.getTargetItem()
				);		
		
		
		//=====AIR COLLECTOR=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.aircollector),
				"SNS",
				"SCS",
				"SPS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(ItemLoader.airpump),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);		
		
		//=====HVLC FILLER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.hvlcfiller),
				"SNS",
				"FCF",
				"SLS",
				'S',new ItemStack(ItemLoader.screw),
				'N',new ItemStack(ItemLoader.pressurenozzle),
				'F',new ItemStack(ItemLoader.frame),
				'C',new ItemStack(BlockLoader.casing),
				'L',new ItemStack(ItemLoader.fluidchamber));
		
		//=====GAS CHIMNEY=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.gaschimney),
				"BFB",
				"BFB",
				"BFB",
				'B',new ItemStack(Items.brick),
				'F',new ItemStack(ItemLoader.fluidchamber)
		);
		
		//=====METAL FORMER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.metformer),
				"APA",
				"HCH",
				"SFS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'F',new ItemStack(ItemLoader.fluidchamber),
				'P',new ItemStack(ItemLoader.presshead),
				'H',new ItemStack(ItemLoader.heatingelement),
				'A',new ItemStack(ItemLoader.actuator)
		);		
		
		//=====MIXER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.mixer),
				"MNM",
				"MCM",
				"MFM",
				'C',new ItemStack(BlockLoader.casing),
				'M',new ItemStack(ItemLoader.fluidmixer),
				'N',new ItemStack(ItemLoader.pressurenozzle),
				'F',new ItemStack(ItemLoader.fluidchamber)
		);		
		
		//=====AUTOMATIC DRAWPLATE=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.automaticdrawplate),
				"PDP",
				"ACA",
				"SRS",
				'C',new ItemStack(BlockLoader.casing),
				'D',new ItemStack(ItemLoader.diamonddrawplateassembly),
				'R',new ItemStack(ItemLoader.replacablesleigh),
				'P',new ItemStack(ItemLoader.presshead),
				'A',new ItemStack(ItemLoader.actuator),
				'S',new ItemStack(ItemLoader.screw)
		);				
		
		//=====QUENCHER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.quencher),
				"PUP",
				"MCM",
				"FFF",
				'C',new ItemStack(BlockLoader.casing),
				'F',new ItemStack(ItemLoader.fluidchamber),
				'P',new ItemStack(ItemLoader.pressurenozzle),
				'M',new ItemStack(ItemLoader.fluidmixer),
				'U',new ItemStack(ItemLoader.frame)
		);	
		
		//=====SYNDICATION HUB=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.syndicationhub),
				"SPS",
				"MCM",
				"SMS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(ItemLoader.syndicationcpuunit),
				'M',new ItemStack(ItemLoader.multiportconnector)
		);		
		
		//=====ITEMPORT=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.syndication_itemport),
				"SPS",
				"ICI",
				"SMS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(ItemLoader.frame),
				'M',new ItemStack(ItemLoader.multiportconnector),
				'I',new ItemStack(ItemLoader.ioportexpansionconnector)
		);				
		//=====EMF GENERATOR=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.syndication_emfgenerator),
				"SPS",
				"ECE",
				"SSS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(ItemLoader.frame),
				'E',new ItemStack(ItemLoader.mjconversionunit)
		);	
		//=====EMF CAPACITOR=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.syndication_util_capacitor),
				"SSS",
				"PCP",
				"SSS",
				'C',new ItemStack(BlockLoader.casing),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(Items.ender_pearl)
		);	
		//=====SYNDICATION BUS MULTIPIPE=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.pipes_syndicationbus,16),
				"SSS",
				"MPM",
				"SSS",
				'M',new ItemStack(ItemLoader.multiportconnector),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(BuildCraftTransport.pipeItemsGold)
		);			
		
		//=====WIRE COATING EXTRUDER=====
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.wcextruder),
				"NTN",
				"PCP",
				"SFS",
				'C',new ItemStack(BlockLoader.casing),
				'T',new ItemStack(ItemLoader.fluidchamber),
				'P',new ItemStack(ItemLoader.presshead),
				'N',new ItemStack(ItemLoader.pressurenozzle),
				'F',new ItemStack(ItemLoader.frame),
				'S',new ItemStack(ItemLoader.screw)
		);				
		
		
		
	}

}
