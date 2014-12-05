package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.blocks.multiblock.MultiblockPointerTileEntity;
import ru.itaros.chemlab.hoe.io.*;
import ru.itaros.chemlab.hoe.io.special.ArcFurnaceControllerIO;
import ru.itaros.chemlab.hoe.io.special.GasChimneyIO;
import ru.itaros.chemlab.hoe.io.syndication.SyndicationEMFGeneratorIO;
import ru.itaros.chemlab.hoe.io.syndication.SyndicationHubIO;
import ru.itaros.chemlab.hoe.io.syndication.SyndicationItemPortIO;
import ru.itaros.chemlab.tiles.*;
import ru.itaros.chemlab.tiles.syndication.SyndicationCapacitorTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationEMFGeneratorTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationHubTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationItemPortTileEntity;
import ru.itaros.chemlab.tiles.syndication.SyndicationPipingTileEntity;
import ru.itaros.hoe.utils.IOCollectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityLoader {

	public static IOCollectionHelper load(){
		IOCollectionHelper iocollection = new IOCollectionHelper(
    			new BiogrinderIO().setHostBlock(BlockLoader.biogrinder),
    			new CentrifugalExtractorIO().setHostBlock(BlockLoader.centriextractor),
    			new WasherIO().setHostBlock(BlockLoader.washer),
    			new ImpregnatorIO().setHostBlock(BlockLoader.impregnator),
    			new PressIO().setHostBlock(BlockLoader.press),
    			new SteamBoilerIO().setHostBlock(BlockLoader.steamboiler),
    			new SteamExplosionUnitIO().setHostBlock(BlockLoader.steamexplosionunit),
    			new CrusherIO().setHostBlock(BlockLoader.crusher),
    			new DiaphragmalElectrolyzerIO().setHostBlock(BlockLoader.diaphragmalelectrolyzer),
    			new HiTFurnaceIO().setHostBlock(BlockLoader.furnace),
    			new AirCollectorIO().setHostBlock(BlockLoader.aircollector),
    			new FluidCompressorIO().setHostBlock(BlockLoader.fluidcompressor),
    			new TurboexpanderIO().setHostBlock(BlockLoader.turboexpander),
    			new EvaporationUnitIO().setHostBlock(BlockLoader.evaporationunit),
    			new CatalyticTankIO().setHostBlock(BlockLoader.cattank),
    			new HiResistantMixerIO().setHostBlock(BlockLoader.hiresistmixer),
    			new ServiceBayIO().setHostBlock(BlockLoader.servicebay),
    			new GravMagIO().setHostBlock(BlockLoader.gravmag),
    			//new HVLCFillerIO().setHostBlock(BlockLoader.hvlcfiller),
    			new GasChimneyIO().setHostBlock(BlockLoader.gaschimney),
    			new SyndicationHubIO().setHostBlock(BlockLoader.syndicationhub),
    			new SyndicationEMFGeneratorIO().setHostBlock(BlockLoader.syndication_emfgenerator),
    			new SyndicationItemPortIO().setHostBlock(BlockLoader.syndication_itemport),
    			new MixerIO().setHostBlock(BlockLoader.mixer),
    			new AutomaticDrawplateIO().setHostBlock(BlockLoader.automaticdrawplate),
    			new QuenchingChamberIO().setHostBlock(BlockLoader.quencher),
    			new MetalFormationMachineIO().setHostBlock(BlockLoader.metformer),
    			new WireCoatingExtruderIO().setHostBlock(BlockLoader.wcextruder),
    			new ArcFurnaceControllerIO().setHostBlock(BlockLoader.controller_arcFurnace),
    			new BloomeryIO().setHostBlock(BlockLoader.bloomery)
    			);
		iocollection.registerInHOE();
		
		
		
		GameRegistry.registerTileEntity(BiogrinderTileEntity.class, BiogrinderTileEntity.class.getName());
		GameRegistry.registerTileEntity(CentrifugalExtractorTileEntity.class, CentrifugalExtractorTileEntity.class.getName());
		GameRegistry.registerTileEntity(WasherTileEntity.class, WasherTileEntity.class.getName());
		GameRegistry.registerTileEntity(ImpregnatorTileEntity.class, ImpregnatorTileEntity.class.getName());
		GameRegistry.registerTileEntity(PressTileEntity.class, PressTileEntity.class.getName());
		GameRegistry.registerTileEntity(SteamBoilerTileEntity.class, SteamBoilerTileEntity.class.getName());
		GameRegistry.registerTileEntity(SteamExplosionUnitTileEntity.class, SteamExplosionUnitTileEntity.class.getName());
		GameRegistry.registerTileEntity(CrusherTileEntity.class, CrusherTileEntity.class.getName());
		GameRegistry.registerTileEntity(DiaphragmalElectrolyzerTileEntity.class,DiaphragmalElectrolyzerTileEntity.class.getName());
		GameRegistry.registerTileEntity(HiTFurnaceTileEntity.class,HiTFurnaceTileEntity.class.getName());
		GameRegistry.registerTileEntity(AirCollectorTileEntity.class,AirCollectorTileEntity.class.getName());
		GameRegistry.registerTileEntity(FluidCompressorTileEntity.class,FluidCompressorTileEntity.class.getName());
		GameRegistry.registerTileEntity(TurboexpanderTileEntity.class,TurboexpanderTileEntity.class.getName());
		GameRegistry.registerTileEntity(EvaporationUnitTileEntity.class,EvaporationUnitTileEntity.class.getName());
		GameRegistry.registerTileEntity(CatalyticTankTileEntity.class,CatalyticTankTileEntity.class.getName());
		GameRegistry.registerTileEntity(HiResistantMixerTileEntity.class,HiResistantMixerTileEntity.class.getName());
		GameRegistry.registerTileEntity(ServiceBayTileEntity.class,ServiceBayTileEntity.class.getName());
		GameRegistry.registerTileEntity(GravMagTileEntity.class,GravMagTileEntity.class.getName());
		//GameRegistry.registerTileEntity(HVLCFillerTileEntity.class,HVLCFillerTileEntity.class.getName());
		GameRegistry.registerTileEntity(GasChimneyTileEntity.class,GasChimneyTileEntity.class.getName());
		GameRegistry.registerTileEntity(BloomeryTileEntity.class,BloomeryTileEntity.class.getName());
		
		GameRegistry.registerTileEntity(SyndicationHubTileEntity.class,SyndicationHubTileEntity.class.getName());
		
		
		GameRegistry.registerTileEntity(SyndicationPipingTileEntity.class,SyndicationPipingTileEntity.class.getName());
		GameRegistry.registerTileEntity(SyndicationCapacitorTileEntity.class,SyndicationCapacitorTileEntity.class.getName());
		GameRegistry.registerTileEntity(SyndicationEMFGeneratorTileEntity.class,SyndicationEMFGeneratorTileEntity.class.getName());
		GameRegistry.registerTileEntity(SyndicationItemPortTileEntity.class,SyndicationItemPortTileEntity.class.getName());

		GameRegistry.registerTileEntity(MixerTileEntity.class,MixerTileEntity.class.getName());
		GameRegistry.registerTileEntity(AutomaticDrawplateTileEntity.class,AutomaticDrawplateTileEntity.class.getName());
		GameRegistry.registerTileEntity(QuenchingChamberTileEntity.class,QuenchingChamberTileEntity.class.getName());
		GameRegistry.registerTileEntity(MetalFormationMachineTileEntity.class,MetalFormationMachineTileEntity.class.getName());

		GameRegistry.registerTileEntity(WireCoatingExtruderTileEntity.class,WireCoatingExtruderTileEntity.class.getName());
		
		GameRegistry.registerTileEntity(ArcFurnaceControllerTileEntity.class,ArcFurnaceControllerTileEntity.class.getName());
		//GameRegistry.registerTileEntity(.class,.class.getName());
		
		//TODO: Move to HOE
		GameRegistry.registerTileEntity(MultiblockPointerTileEntity.class,MultiblockPointerTileEntity.class.getName());
		
		return iocollection;
	}
	
}
