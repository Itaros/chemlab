package ru.itaros.chemlab.client.ui.common;

import java.util.Iterator;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import buildcraft.core.render.FluidRenderer;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.gui.HOESlotType;
import ru.itaros.hoe.gui.MachineSlot;
import ru.itaros.hoe.gui.ReadonlySlot;
import ru.itaros.hoe.gui.UniversalSlot;
import ru.itaros.hoe.itemhandling.WidgetMixtureIndicator;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;
import ru.itaros.hoe.tiles.ioconfig.IConfigurableIO;
import ru.itaros.hoe.tiles.ioconfig.PortInfo;
import ru.itaros.hoe.utils.RenderingUtils;

public abstract class GUIHOEClassicalMachine extends GuiContainer {

	

	
	//public abstract Class<? extends Container> getContainerType();
	
	public abstract String getMachineUnlocalizedName();
	
	public static final int CAPTIONCOLOR = 4210752;
	protected MachineTileEntity tile;
	protected InventoryPlayer playerInv;
	private ResourceLocation background;
	
	private ResourceLocation additionals;
	
	protected int x;
	protected int y;
	
	
	@Override
	public void initGui() {
		xSize=176+HOEContainer.xOffset*2;
		
		HOEMachineData data = tile.getClientData();
		detectOngoingSync(data);
		
		background = new ResourceLocation("chemlab",getUITexturePath());
		
		additionals = new ResourceLocation("chemlab","textures/gui/additionalui.png");
		
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
	

	private boolean isHOESyncing=false;
	protected boolean isHOESyncing(){
		return isHOESyncing;
	}
	protected void detectOngoingSync(HOEData clientData){
		if(isHOESyncing){
			if(clientData!=null){
				isHOESyncing=false;
				initGui();
				return;
			}
		}
		if(clientData==null){
			isHOESyncing=true;
			return;
		}
	}

	
	
	@Override
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
		HOEMachineData data = tile.getClientData();
		detectOngoingSync(data);
		if(isHOESyncing){
			fontRendererObj.drawString("Machine is syncing with HOE service...", 8, 6, 0xFFFFFF);
			return;
		}
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	}


	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
				HOEMachineData data = tile.getClientData();
				
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.renderEngine.bindTexture(background);
				this.drawTexturedModalRect(x+HOEContainer.xOffset, y, 0, 0, xSize-HOEContainer.xOffset*2, ySize);		
				
				//Additional SlotsUI
				if(this.tile instanceof IConfigurableIO){
					this.mc.renderEngine.bindTexture(additionals);
					this.drawTexturedModalRect(x-(76-34)+3+HOEContainer.xOffset, y+11, 36, 00, (76-34), 66-00);	
					drawAuxSlotsMarkings();
				}
				//Gauges
				
				DrawGauges(data);
				
				fontRendererObj.drawString(this.getMachineUnlocalizedName(), HOEContainer.xOffset+x+8, y+6, CAPTIONCOLOR);//4210752
				
				fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), HOEContainer.xOffset+x+8, y+ySize - 96 + 2 + 2, CAPTIONCOLOR);
				
				
				//Iteminfo
				//fontRendererObj.drawString(tile.getClientData().getInboundAmount(),x+47+2,y+37,0xFFFFFF);
				//fontRendererObj.drawString(tile.getClientData().getOutboundAmount(),x+112+2,y+37,0xFFFFFF);
				
				//ItemStack input = tile.getClientData().getInboundRO();
				//this.TRANSFORMED_drawItemStack(input, x+47+2,y+37, null);
				
				//112,36
				drawHOESlots();
			}

	
	private void drawAuxSlotsMarkings() {
		HOEContainer hc = (HOEContainer)inventorySlots;
		MachineTileEntity mte = hc.getTile();	
		if(mte instanceof IConfigurableIO){
			IConfigurableIO cio = (IConfigurableIO)mte;
			PortInfo[] rpi = cio.getPorts();
			for(Object o:this.inventorySlots.inventorySlots){
				Slot s =(Slot)o;
				if(!(s instanceof MachineSlot) || ((MachineSlot)s).getType()!=HOESlotType.AUX){continue;}
				
				PortInfo crpi = rpi[s.getSlotIndex()-MachineCrafterTileEntity.PORTS_SHIFT];
				if(crpi==null || crpi.isNothing()){
					this.drawTexturedModalRect(x+s.xDisplayPosition-1, y+s.yDisplayPosition-1, 37, 67, 16, 16);		
				}else if(crpi.isItemSocket()){
					this.drawTexturedModalRect(x+s.xDisplayPosition-1, y+s.yDisplayPosition-1, 56, 67, 16, 16);	
				}else if(crpi.isFluidSocket()){
					//this.drawTexturedModalRect(x+s.xDisplayPosition-1, y+s.yDisplayPosition-1, 54, 67, 16, 16);	
					specialOverrideFluids(s,crpi);
				}
			}
		}
	}


	private void specialOverrideFluids(Slot s, PortInfo crpi) {
		FluidStack flstack = (FluidStack)crpi.getStack();
		int angular=0;
		if(flstack!=null){
			//Item underlay
			IIcon fltex = FluidRenderer.getFluidTexture(flstack.getFluid(), false);
			mc.renderEngine.bindTexture(FluidRenderer.getFluidSheet(flstack.getFluid()));
			drawTexturedModelRectFromIcon(s.xDisplayPosition+x, s.yDisplayPosition+y, fltex, 16, 16);		
			angular = (int)(((float)flstack.amount/64000F)*(16F-1F));
		}
		//overlay
		drawTexturedModalRect(s.xDisplayPosition+x-1, s.yDisplayPosition+y-1, 18, -1+(16*angular), 18,18);
	}


	private void drawHOESlots() {
		RenderingUtils.elevateItemRenderingContext();
		
		Iterator<UniversalSlot> i = ((HOEContainer)this.inventorySlots).getHOESlotsIterator();
		while(i.hasNext()){
			UniversalSlot c = i.next();
			c.drawSlot(x,y, this.zLevel,itemRender, fontRendererObj,this);
		}
		
		RenderingUtils.relieveItemRenderingContext();
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
		drawTexturedModalRect(x+159, y+13+inverted, 177, 2+inverted, 9+1, offset);
		
		//MJs
		double mjdiff = mjSmoothed/tile.getMaximumMJ();
		offset = (int) ((66D-6D)*mjdiff);	
		inverted = 66-6-offset;
		drawTexturedModalRect(x+159, y+12+inverted, 177, 67, 9,6);
		
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
	
	//utils
	
	protected void setUnderlyingContext(){
		this.mc.renderEngine.bindTexture(background);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	public void setAdditionalsDrawingMode(){
		this.mc.renderEngine.bindTexture(additionals);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}


	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}


	public MachineTileEntity getTile() {
		return tile;
	}	

	
}