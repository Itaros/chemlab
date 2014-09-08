package ru.itaros.hoe.client;

import net.minecraft.client.gui.FontRenderer;

public interface IUIWidget {

	public void draw(FontRenderer font, int mx, int my, int pass);
	
}
