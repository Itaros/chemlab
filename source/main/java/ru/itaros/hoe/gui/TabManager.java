package ru.itaros.hoe.gui;

import java.util.ArrayList;

import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class TabManager {

	private Tab activeTab;
	
	ArrayList<Tab> tabs = new ArrayList<Tab>();
	
	public TabManager(Tab... initialTabs){
		if(initialTabs.length>0){
			initialTabs[0].setActive(true);
			activeTab=initialTabs[0];
		}
		for(Tab t : initialTabs){
			tabs.add(t);
		}
	}
	
	private int taboffset = 20;
	
	public void drawTabs(GuiScreen screen, int x, int y){
		int offset=0;
		for(Tab t : tabs){
			t.draw(screen, x, y+(offset*taboffset));
			offset++;
		}
	}
	
	
	public boolean clickTabs(GuiScreen screen,int mousex, int mousey, int x, int y, int button){
		boolean isTabSwitched=false;
		
		int offset=0;
		
		int ox = mousex-x;
		int oy = mousey-y;
		for(Tab t : tabs){
			if(t.isIn(ox, oy-(offset*taboffset))){
				screen.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
				if(activeTab!=null){activeTab.setActive(false);}
				t.setActive(true);
				activeTab=t;
				isTabSwitched=true;
			}
			offset++;
		}
		return isTabSwitched;
	}
	
	public Tab getActiveTab(){
		return activeTab;
	}
	
	
}
