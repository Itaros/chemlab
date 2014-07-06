package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.HiTFurnaceTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUIFurnace extends GUIHOEClassicalMachine {

	public GUIFurnace(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(HiTFurnaceTileEntity)tile);
	}
	private GUIFurnace(InventoryPlayer playerInv, HiTFurnaceTileEntity tile) {
		super(new FurnaceContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Electric High Temperature Furnace";
	}	

}
