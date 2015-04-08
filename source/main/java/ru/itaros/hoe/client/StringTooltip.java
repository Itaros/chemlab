package ru.itaros.hoe.client;


public class StringTooltip extends TextTooltip {
	
	public StringTooltip(String text){
		this.text = text;
	}
	
	private String text;
	
	@Override
	public String getText() {
		return text;
	}

}
