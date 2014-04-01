package ru.itaros.chemlab.client.ui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIToolProgrammer extends GuiScreen {

	
    protected int xSize = 186;
    protected int ySize = 198;
	
	
	public static final int CAPTIONCOLOR = 4210752;
	
	protected int x;
	protected int y;
	
	ResourceLocation background;
	
	@Override
	public void initGui() {
		background = new ResourceLocation("chemlab","textures/gui/programmerui.png");
		
		
		super.initGui();
	}	
	
	
	
	
	
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		super.drawScreen(par1, par2, par3);
	}






	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(background);
		x = (width - xSize) / 2;
		y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);	
		fontRendererObj.drawString("Hardware Programmer", x+8, y+6, CAPTIONCOLOR);//4210752
	}
	
}
