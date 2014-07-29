package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.QuenchingChamberTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIQuenchingChamber extends GUIHOEClassicalMachine {

	public GUIQuenchingChamber(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(QuenchingChamberTileEntity)tile);
	}
	private GUIQuenchingChamber(InventoryPlayer playerInv, QuenchingChamberTileEntity tile) {
		super(new QuenchingChamberContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Quenching Chamber";
	}	

}
