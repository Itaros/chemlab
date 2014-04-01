package ru.itaros.chemlab.client.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.elements.Tab;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIToolProgrammer extends GuiScreen {

	MachineTileEntity tile;
	public GUIToolProgrammer(MachineTileEntity te){
		tile=te;
	}
	
    protected int xSize = 186;
    protected int ySize = 198;
	
	
	public static final int CAPTIONCOLOR = 4210752;
	
	protected int x;
	protected int y;
	
	ResourceLocation background;
	
	@Override
	public void initGui() {
		background = new ResourceLocation("chemlab","textures/gui/programmerui.png");
		
		int taboffset = -15;
		int tabheight = 19 + 3;
		int i = 1;
		tabs.add(new Tab(0+taboffset,0+(tabheight*i),190,2,17,19));
		
		super.initGui();
	}	
	ArrayList<Tab> tabs = new ArrayList<Tab>();
	
	
	
	
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		
		super.drawScreen(par1, par2, par3);
	}






	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		// TODO Auto-generated method stub
		super.mouseClicked(par1, par2, par3);
	}


	private void drawTabs(int x,int y) {
		for(Tab t:tabs){
			t.draw(this,x,y);
		}
	}






	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(background);
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		
		this.drawTabs(x,y);
		
		fontRendererObj.drawString("Hardware Programmer", x+8, y+6, CAPTIONCOLOR);//4210752
	}
	
}
