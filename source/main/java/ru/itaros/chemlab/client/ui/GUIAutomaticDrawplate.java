package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.AutomaticDrawplateTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIAutomaticDrawplate extends GUIHOEClassicalMachine {

	public GUIAutomaticDrawplate(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(AutomaticDrawplateTileEntity)tile);
	}
	private GUIAutomaticDrawplate(InventoryPlayer playerInv, AutomaticDrawplateTileEntity tile) {
		super(new AutomaticDrawplateContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Automatic Drawplate";
	}

}
