package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.WasherTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIWasher extends GUIHOEClassicalMachine {

	public GUIWasher(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(WasherTileEntity)tile);
	}
	private GUIWasher(InventoryPlayer playerInv, WasherTileEntity tile) {
		super(new WasherContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Washer";
	}	
	
	

}
