package ru.itaros.hoe.toolkit.ui;

import net.minecraft.client.gui.GuiScreen;

public class Tab {

	private boolean isActive=false;
	public final boolean isActive() {
		return isActive;
	}

	public final void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	private int x,y;
	
	private int back_u,back_v,back_usize,back_vsize;
	
	private int pict_u,pict_v,pict_usize,pict_vsize;
	private boolean hasPictogram=false;
	
	public Tab(int x, int y, int back_u, int back_v, int back_usize, int back_vsize, int pictu, int pictv){
		this.x=x;
		this.y=y;
		this.back_u=back_u;
		this.back_v=back_v;
		this.back_usize=back_usize;
		this.back_vsize=back_vsize;
		
		pict_u=pictu;
		pict_v=pictv;
		
		pict_usize=13;
		pict_vsize=13;
	}
	
	public void draw(GuiScreen screen, int xo, int yo){
		if(isActive){
			screen.drawTexturedModalRect(xo+x, yo+y, back_u, back_v, back_usize, back_vsize);
		}else{
			screen.drawTexturedModalRect(xo+x, yo+y, back_u, back_v+22, back_usize, back_vsize);
		}
		screen.drawTexturedModalRect(xo+x+3, yo+y+3, pict_u, pict_v, pict_usize, pict_vsize);
	}

	public boolean isIn(int x2, int y2) {
		if(x2>x && x2<(x)+back_usize){
			if(y2>y && y2<(y)+back_vsize){
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
