package ru.itaros.chemlab.proxy;

import net.minecraft.client.entity.EntityClientPlayerMP;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.tesr.ParticleInjectorHackTESR;
import ru.itaros.chemlab.client.ui.GUIToolProgrammer;
import ru.itaros.chemlab.loader.client.ISBRLoader;
import ru.itaros.chemlab.tiles.GasChimneyTileEntity;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;

public class Client extends Server {

	//@Override
	//public void initLinker() {
	//	//NOP
	//}

	//@Override
	//public HOELinker getLinker() {
	//	//NOP
	//	return null;
	//}

	@Override
	public void openProgrammerGUI(MachineCrafterTileEntity tile) {
		EntityClientPlayerMP player = FMLClientHandler.instance().getClientPlayerEntity();
		FMLClientHandler.instance().displayGuiScreen(player, new GUIToolProgrammer(tile));
		
	}

	@Override
	public void registerGFX() {
		
		if(ChemLab.getConfig().gfx_AdvancedParticleInjectorHack){
			ClientRegistry.bindTileEntitySpecialRenderer(GasChimneyTileEntity.class, new ParticleInjectorHackTESR());
		}
		
		ISBRLoader.load();
		
		
		super.registerGFX();
	}
	
	
	

}
