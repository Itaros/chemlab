package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUIBiogrinder extends GUIHOEClassicalMachine {

	
	public GUIBiogrinder(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(BiogrinderTileEntity)tile);
	}
	private GUIBiogrinder(InventoryPlayer playerInv, BiogrinderTileEntity tile) {
		super(new BiogrinderContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	@Override
	public String getMachineUnlocalizedName() {
		return "Biogrinder";
	}
	

	
	

}
