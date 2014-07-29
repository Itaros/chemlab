package ru.itaros.hoe.utils;

import buildcraft.core.render.FluidRenderer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

@SideOnly(Side.CLIENT)
public class UIUtility {


	
	
	public static void drawFluidGauge(GuiScreen screen, FluidStack fluid, double amount,double max, Rect rect){
		ResourceLocation tex = FluidRenderer.getFluidSheet(fluid);
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
	
	
}
