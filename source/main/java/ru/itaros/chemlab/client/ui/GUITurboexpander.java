package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.TurboexpanderTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUITurboexpander extends GUIHOEClassicalMachine {

	public GUITurboexpander(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(TurboexpanderTileEntity)tile);
	}
	private GUITurboexpander(InventoryPlayer playerInv, TurboexpanderTileEntity tile) {
		super(new TurboexpanderContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Turboexpander";
	}

}
