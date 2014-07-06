package ru.itaros.chemlab.loader.tileentity;

import ru.itaros.chemlab.hoe.AirCollectorIO;
import ru.itaros.chemlab.hoe.AutomaticDrawplateIO;
import ru.itaros.chemlab.hoe.BiogrinderIO;
import ru.itaros.chemlab.hoe.CatalyticTankIO;
import ru.itaros.chemlab.hoe.CentrifugalExtractorIO;
import ru.itaros.chemlab.hoe.CrusherIO;
import ru.itaros.chemlab.hoe.DiaphragmalElectrolyzerIO;
import ru.itaros.chemlab.hoe.EvaporationUnitIO;
import ru.itaros.chemlab.hoe.FluidCompressorIO;
import ru.itaros.chemlab.hoe.HiResistantMixerIO;
import ru.itaros.chemlab.hoe.HiTFurnaceIO;
import ru.itaros.chemlab.hoe.ImpregnatorIO;
import ru.itaros.chemlab.hoe.MetalFormationMachineIO;
import ru.itaros.chemlab.hoe.MixerIO;
import ru.itaros.chemlab.hoe.PressIO;
import ru.itaros.chemlab.hoe.QuenchingChamberIO;
import ru.itaros.chemlab.hoe.ServiceBayIO;
import ru.itaros.chemlab.hoe.SteamBoilerIO;
import ru.itaros.chemlab.hoe.SteamExplosionUnitIO;
import ru.itaros.chemlab.hoe.TurboexpanderIO;
import ru.itaros.chemlab.hoe.WasherIO;
import ru.itaros.chemlab.hoe.WireCoatingExtruderIO;
import ru.itaros.chemlab.hoe.special.GasChimneyIO;
import ru.itaros.chemlab.hoe.special.HVLCFillerIO;
import ru.itaros.chemlab.hoe.syndication.SyndicationEMFGeneratorIO;
import ru.itaros.chemlab.hoe.syndication.SyndicationHubIO;
import ru.itaros.chemlab.hoe.syndication.SyndicationItemPortIO;
import ru.itaros.chemlab.minecraft.tileentity.*;
import ru.itaros.chemlab.minecraft.tileentity.syndication.*;
import ru.itaros.toolkit.hoe.io.IOCollectionHelper;
import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntityLoader {

	public static IOCollectionHelper load(){
		IOCollectionHelper iocollection = new IOCollectionHelper(
    			new BiogrinderIO(),
    			new CentrifugalExtractorIO(),
    			new WasherIO(),
    			new ImpregnatorIO(),
    			new PressIO(),
    			new SteamBoilerIO(),
    			new SteamExplosionUnitIO(),
    			new CrusherIO(),
    			new DiaphragmalElectrolyzerIO(),
    			new HiTFurnaceIO(),
    			new AirCollectorIO(),
    			new FluidCompressorIO(),
    			new TurboexpanderIO(),
    			new EvaporationUnitIO(),
    			new CatalyticTankIO(),
    			new HiResistantMixerIO(),
    			new ServiceBayIO(),
    			new HVLCFillerIO(),
    			new GasChimneyIO(),
    			new SyndicationHubIO(),
    			new SyndicationEMFGeneratorIO(),
    			new SyndicationItemPortIO(),
    			new MixerIO(),
    			new AutomaticDrawplateIO(),
    			new QuenchingChamberIO(),
    			new MetalFormationMachineIO(),
    			new WireCoatingExtruderIO()
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
		GameRegistry.registerTileEntity(HVLCFillerTileEntity.class,HVLCFillerTileEntity.class.getName());
		GameRegistry.registerTileEntity(GasChimneyTileEntity.class,GasChimneyTileEntity.class.getName());

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
		//GameRegistry.registerTileEntity(.class,.class.getName());
		
		return iocollection;
	}
	
}
