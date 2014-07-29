package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.ImpregnatorTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIImpregnator extends GUIHOEClassicalMachine {

	public GUIImpregnator(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(ImpregnatorTileEntity)tile);
	}
	private GUIImpregnator(InventoryPlayer playerInv, ImpregnatorTileEntity tile) {
		super(new ImpregnatorContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "HPIC";
	}	
	

}
