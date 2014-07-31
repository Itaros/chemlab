package ru.itaros.hoe.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;

import org.lwjgl.opengl.GL11;

import buildcraft.core.render.FluidRenderer;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackUtils;
import ru.itaros.hoe.tiles.IUniversalInventory;
import ru.itaros.hoe.utils.RenderingUtils;


public class UniversalSlot {

	private final IUniversalInventory inv;
	
	private final int slotid;
	
	private final int xDisplayPosition, yDisplayPosition;
	
	public UniversalSlot(IUniversalInventory inv,int slotid, int xDisplayPosition, int yDisplayPosition){
		this.slotid = slotid;
		this.inv=inv;
		
		this.xDisplayPosition=xDisplayPosition;
		this.yDisplayPosition=yDisplayPosition;
	}
	
	
	public void drawSlot(int x, int y, float zlevel, RenderItem itemRender, FontRenderer fontRendererObj, GUIHOEClassicalMachine screen){
		
		 
		 IUniversalStack ius = inv.getStackInHOERemoteSlot(slotid);
		 if(ius!=null){
			 Object proxy = UniversalStackUtils.getSafeProxy(ius);
			 if(proxy!=null){
				 if(proxy instanceof ItemStack){
					 ItemStack o = (ItemStack)proxy;
					 RenderingUtils.drawItemStack(o, xDisplayPosition+x, yDisplayPosition+y, null, 0, itemRender, fontRendererObj);
				 }else if(proxy instanceof HOEFluidStack){
					//drawGradientRect(xDisplayPosition+x, yDisplayPosition+y, xDisplayPosition + 16 + x, yDisplayPosition + 16+y, -2130706433, -2130706433, 0D);
					 HOEFluidStack o = (HOEFluidStack)proxy;
					 
					IIcon fltex = FluidRenderer.getFluidTexture(o.getFluid().getForgeFluid(), false);
					screen.mc.renderEngine.bindTexture(FluidRenderer.getFluidSheet(o.getFluid().getForgeFluid()));
					screen.drawTexturedModelRectFromIcon(xDisplayPosition+x, yDisplayPosition+y, fltex, 16, 16);
					
					// fontRendererObj.drawStringWithShadow(String.valueOf(o.stackSize), xDisplayPosition+x + 19 - 2 - fontRendererObj.getStringWidth(String.valueOf(o.stackSize)), yDisplayPosition+y + 6 + 3, 16777215);
					 int angular = (int)(((float)o.stackSize/64000F)*(16F-1F));
					 screen.setAdditionalsDrawingMode();
					 screen.drawTexturedModalRect(xDisplayPosition+x, yDisplayPosition+y, 0, 0+(16*angular), 16,16);
				 }
			 }
		 }
	}

	
    protected void drawGradientRect(int x1, int y1, int x2, int y2, int color1, int color2, double zlevel)
    {
        float f = (float)(color1 >> 24 & 255) / 255.0F;
        float f1 = (float)(color1 >> 16 & 255) / 255.0F;
        float f2 = (float)(color1 >> 8 & 255) / 255.0F;
        float f3 = (float)(color1 & 255) / 255.0F;
        float f4 = (float)(color2 >> 24 & 255) / 255.0F;
        float f5 = (float)(color2 >> 16 & 255) / 255.0F;
        float f6 = (float)(color2 >> 8 & 255) / 255.0F;
        float f7 = (float)(color2 & 255) / 255.0F;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(f1, f2, f3, f);
        tessellator.addVertex((double)x2, (double)y1, zlevel);
        tessellator.addVertex((double)x1, (double)y1, zlevel);
        tessellator.setColorRGBA_F(f5, f6, f7, f4);
        tessellator.addVertex((double)x1, (double)y2, zlevel);
        tessellator.addVertex((double)x2, (double)y2, zlevel);
        tessellator.draw();
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }


    private HOESlotType type;
    
	public UniversalSlot setType(HOESlotType output) {
		this.type=output;
		return this;
	}	
}