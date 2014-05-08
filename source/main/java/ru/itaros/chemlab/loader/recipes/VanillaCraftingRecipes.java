package ru.itaros.chemlab.loader.recipes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import buildcraft.BuildCraftCore;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaCraftingRecipes {

	public static void load() {
		
		//Buildcraft helper recipes
		GameRegistry.addShapedRecipe(new ItemStack(BuildCraftCore.ironGearItem),
				"SIS",
				"IWI",
				"SIS",
				'W', new ItemStack(BuildCraftCore.stoneGearItem),
				'I', new ItemStack(ItemLoader.wroughtiron),//That is only wrought recipe
				'S', new ItemStack(ItemLoader.screw)
				);
		
		//Vanilla piston helper recipe
		ShapedOreRecipe pistonHelperRecipe = new ShapedOreRecipe(new ItemStack(Blocks.piston),
				"WWW",
				"CIC",
				"CRC",
				'I',new ItemStack(ItemLoader.wroughtiron),
				'C',new ItemStack(Blocks.cobblestone),
				'R',new ItemStack(Items.redstone),
				'W',"plankWood");
		CraftingManager.getInstance().getRecipeList().add(pistonHelperRecipe);
		
		
		//Tools
		
		//~~~PROGRAMMER~~~
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.programmer),
				"SSS",
				"IPI",
				"GGG",
				'S',new ItemStack(ItemLoader.screw),
				'I',new ItemStack(ItemLoader.wroughtiron),
				'P',new ItemStack(ItemLoader.pcbpad),
				'G',new ItemStack(Items.gold_nugget)
				);
		
		//~~~WRENCH~~~
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.wrench),
				"0II",
				"00I",
				"00I",
				'I',new ItemStack(ItemLoader.wroughtiron)
				);
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.wrench),
				"II0",
				"I00",
				"I00",
				'I',new ItemStack(ItemLoader.wroughtiron)
				);
				
		//~~~HVLC~~~
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.emptyhvlc,16),
				"ICI",
				"ICI",
				"INI",
				'I',new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron)
				);		
		
		//SCREW - WROUGHT IRON
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.screw,16),
				"III",
				"0I0",
				"0I0",
				'I',new ItemStack(ItemLoader.wroughtiron)
				);	
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
		//PANEL - WROUGHT IRON
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.panel),
				"III",
				"III",
				"III",
				'I',new ItemStack(ItemLoader.wroughtiron)
				);				
		
		
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
				'I',new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron)
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
				'I',new ItemStack(ItemLoader.wroughtiron)
				);	
		//HEATING ELEMENT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.heatingelement),
				"CCI",
				"IGI",
				"ICC",
				'I',new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron)
				);			
		
		//MULTICOMPARTMENT
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.multicompartment),
				"ITI",
				"TTT",
				"ITI",
				'I',new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron),
				'N',new ItemStack(ItemLoader.pressurenozzle)
				);	
		//FLUID MIXER
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.fluidmixer),
				"I0I",
				"0G0",
				"I0I",
				'I',new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron)
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
				'I',new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron),
				'G',new ItemStack(BuildCraftCore.ironGearItem));
		
		
		//PNEUMATIC ACTUATOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.actuator),
				"SSS",
				"IMG",
				"SSS",
				'S', new ItemStack(ItemLoader.screw),
				'I', new ItemStack(ItemLoader.wroughtiron),
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
				'I',new ItemStack(ItemLoader.wroughtiron)
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
				'I',new ItemStack(ItemLoader.wroughtiron),
				'G',new ItemStack(BuildCraftCore.stoneGearItem)
				);
		//HELICAL ROTOR
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pumphelicalrotor),
				"I0I",
				"III",
				"0I0",
				'I',new ItemStack(ItemLoader.wroughtiron)
				);
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.pumphelicalrotor),
				"0I0",
				"III",
				"I0I",
				'I',new ItemStack(ItemLoader.wroughtiron)
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
		
		
		
		
	}

}
