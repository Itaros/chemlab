package ru.itaros.hoe.utils;

import ru.itaros.hoe.client.textures.TextureAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIUtility {


	
	
	public static void drawFluidGauge(GuiScreen screen, FluidStack fluid, double amount,double max, Rect rect){
		ResourceLocation tex = TextureAccessor.getCommonSheet();
		Minecraft.getMinecraft().renderEngine.bindTexture(tex);
		
		if(fluid==null){return;}
		
		int x = rect.x1;
		int y = rect.y1;
		
		double hdiff = rect.h;
		double offset = hdiff * ((amount)/max);
		double inverted = Math.ceil(rect.h-offset);
		
		IIcon icon = fluid.getFluid().getStillIcon();
		
		//screen.drawTexturedModalRect(x, (int)(y-offset), icon., icon.getMaxU(), 16, (int)offset);
		screen.drawTexturedModelRectFromIcon(x, (int)(y+inverted), icon, 16, (int)offset);
		
		
	}
	
	//@SideOnly(Side.CLIENT)
	public static void bindTexture(ResourceLocation texture){
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
	}
	
	public static boolean isMouseIn(int x1, int y1, int x2, int y2, int mx, int my){
		if(mx>=x1 & mx<=x2){
			if(my>=y1 & my<=y2){
				return true;
			}
		}
		return false;
	}
	
	
}
