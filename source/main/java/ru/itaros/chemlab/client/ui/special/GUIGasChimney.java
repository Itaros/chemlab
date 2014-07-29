package ru.itaros.chemlab.client.ui.special;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.tiles.GasChimneyTileEntity;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIGasChimney extends GUIHOEClassicalMachine {

	public GUIGasChimney(InventoryPlayer playerInv,MachineTileEntity tile){
		this(playerInv,(GasChimneyTileEntity)tile);
	}
	private GUIGasChimney(InventoryPlayer playerInv, GasChimneyTileEntity tile) {
		super(new GasChimneyContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}
	
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Gas Chimney";
	}
	@Override
	protected String getUITexturePath() {
		return "textures/gui/gaschimneymachine.png";
	}
	

	//float fluid_input_eye;
	//Rect inputRect,tankRect;
	
	@Override
	protected void DrawGauges(HOEMachineData data) {
		DrawProgressbar(data);
	}
	
	
	

}
