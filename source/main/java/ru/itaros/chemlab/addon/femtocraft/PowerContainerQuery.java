package ru.itaros.chemlab.addon.femtocraft;

import ru.itaros.hoe.tiles.MachineTileEntity;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.power.PowerContainer;

import cpw.mods.fml.common.Loader;

public class PowerContainerQuery {

	public final static void providePowerContainer(MachineTileEntity hoemachine){
		if(isFemtoCraftAvailable){
			hoemachine.setFemtocraftPowerCell(new PowerContainer(EnumTechLevel.MACRO,200));
		}
	}
	
	
	public final static int FEMTO_TO_AE_RATIO=(int)(500F/80F);


	public static final double FEMTO_TO_AE_RATIO_DOUBLED = FEMTO_TO_AE_RATIO*2D;


	private static boolean isFemtoCraftAvailable;
	
	public static boolean isFemtoCraftAvailable() {
		return isFemtoCraftAvailable;
	}
	public static void inspectFML(){
		isFemtoCraftAvailable = Loader.isModLoaded("Femtocraft");
	}
	
	
	
}
