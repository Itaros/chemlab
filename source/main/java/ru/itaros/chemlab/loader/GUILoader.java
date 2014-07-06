package ru.itaros.chemlab.loader;

import ru.itaros.chemlab.client.ui.*;
import ru.itaros.chemlab.client.ui.special.*;
import ru.itaros.chemlab.client.ui.syndication.*;
import ru.itaros.chemlab.client.ui.common.GUIHandler;

public class GUILoader {
	
	@SuppressWarnings("unchecked")
	public static void loadGUIs(){
		GUIHandler.registerGUIs(
				BiogrinderContainer.class,
				CentrifugalExtractorContainer.class,
				WasherContainer.class,
				ImpregnatorContainer.class,
				PressContainer.class,
				SteamBoilerContainer.class,
				SteamExplosionUnitContainer.class,
				CrusherContainer.class,
				DiaphragmalElectrolyzerContainer.class,
				FurnaceContainer.class,
				AirCollectorContainer.class,
				FluidCompressorContainer.class,
				TurboexpanderContainer.class,
				EvaporationUnitContainer.class,
				CatalyticTankContainer.class,
				HiResistantMixerContainer.class,
				HVLCFillerContainer.class,
				GasChimneyContainer.class,
				SyndicationHubContainer.class,
				SyndicationItemPortContainer.class,
				MixerContainer.class,
				AutomaticDrawplateContainer.class,
				QuenchingChamberContainer.class,
				MetalFormationMachineContainer.class,
				WireCoatingExtruderContainer.class
				);
	}
	
}
