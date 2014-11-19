//package ru.itaros.chemlab.client.ui.special;
//
//import net.minecraft.entity.player.InventoryPlayer;
//import net.minecraftforge.common.util.ForgeDirection;
//import net.minecraftforge.fluids.FluidTank;
//import net.minecraftforge.fluids.FluidTankInfo;
//import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
//import ru.itaros.chemlab.hoe.data.HVLCFillerData;
//import ru.itaros.chemlab.loader.HOEFluidLoader;
//import ru.itaros.chemlab.tiles.HVLCFillerTileEntity;
//import ru.itaros.hoe.data.machines.HOEMachineData;
//import ru.itaros.hoe.fluid.HOEFluidDepot;
//import ru.itaros.hoe.fluid.HOEFluidTank;
//import ru.itaros.hoe.tiles.MachineTileEntity;
//import ru.itaros.hoe.utils.Rect;
//import ru.itaros.hoe.utils.UIUtility;
//
//public class GUIHVLCFiller extends GUIHOEClassicalMachine {
//
//	public GUIHVLCFiller(InventoryPlayer playerInv,MachineTileEntity tile){
//		this(playerInv,(HVLCFillerTileEntity)tile);
//	}
//	private GUIHVLCFiller(InventoryPlayer playerInv, HVLCFillerTileEntity tile) {
//		super(new HVLCFillerContainer(playerInv,tile));
//		this.playerInv=playerInv;
//		this.tile=tile;
//		
//		//EYE
//		HVLCFillerTileEntity htile = (HVLCFillerTileEntity)tile;
//		fluid_input_eye=htile.getInputTank().getFluidAmount();
//	}
//	
//	@Override
//	public void initGui() {
//		super.initGui();
//		inputRect = new Rect(133+x,20+y,16,51);
//		tankRect = new Rect(48+x,18+y,16,51);
//	}
//	
//	
//	@Override
//	public String getMachineUnlocalizedName() {
//		return "HVLC Filler";
//	}
//	@Override
//	protected String getUITexturePath() {
//		return "textures/gui/hvlcfillermachine.png";
//	}
//	
//
//	float fluid_input_eye;
//	Rect inputRect,tankRect;
//	
//	@Override
//	protected void DrawGauges(HOEMachineData data, int mx, int my) {
//		//HVLCFillerData fd = (HVLCFillerData)data;
//		HVLCFillerTileEntity htile = (HVLCFillerTileEntity)tile;
//		FluidTank info = htile.getInputTank();
//		fluid_input_eye=(fluid_input_eye+info.getFluidAmount())/2F;
//		UIUtility.drawFluidGauge(this, info.getFluid(), fluid_input_eye, info.getCapacity() , inputRect);
//		
//		HVLCFillerData fd = (HVLCFillerData)data;
//		HOEFluidDepot depot = fd.getFluidDepot();
//		HOEFluidTank tank = depot.getCorrespondingTank(HOEFluidLoader.water_natural);
//		if(tank!=null){
//			UIUtility.drawFluidGauge(this, info.getFluid(), tank.getAmount(), tank.getCapacity() , tankRect);
//		}
//		
//	}
//	
//	
//	
//
//}
