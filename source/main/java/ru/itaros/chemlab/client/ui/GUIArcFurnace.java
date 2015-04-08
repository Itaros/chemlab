package ru.itaros.chemlab.client.ui;

import net.minecraft.entity.player.InventoryPlayer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.hoe.data.ArcFurnaceControllerData;
import ru.itaros.chemlab.tiles.ArcFurnaceControllerTileEntity;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.itemhandling.WidgetMixtureIndicator;
import ru.itaros.hoe.tiles.MachineTileEntity;

public class GUIArcFurnace extends GUIHOEClassicalMachine {
	public GUIArcFurnace(InventoryPlayer playerInv,MachineTileEntity tile){
		this(playerInv,(ArcFurnaceControllerTileEntity)tile);
	}
	private GUIArcFurnace(InventoryPlayer playerInv, ArcFurnaceControllerTileEntity tile) {
		super(new ArcFurnaceContainer(playerInv,tile));
		this.playerInv=playerInv;
		this.tile=tile;
		
		//EYE
		//ArcFurnaceControllerTileEntity htile = (ArcFurnaceControllerTileEntity)tile;
		//fluid_input_eye=htile.getInputTank().getFluidAmount();
	}
	
	@Override
	public void initGui() {
		super.initGui();
		//inputRect = new Rect(133+x,20+y,16,51);
		//tankRect = new Rect(48+x,18+y,16,51);
		
		mixtureIndicator = new WidgetMixtureIndicator(this);
	}
	private WidgetMixtureIndicator mixtureIndicator;
	
	@Override
	public String getMachineUnlocalizedName() {
		return "Arc Furnace Assembly";
	}
	@Override
	protected String getUITexturePath() {
		return "textures/gui/arcfurnaceui.png";
	}
	
	
	@Override
	protected void DrawGauges(HOEMachineData data, int mx, int my) {
		ArcFurnaceControllerData fc = (ArcFurnaceControllerData)data;
		this.fontRendererObj.drawString("Mt: "+fc.getHeatResistance(), x+44+60, y+14, 4210752,false);
		this.fontRendererObj.drawString("Mf: "+((int)Math.floor(fc.getVoluResistance()*100F))+"%", x+44+60, y+14+9, 4210752,false);		
		this.fontRendererObj.drawString("T: "+((int)Math.floor(fc.getMixtureVat().getCurrentTemperature())), x+44+60, y+14+9*2, 4210752,false);		
		
		mixtureIndicator.draw(fontRendererObj,mx,my,0);//Graph
		mixtureIndicator.draw(fontRendererObj,mx,my,1);//Tooltips
		
		
	}
}
