package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.HeatingFurnaceTileEntity;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIHeatingFurnace extends GUIHOEClassicalMachine {

	public GUIHeatingFurnace(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(HeatingFurnaceTileEntity)tile);
	}
	private GUIHeatingFurnace(InventoryPlayer playerInv, HeatingFurnaceTileEntity tile) {
		super(new HeatingFurnaceContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Heating Furnace";
	}	

	@Override
	protected void DrawGauges(HOEMachineData data, int mx, int my) {
		super.DrawGauges(data, mx, my);
	}
	@Override
	protected String getUITexturePath() {
		return "textures/gui/bloomeryhoemachine.png";
	}

}
