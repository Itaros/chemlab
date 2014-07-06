package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.minecraft.tileentity.FluidCompressorTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;

public class GUIFluidCompressor extends GUIHOEClassicalMachine {

	public GUIFluidCompressor(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(FluidCompressorTileEntity)tile);
	}
	private GUIFluidCompressor(InventoryPlayer playerInv, FluidCompressorTileEntity tile) {
		super(new FluidCompressorContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	@Override
	public String getMachineUnlocalizedName() {
		return "Fluid Compressor";
	}

}
