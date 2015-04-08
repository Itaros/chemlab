package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.FrothCellTileEntity;
import ru.itaros.chemlab.tiles.GravMagTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIFrothCell extends GUIHOEClassicalMachine {

	public GUIFrothCell(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(FrothCellTileEntity)tile);
	}
	private GUIFrothCell(InventoryPlayer playerInv, FrothCellTileEntity tile) {
		super(new FrothCellContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Froth Flotation Cell";
	}	
}
