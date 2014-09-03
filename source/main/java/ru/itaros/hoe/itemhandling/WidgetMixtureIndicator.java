package ru.itaros.hoe.itemhandling;

import java.util.Iterator;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import ru.itaros.chemlab.client.ui.common.GUIHOEClassicalMachine;
import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.hoe.data.ArcFurnaceControllerData;
import ru.itaros.hoe.client.IUIWidget;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WidgetMixtureIndicator implements IUIWidget{
	
	private static final float WINDOW_HEIGHT = 33F;
	GUIHOEClassicalMachine uisource;
	
	public WidgetMixtureIndicator(GUIHOEClassicalMachine uisource){
		this.uisource=uisource;
	}
	
	public void draw(FontRenderer font){
		ArcFurnaceControllerData data = (ArcFurnaceControllerData) uisource.getTile().getClientData();
		
		MixtureStack stack = data.getMixtureVat();
		
		
		
		//Evaluating max volume
		float maxv=0;
		Iterator<IUniversalStack> sti = stack.getViewIterator();
		while(sti.hasNext()){
			IUniversalStack i = sti.next();		
			if(maxv<i.getVolume()){maxv=i.getVolume();}
		}
		//Drawing bars
		sti = stack.getViewIterator();
		int step=0;
		while(sti.hasNext()){
			IUniversalStack i = sti.next();
			float p = i.getVolume()/maxv;
		
			int ix=uisource.getX()+9+(step*3)+HOEContainer.xOffset;
			int iy=(int) (uisource.getY()+38+WINDOW_HEIGHT);
			
			Gui.drawRect(ix, iy-(int)(WINDOW_HEIGHT*p), ix+2, iy, 0xFF0000FF);
			//host.
			
			step++;
		}
		
		uisource.drawString(font, "U:"+step, uisource.getX(), uisource.getY(), 0xFF0000);
		uisource.drawString(font, "V:"+stack.getTotalVolume(), uisource.getX(), uisource.getY()+16, 0xFF0000);
		
	}
	
}
