package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.MixerTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIMixer extends GUIHOEClassicalMachine {

	public GUIMixer(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(MixerTileEntity)tile);
	}
	private GUIMixer(InventoryPlayer playerInv, MixerTileEntity tile) {
		super(new MixerContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Mixer";
	}	

}
