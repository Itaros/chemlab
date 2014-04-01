package ru.itaros.chemlab.proxy;

import net.minecraft.client.entity.EntityClientPlayerMP;
import cpw.mods.fml.client.FMLClientHandler;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.client.ui.GUIToolProgrammer;

public class Client extends Proxy {

	@Override
	public void initLinker() {
		//NOP
	}

	@Override
	public HOELinker getLinker() {
		//NOP
		return null;
	}

	@Override
	public void openProgrammerGUI() {
		EntityClientPlayerMP player = FMLClientHandler.instance().getClientPlayerEntity();
		FMLClientHandler.instance().displayGuiScreen(player, new GUIToolProgrammer());
		
	}

}
