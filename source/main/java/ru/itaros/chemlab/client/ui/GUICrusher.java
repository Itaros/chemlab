package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.CrusherTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUICrusher extends GUIHOEClassicalMachine {

	public GUICrusher(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(CrusherTileEntity)tile);
	}
	private GUICrusher(InventoryPlayer playerInv, CrusherTileEntity tile) {
		super(new CrusherContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Crusher";
	}	


}
