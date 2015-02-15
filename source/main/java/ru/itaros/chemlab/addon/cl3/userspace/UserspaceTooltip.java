package ru.itaros.chemlab.addon.cl3.userspace;

import ru.itaros.hoe.client.LanguageTokenTooltip;
import ru.itaros.hoe.client.StringTooltip;
import ru.itaros.hoe.client.TextTooltip;

public class UserspaceTooltip {

	public enum TooltipType{
		StringTooltip,
		LanguageTokenTooltip
	}
	
	public TooltipType type;
	
	public String data;
	
	private TextTooltip tooltip;
	
	public TextTooltip getTooltip(){
		return tooltip;
	}
	
	public void register(){
		switch(type){
		case StringTooltip:
			tooltip = new StringTooltip(data);
			break;
		case LanguageTokenTooltip:
			tooltip = new LanguageTokenTooltip(data);
			break;
		default:
			throw new IllegalArgumentException("Tooltip type was incorrect");
		}
	}
	
}
