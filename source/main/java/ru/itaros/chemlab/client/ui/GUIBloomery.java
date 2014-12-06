package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.BloomeryTileEntity;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIBloomery extends GUIHOEClassicalMachine {

	public GUIBloomery(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(BloomeryTileEntity)tile);
	}
	private GUIBloomery(InventoryPlayer playerInv, BloomeryTileEntity tile) {
		super(new BloomeryContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Bloomery";
	}	

	@Override
	protected void DrawGauges(HOEMachineData data, int mx, int my) {
		super.DrawGauges(data, mx, my);
	}
	
}
