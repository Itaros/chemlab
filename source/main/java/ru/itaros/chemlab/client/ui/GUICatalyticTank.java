package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.CatalyticTankTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class GUICatalyticTank extends GUIHOEClassicalMachine {

	public GUICatalyticTank(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(CatalyticTankTileEntity)tile);
	}
	private GUICatalyticTank(InventoryPlayer playerInv, CatalyticTankTileEntity tile) {
		super(new CatalyticTankContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	@Override
	public String getMachineUnlocalizedName() {
		return "Catalytic Tank";
	}

}
