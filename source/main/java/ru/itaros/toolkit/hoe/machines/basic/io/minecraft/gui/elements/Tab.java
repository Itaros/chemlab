package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.elements;

import net.minecraft.client.gui.GuiScreen;

public class Tab {

	private int x,y;
	
	private int back_u,back_v,back_usize,back_vsize;
	
	private int pict_u,pict_v,pict_usize,pict_vsize;
	private boolean hasPictogram=false;
	
	public Tab(int x, int y, int back_u, int back_v, int back_usize, int back_vsize){
		this.x=x;
		this.y=y;
		this.back_u=back_u;
		this.back_v=back_v;
		this.back_usize=back_usize;
		this.back_vsize=back_vsize;
	}
	
	public void draw(GuiScreen screen, int xo, int yo){
		screen.drawTexturedModalRect(xo+x, yo+y, back_u, back_v, back_usize, back_vsize);
	}
	
	
	
	
}
