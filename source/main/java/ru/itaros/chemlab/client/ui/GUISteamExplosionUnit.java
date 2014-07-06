package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.SteamExplosionUnitTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUISteamExplosionUnit extends GUIHOEClassicalMachine {

	public GUISteamExplosionUnit(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(SteamExplosionUnitTileEntity)tile);
	}
	private GUISteamExplosionUnit(InventoryPlayer playerInv, SteamExplosionUnitTileEntity tile) {
		super(new SteamExplosionUnitContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Steam Explosion Unit";
	}	

}
