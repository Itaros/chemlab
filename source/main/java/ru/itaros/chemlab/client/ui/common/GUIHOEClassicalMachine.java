package ru.itaros.chemlab.client.ui.common;

import org.lwjgl.opengl.GL11;

import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class GUIHOEClassicalMachine extends GuiContainer {

	//public abstract Class<? extends Container> getContainerType();
	
	public abstract String getMachineUnlocalizedName();
	
	public static final int CAPTIONCOLOR = 4210752;
	protected MachineTileEntity tile;
	protected InventoryPlayer playerInv;
	private ResourceLocation background;
	protected int x;

	@Override
	public void initGui() {
		background = new ResourceLocation("chemlab","textures/gui/generichoemachine.png");
		
		
		super.initGui();
	}

	protected int y;

	public GUIHOEClassicalMachine(Container par1Container) {
		super(par1Container);
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
				x = (width - xSize) / 2;
				y = (height - ySize) / 2;
				this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);		
				
				DrawPowerGauge(data);
				
				DrawProgressbar(data);
				
				fontRendererObj.drawString(this.getMachineUnlocalizedName(), x+8, y+6, CAPTIONCOLOR);//4210752
				
				fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), x+8, y+ySize - 96 + 2, CAPTIONCOLOR);
				
				
				//Iteminfo
				fontRendererObj.drawString(tile.getClientData().getInboundAmount(),x+47+2,y+37,0xFFFFFF);
				fontRendererObj.drawString(tile.getClientData().getOutboundAmount(),x+112+2,y+37,0xFFFFFF);
				//112,36
				
			}

	
	
	
	private void DrawProgressbar(HOEMachineData data) {
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
		int power = data.getPower();
		int powerMax = data.getPowerMax();	
		power=powerMax/2;//TODO: DEBUG. There is no power yet
		
		double diff = (double)power/(double)powerMax;
		int offset = (int) (66D*diff);
		int inverted = 66-offset;
		this.drawTexturedModalRect(x+159, y+13+inverted, 177, 2+inverted, 9+1, offset);
		
		
		//fontRendererObj.drawString(power+"/"+powerMax, x+8, y+6+30, CAPTIONCOLOR);
		
	}

}