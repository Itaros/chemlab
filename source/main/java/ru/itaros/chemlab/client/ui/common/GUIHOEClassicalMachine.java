package ru.itaros.chemlab.client.ui.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import ru.itaros.hoe.vanilla.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import cpw.mods.fml.common.Optional;

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


    /**
     * Draws the screen and all the components in it.
     */
//	@Override
//    public void drawScreen(int par1, int par2, float par3)
//    {
//		super.drawScreen(par1, par2, par3);
//        this.drawDefaultBackground();
//        int k = this.guiLeft;
//        int l = this.guiTop;
//        this.drawGuiContainerBackgroundLayer(par3, par1, par2);
//        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        RenderHelper.disableStandardItemLighting();
//        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
//        drawButtons(par1, par2, par3);
//        //super.drawScreen(par1, par2, par3);
//        RenderHelper.enableGUIStandardItemLighting();
//        GL11.glPushMatrix();
//        GL11.glTranslatef((float)k, (float)l, 0.0F);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//        this.theSlot = null;
//        short short1 = 240;
//        short short2 = 240;
//        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)short1 / 1.0F, (float)short2 / 1.0F);
//        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
//        int k1;
//
//        for (int i1 = 0; i1 < this.inventorySlots.inventorySlots.size(); ++i1)
//        {
//            Slot slot = (Slot)this.inventorySlots.inventorySlots.get(i1);
//            this.local_func_146977_a(slot);
//
//            if (this.isMouseOverSlot(slot, par1, par2) && slot.func_111238_b())
//            {
//                this.theSlot = slot;
//                GL11.glDisable(GL11.GL_LIGHTING);
//                GL11.glDisable(GL11.GL_DEPTH_TEST);
//                int j1 = slot.xDisplayPosition;
//                k1 = slot.yDisplayPosition;
//                GL11.glColorMask(true, true, true, false);
//                this.drawGradientRect(j1, k1, j1 + 16, k1 + 16, -2130706433, -2130706433);
//                GL11.glColorMask(true, true, true, true);
//                GL11.glEnable(GL11.GL_LIGHTING);
//                GL11.glEnable(GL11.GL_DEPTH_TEST);
//            }
//        }
//
//        //Forge: Force lighting to be disabled as there are some issue where lighting would
//        //incorrectly be applied based on items that are in the inventory.
//        GL11.glDisable(GL11.GL_LIGHTING);
//        this.drawGuiContainerForegroundLayer(par1, par2);
//        GL11.glEnable(GL11.GL_LIGHTING);
//        InventoryPlayer inventoryplayer = this.mc.thePlayer.inventory;
//        ItemStack itemstack = this.draggedStack == null ? inventoryplayer.getItemStack() : this.draggedStack;
//
//        if (itemstack != null)
//        {
//            byte b0 = 8;
//            k1 = this.draggedStack == null ? 8 : 16;
//            String s = null;
//
//            if (this.draggedStack != null && this.isRightMouseClick)
//            {
//                itemstack = itemstack.copy();
//                itemstack.stackSize = MathHelper.ceiling_float_int((float)itemstack.stackSize / 2.0F);
//            }
//            else if (this.field_147007_t && this.field_147008_s.size() > 1)
//            {
//                itemstack = itemstack.copy();
//                itemstack.stackSize = this.field_146996_I;
//
//                if (itemstack.stackSize == 0)
//                {
//                    s = "" + EnumChatFormatting.YELLOW + "0";
//                }
//            }
//
//            this.drawItemStack(itemstack, par1 - k - b0, par2 - l - k1, s);
//        }
//
//        if (this.returningStack != null)
//        {
//            float f1 = (float)(Minecraft.getSystemTime() - this.returningStackTime) / 100.0F;
//
//            if (f1 >= 1.0F)
//            {
//                f1 = 1.0F;
//                this.returningStack = null;
//            }
//
//            k1 = this.returningStackDestSlot.xDisplayPosition - this.field_147011_y;
//            int j2 = this.returningStackDestSlot.yDisplayPosition - this.field_147010_z;
//            int l1 = this.field_147011_y + (int)((float)k1 * f1);
//            int i2 = this.field_147010_z + (int)((float)j2 * f1);
//            this.drawItemStack(this.returningStack, l1, i2, (String)null);
//        }
//
//        GL11.glPopMatrix();
//
//        if (inventoryplayer.getItemStack() == null && this.theSlot != null && this.theSlot.getHasStack())
//        {
//            ItemStack itemstack1 = this.theSlot.getStack();
//            this.renderToolTip(itemstack1, par1, par2);
//        }
//
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
//        RenderHelper.enableStandardItemLighting();
//    }	
	
	//@Optional.Method(modid = "NotEnoughItems")
	//@Override
	// protected void drawSlotInventory(Slot slot)
	//   {
	//	 super.drawSlotInventory(slot);
	//	 hackAdditionalStacksizeOverlay(slot);
	//   }
	@Override
	protected void func_146977_a(Slot slot){
		super.func_146977_a(slot);
		if(slot instanceof ReadonlySlot){
			hackAdditionalStacksizeOverlay(slot);
		}
	}
	
	private void hackAdditionalStacksizeOverlay(Slot slot){
		ItemStack stack = slot.getStack();
		
		if(stack==null || stack.stackSize>1){return;}
		
		int x = slot.xDisplayPosition;
		int y = slot.yDisplayPosition;		
		
		FontRenderer par1FontRenderer = fontRendererObj;
		String s1 = String.valueOf(stack.stackSize);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        par1FontRenderer.drawStringWithShadow(s1, x + 19 - 2 - par1FontRenderer.getStringWidth(s1), y + 6 + 3, 16777215);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);		
	}
	
	
}