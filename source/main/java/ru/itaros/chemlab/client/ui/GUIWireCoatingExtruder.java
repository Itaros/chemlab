package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.WireCoatingExtruderTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUIWireCoatingExtruder extends GUIHOEClassicalMachine {

	public GUIWireCoatingExtruder(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(WireCoatingExtruderTileEntity)tile);
	}
	private GUIWireCoatingExtruder(InventoryPlayer playerInv, WireCoatingExtruderTileEntity tile) {
		super(new WireCoatingExtruderContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Wire Coating Extruder";
	}	

}
