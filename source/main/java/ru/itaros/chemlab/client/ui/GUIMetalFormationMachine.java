package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.MetalFormationMachineTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.chemlab.client.ui.MetalFormationMachineContainer;

public class GUIMetalFormationMachine extends GUIHOEClassicalMachine {

	public GUIMetalFormationMachine(InventoryPlayer playerInv, MachineTileEntity tile){
		this(playerInv,(MetalFormationMachineTileEntity)tile);
	}
	private GUIMetalFormationMachine(InventoryPlayer playerInv, MetalFormationMachineTileEntity tile) {
		super(new MetalFormationMachineContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Metal Formation Machine";
	}	

}
