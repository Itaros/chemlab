package ru.itaros.chemlab.client.ui;

import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import net.minecraft.entity.player.InventoryPlayer;

public class GUIBiogrinder extends GUIHOEClassicalMachine {

	public static final int ID = 1;
	public GUIBiogrinder(InventoryPlayer playerInv, BiogrinderTileEntity tile) {
		super(new BiogrinderContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	@Override
	public String getMachineUnlocalizedName() {
		return "Biogrinder";
	}	
	

}
