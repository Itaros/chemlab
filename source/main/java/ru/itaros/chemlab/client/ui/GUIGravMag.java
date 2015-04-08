package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.AirCollectorTileEntity;
import ru.itaros.chemlab.tiles.GravMagTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIGravMag extends GUIHOEClassicalMachine {

	public GUIGravMag(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(GravMagTileEntity)tile);
	}
	private GUIGravMag(InventoryPlayer playerInv, GravMagTileEntity tile) {
		super(new GravMagContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "GraviMagnetic Separator";
	}	
}
