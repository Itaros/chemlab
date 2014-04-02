package ru.itaros.chemlab.client.ui;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.elements.Tab;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GUIToolProgrammer extends GuiScreen {

	MachineTileEntity tile;
	public GUIToolProgrammer(MachineTileEntity te){
		tile=te;
	}
	
    protected int xSize = 187;
    protected int ySize = 198;
	
	
	public static final int CAPTIONCOLOR = 4210752;
	
	protected int x;
	protected int y;
	
	ResourceLocation background;
	
	@Override
	public void initGui() {
		background = new ResourceLocation("chemlab","textures/gui/programmerui.png");
		
		int taboffset = -17;
		int tabheight = 19 + 3;
		int i = 1;
		Tab temp;
		temp = new Tab(0+taboffset,0+(tabheight*i),190,2,19,20,193,53);
		infotab=temp;
		temp.setActive(true);
		activeTab=temp;
		tabs.add(temp);//Info
		i++;
		temp = new Tab(0+taboffset,0+(tabheight*i),190,2,19,20,208,53);
		recipetab=temp;
		tabs.add(temp);//Recipe
		i++;		
		temp = new Tab(0+taboffset,0+(tabheight*i),190,2,19,20,208,68);
		statstab=temp;
		tabs.add(temp);//Stats
		i++;	
		temp = new Tab(0+taboffset,0+(tabheight*i),190,2,19,20,193,68);
		securitytab=temp;
		tabs.add(temp);//Security
		i++;			
		
		super.initGui();
	}	
	ArrayList<Tab> tabs = new ArrayList<Tab>();
	
	
	private Tab infotab,recipetab,statstab,securitytab;
	
	
	
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		this.drawGuiContainerBackgroundLayer(par3, par1, par2);
		
		super.drawScreen(par1, par2, par3);
	}


	@Override
	protected void mouseMovedOrUp(int x2, int y2,
			int p_146286_3_) {
		
		//clickTabs(x2,y2,p_146286_3_,x,y);
		
		super.mouseMovedOrUp(x2, y2, p_146286_3_);
	}
	@Override
	protected void mouseClicked(int x2, int y2, int button) {

		clickTabs(x2,y2,button,x,y);
		
		super.mouseClicked(x2, y2, button);
	}


	private Tab activeTab=null;
	
	private void clickTabs(int x2, int y2, int button,int x, int y) {
		for(Tab t:tabs){
			if(t.isIn(x2-x,y2-y)){
				this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				if(activeTab!=null){activeTab.setActive(false);}
				t.setActive(true);
				activeTab=t;
			}
		}
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
