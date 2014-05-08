package ru.itaros.chemlab.proxy;

import net.minecraft.client.entity.EntityClientPlayerMP;
import ru.itaros.chemlab.client.ui.GUIToolProgrammer;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import cpw.mods.fml.client.FMLClientHandler;

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

}
