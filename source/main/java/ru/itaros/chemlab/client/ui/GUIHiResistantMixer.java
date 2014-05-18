package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.HiResistantMixerTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class GUIHiResistantMixer extends GUIHOEClassicalMachine {

	public GUIHiResistantMixer(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(HiResistantMixerTileEntity)tile);
	}
	private GUIHiResistantMixer(InventoryPlayer playerInv, HiResistantMixerTileEntity tile) {
		super(new HiResistantMixerContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Hi-Resistant Mixer";
	}	

}
