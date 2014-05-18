package ru.itaros.chemlab.client.ui.common;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public abstract class GUIHOEClassicalMachine extends GuiContainer {

	

	
	//public abstract Class<? extends Container> getContainerType();
	
	public abstract String getMachineUnlocalizedName();
	
	public static final int CAPTIONCOLOR = 4210752;
	protected MachineTileEntity tile;
	protected InventoryPlayer playerInv;
	private ResourceLocation background;
	protected int x;
	protected int y;
	@Override
	public void initGui() {
		
		
		background = new ResourceLocation("chemlab",getUITexturePath());
		
		super.initGui();
		
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;		
		
	}

	
	protected String getUITexturePath(){
		return "textures/gui/generichoemachine.png";
	}
	


	

	public GUIHOEClassicalMachine(Container par1Container) {
		super(par1Container);

		resetEyeCandy();
	}
	


	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}	
	




	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
				HOEMachineData data = tile.getClientData();
				if(data==null){
					fontRendererObj.drawString("Machine is syncing with HOE service...", 8, 6, 0xFFFFFF);
					return;
				}
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.renderEngine.bindTexture(background);
				this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);		
				
				DrawGauges(data);
				
				fontRendererObj.drawString(this.getMachineUnlocalizedName(), x+8, y+6, CAPTIONCOLOR);//4210752
				
				fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), x+8, y+ySize - 96 + 2, CAPTIONCOLOR);
				
				
				//Iteminfo
				//fontRendererObj.drawString(tile.getClientData().getInboundAmount(),x+47+2,y+37,0xFFFFFF);
				//fontRendererObj.drawString(tile.getClientData().getOutboundAmount(),x+112+2,y+37,0xFFFFFF);
				
				//ItemStack input = tile.getClientData().getInboundRO();
				//this.TRANSFORMED_drawItemStack(input, x+47+2,y+37, null);
				
				//112,36
				
			}

	
	protected void DrawGauges(HOEMachineData data){
		DrawPowerGauge(data);
		DrawProgressbar(data);		
	}
	
	
	
	protected void DrawProgressbar(HOEMachineData data) {
		float currentT = data.getTicks();
		float maxT = data.ticksRequared;
		int percent = (int) ((currentT/maxT)*100);
		DrawProgressbarRaw(66, 37, 187, 2, percent);
	}

	protected void DrawProgressbarRaw(int xl, int yl, int u, int v, int percents){
		//this.mc.renderEngine.bindTexture(gui_progressbar);
		
		int prg_width=230-(187-2);
		int prg_height=17-2;
		
		this.drawTexturedModalRect(x+xl, y+yl, u, v, (int) (((double)percents/100D)*(double)prg_width), prg_height);
	}
		
	

	private void DrawPowerGauge(HOEMachineData data) {
		
		new_time=System.nanoTime();
		tcom+=new_time-last_time;
		
		//TIME
		
		//HOE POWER
		double power = data.getPower();
		//timers
		//->
		if(energySmoothed<0D){energySmoothed=power;}
		if(mjSmoothed<0D){mjSmoothed=tile.getCurrentMJ();}
		if(tcom>TIMEOFFSET){
			tcom-=TIMEOFFSET;
			energySmoothed=(energySmoothed+power)/2D;
			mjSmoothed=(mjSmoothed+tile.getCurrentMJ())/2D;
		}
		//<-
		
		double powerMax = data.getPowerMax();	
		
		double diff = energySmoothed/(double)powerMax;
		int offset = (int) (66D*diff);
		int inverted = 66-offset;
		this.drawTexturedModalRect(x+159, y+13+inverted, 177, 2+inverted, 9+1, offset);
		
		//MJs
		double mjdiff = mjSmoothed/tile.getMaximumMJ();
		offset = (int) ((66D-6D)*mjdiff);	
		inverted = 66-6-offset;
		this.drawTexturedModalRect(x+159, y+12+inverted, 177, 67, 9,6);
		
		//fontRendererObj.drawString(tile.getCurrentMJ()+"/"+tile.getMaximumMJ(), x+8, y+6+30, CAPTIONCOLOR);
		
	}
	private static final int TIMEOFFSET=(int) (1D/20D*1000D);
	private long last_time;
	private long new_time;
	private int tcom;
	private double energySmoothed;
	private double mjSmoothed;
	private void resetEyeCandy() {
		energySmoothed=-1;
		mjSmoothed=-1;
		
		tcom=0;
		last_time=System.nanoTime();
	}
	
}