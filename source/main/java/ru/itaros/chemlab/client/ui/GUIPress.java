package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.PressTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;


public class GUIPress extends GUIHOEClassicalMachine {
	public GUIPress(InventoryPlayer playerInv, MachineCrafterTileEntity tile){
		this(playerInv,(PressTileEntity)tile);
	}
	private GUIPress(InventoryPlayer playerInv, PressTileEntity tile) {
		super(new PressContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Press";
	}	
}
