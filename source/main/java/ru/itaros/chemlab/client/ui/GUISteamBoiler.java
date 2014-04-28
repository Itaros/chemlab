package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.SteamBoilerTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class GUISteamBoiler extends GUIHOEClassicalMachine {
	public GUISteamBoiler(InventoryPlayer playerInv, MachineCrafterTileEntity tile){
		this(playerInv,(SteamBoilerTileEntity)tile);
	}
	private GUISteamBoiler(InventoryPlayer playerInv, SteamBoilerTileEntity tile) {
		super(new SteamBoilerContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Steam Boiler";
	}	

}
