package ru.itaros.chemlab.client.ui;

import java.util.ArrayList;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.common.registry.LanguageRegistry;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.network.packets.SetHOEMachineRecipePacket;
import ru.itaros.hoe.gui.Tab;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.recipes.Recipe;
import ru.itaros.hoe.recipes.RecipesCollection;
import ru.itaros.hoe.tiles.ISecured;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.utils.RenderingUtils;

public class GUIToolProgrammer extends GuiScreen {

	MachineCrafterTileEntity tile;
	public GUIToolProgrammer(MachineCrafterTileEntity te){
		tile=te;
	}
	
    protected int xSize = 187;
    protected int ySize = 198;
	
	
	public static final int CAPTIONCOLOR = 4210752;
	
	protected int x;
	protected int y;
	
	ResourceLocation background;
	ResourceLocation overlay;
	
	@Override
	public void initGui() {
		
		currentPage=0;
		
		background = new ResourceLocation("chemlab","textures/gui/programmerui.png");
		
		overlay = new ResourceLocation("chemlab","textures/gui/programmerui_overlay.png");
		
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
		
		if(activeTab==recipetab){
			this.drawPageSelector();
			
			this.drawRecipes(0,0,0);
			
		}
		
		if(activeTab==infotab){
			this.drawMachineInfo();
		}
		
		
		
		super.drawScreen(par1, par2, par3);
	}

	
	private void drawPageSelector(){
		//46
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(overlay);
		
		this.drawTexturedModalRect(x+8, y+153, 0, 46, 171, 66-45);
		
		fontRendererObj.drawString(currentPage+"/"+totalPages, x+136+2, y+153+5+2, 0x00FF00);
		//136/153
		
	}
	
	private void interactPageSelector(int x2, int y2){	
		
		if(x2>x+169 && x2<x+178 && y2>y+153 && y2<y+173){
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			currentPage++;
			if(currentPage>totalPages){currentPage=totalPages;}
			
		}
		
		if(x2>x+158 && x2<x+167 && y2>y+153 && y2<y+173){
			this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
			
			currentPage--;
			if(currentPage<0){currentPage=0;}
			
		}		
		
	}

	
	private void drawMachineInfo(){
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);		
		
		int xi = 8;
		int yi = 18;
		
		int ystep = 10;
		
		int i = 0;
		
		HOEMachineIO mio = tile.getSuperIO();
		if(mio instanceof HOEMachineCrafterIO){
			HOEMachineCrafterIO mcrafter = (HOEMachineCrafterIO)mio;
			
			String namePrefix = LanguageRegistry.instance().getStringLocalization("ui.prefix.name");
			String ownerPrefix = LanguageRegistry.instance().getStringLocalization("ui.prefix.owner");
			String sep = LanguageRegistry.instance().getStringLocalization("ui.separator");
			
			String powerPrefix = LanguageRegistry.instance().getStringLocalization("ui.prefix.power");
			String powerPrefix_max = LanguageRegistry.instance().getStringLocalization("ui.prefix.power.max");
			String powerPrefix_cur = LanguageRegistry.instance().getStringLocalization("ui.prefix.power.cur");
			String powerPostfix = LanguageRegistry.instance().getStringLocalization("ui.postfix");
			
			
			fontRendererObj.drawString(namePrefix+"UNDEFINED", xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);		
			i++;
			ISecured secte = (ISecured) tile;
			fontRendererObj.drawString(ownerPrefix+secte.getSecurity().getOwnerName(), xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);		
			i++;
			fontRendererObj.drawString(sep, xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);		
			i++;
			fontRendererObj.drawString(powerPrefix, xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);
			i++;
			fontRendererObj.drawString("-"+powerPrefix_cur+tile.getClientData().getPower()+powerPostfix, xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);
			i++;			
			fontRendererObj.drawString("-"+powerPrefix_max+tile.getClientData().getPowerMax()+powerPostfix, xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);
			i++;
			
			
			
			
			
		}
	}
	
	
	private int totalPages = 0;
	private int currentPage = 0;
	private void drawRecipes(int operation, int x2, int y2) {
		//opertation:
		//0 = DRAW
		//1 = CLICKSIGN
		
		HOEMachineIO mio = tile.getSuperIO();
		if(mio instanceof HOEMachineCrafterIO){
			HOEMachineCrafterIO mcrafter = (HOEMachineCrafterIO)mio;
			RecipesCollection repcol = mcrafter.getRecipesCollection();
			//TODO: If there is no col - show "NO RECIPES AVAILABLE. THIS IS A BUG!"
			
			int recipesAmount = repcol.getRecipesAmount();
			totalPages = recipesAmount/3;//3 is amount per page
			if(recipesAmount % 3 == 0){
				totalPages--;
			}
			
			int rangeStart = currentPage * 3;
			int rangeEnd = rangeStart + 3;
			if(rangeEnd>recipesAmount){rangeEnd=recipesAmount;};
			
			int osx=171;
			int osy=44;
			
			int xi = 8;
			int yi = 18;
			int ystep = 45;
			
			//Drawing
			int i=-1;
			for(int xp = rangeStart; xp < rangeEnd; xp++){
				i++;
				if(operation == 0){
					GL11.glDisable(GL11.GL_LIGHTING);
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					this.mc.renderEngine.bindTexture(overlay);
					this.drawTexturedModalRect(xi+x, yi+y+(ystep*i), 0, 0, osx, osy);
					GL11.glEnable(GL11.GL_LIGHTING);
					
					Recipe r = repcol.getRecipes()[xp];
					
					 GL11.glEnable(GL12.GL_RESCALE_NORMAL);
					//What the hell is that?
			        short short1 = 240;
			        short short2 = 240;
			        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);				
					
			        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					
					RenderHelper.enableGUIStandardItemLighting();
					
					GL11.glColorMask(true, true, true, true);
	
					GL11.glPushMatrix();
			       // int k = this.guiLeft;
			       // int l = this.guiTop;
			       // GL11.glTranslatef((float)k, (float)l, 0.0F);
					
					//TODO: This shit should be all precached
					//Drawing incomings
					int inc_offset=-1;
					for(IUniversalStack stack_inc : r.getIncomingStricttypes()){
						inc_offset++;
						if(stack_inc instanceof UniversalItemStack){
							RenderingUtils.drawItemStack((ItemStack) stack_inc.getProxy(), x+xi+60-10-(inc_offset*(16+2)), y+yi+18+(ystep*i), null, this.zLevel, this.itemRender, this.fontRendererObj);
						}
					}
					//Drawing outcomings
					int out_offset=-1;
					for(IUniversalStack stack_out : r.getOutcomingStricttypes()){
						out_offset++;
						if(stack_out instanceof UniversalItemStack){
							RenderingUtils.drawItemStack((ItemStack) stack_out.getProxy(), x+xi+97+10+(out_offset*(16+2)), y+yi+18+(ystep*i), null, this.zLevel, this.itemRender, this.fontRendererObj);
						}
					}	
					
					RenderHelper.enableStandardItemLighting();
					
					GL11.glPopMatrix();
					
					RenderHelper.disableStandardItemLighting();
					GL11.glDisable(GL11.GL_LIGHTING);
					
					fontRendererObj.drawString(r.getLocalizedName(), xi+x+2, yi+y+(ystep*i)+1, 0x00FF00);
					
				}else if(operation==1){
					int ox = x2-x;
					int oy = y2-y;
					
					int initx=xi;
					int inity=yi+(ystep*i);
					
					
					if(ox>initx && ox<initx+osx){
						if(oy>inity && oy<inity+osy){
							//Clicked
							Recipe r = repcol.getRecipes()[xp];
							//this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
							
							if(tile.trySetRecipe(r)){
								this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
							}else{
								this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("chemlab:ui.click"), 1.0F));
							}
							//Clicking is always done on a client, you know xD
							ChemLab.getInstance().SendPacketAsClientToServer(new SetHOEMachineRecipePacket(tile,r));
						}
					}
					
				}
			}
		//60/18
		}
		
	}

	
	
	
	@Override
	public void onGuiClosed() {
		this.mc.getSoundHandler().stopSounds();
	}


	@Override
	public boolean doesGuiPauseGame() {
		return false;
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
		if(activeTab==recipetab){
			drawRecipes(1,x2,y2);
			interactPageSelector(x2,y2);
		}
		
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
