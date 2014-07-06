package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.DiaphragmalElectrolyzerTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUIDiaphragmalElectrolyzer extends GUIHOEClassicalMachine {

	public GUIDiaphragmalElectrolyzer(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(DiaphragmalElectrolyzerTileEntity)tile);
	}
	private GUIDiaphragmalElectrolyzer(InventoryPlayer playerInv, DiaphragmalElectrolyzerTileEntity tile) {
		super(new DiaphragmalElectrolyzerContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	@Override
	public String getMachineUnlocalizedName() {
		return "Diaphragmal Electrolyzer";
	}

}
