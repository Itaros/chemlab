package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.WasherTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class GUIWasher extends GUIHOEClassicalMachine {

	public GUIWasher(InventoryPlayer playerInv, MachineCrafterTileEntity tile){
		this(playerInv,(WasherTileEntity)tile);
	}
	private GUIWasher(InventoryPlayer playerInv, WasherTileEntity tile) {
		super(new WasherContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Washer";
	}	
	
	

}
