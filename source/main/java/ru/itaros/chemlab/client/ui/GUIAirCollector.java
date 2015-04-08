package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.AirCollectorTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIAirCollector extends GUIHOEClassicalMachine {

	public GUIAirCollector(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(AirCollectorTileEntity)tile);
	}
	private GUIAirCollector(InventoryPlayer playerInv, AirCollectorTileEntity tile) {
		super(new AirCollectorContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Air Collector";
	}	

}
