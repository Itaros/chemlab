package ru.itaros.chemlab.client.ui;

import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

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
