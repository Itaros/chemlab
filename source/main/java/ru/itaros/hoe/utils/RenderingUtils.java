package ru.itaros.hoe.utils;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

public class RenderingUtils {
	
	public static void elevateItemRenderingContext(){
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F / 1.0F, 240F / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glColorMask(true, true, true, true);
		GL11.glPushMatrix();
	}
	public static void relieveItemRenderingContext(){
		RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_LIGHTING);
	}
	
	
	//Mojangcode
    public static void drawItemStack(ItemStack stack, int x, int y, String stackSizeOverride, float zlevel, RenderItem itemRender, FontRenderer fontRendererObj)
    {
    	if(stack!=null){
    		stackSizeOverride=String.valueOf(stack.stackSize);
    	}
    	
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        zlevel = 100.0F;
        itemRender.zLevel = 100.0F;
        FontRenderer font = null;
        if (stack != null) font = stack.getItem().getFontRenderer(stack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), stack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, Minecraft.getMinecraft().getTextureManager(), stack, x, y - 0, stackSizeOverride);//0 or 8
        zlevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }
}
