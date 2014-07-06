package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.EvaporationUnitTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUIEvaporationUnit extends GUIHOEClassicalMachine {

	public GUIEvaporationUnit(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(EvaporationUnitTileEntity)tile);
	}
	private GUIEvaporationUnit(InventoryPlayer playerInv, EvaporationUnitTileEntity tile) {
		super(new EvaporationUnitContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Evaporation Unit";
	}	

}
