package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.FurnaceTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;

public class GUIFurnace extends GUIHOEClassicalMachine {

	public GUIFurnace(InventoryPlayer playerInv, MachineCrafterTileEntity tile){
		this(playerInv,(FurnaceTileEntity)tile);
	}
	private GUIFurnace(InventoryPlayer playerInv, FurnaceTileEntity tile) {
		super(new FurnaceContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Electric High Temperature Furnace";
	}	

}
