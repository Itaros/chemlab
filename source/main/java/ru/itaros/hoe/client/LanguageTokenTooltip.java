package ru.itaros.hoe.client;

import net.minecraft.util.StatCollector;


public class LanguageTokenTooltip extends TextTooltip {

	public LanguageTokenTooltip(String token){
		this.token = token;
	}
	
	private String token;
	
	private boolean isCached=false;
	
	private String cached;
	
	@Override
	public String getText() {
		if(isCached){
			return cached;
		}else{
			cached = StatCollector.translateToLocal(token);
			isCached = true;
			return cached;
		}
	}

}
