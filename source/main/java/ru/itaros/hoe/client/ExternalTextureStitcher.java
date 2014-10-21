package ru.itaros.hoe.client;

import java.util.ArrayList;

import ru.itaros.hoe.client.textures.ExternalTextureAtlasSprite;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;


public class ExternalTextureStitcher {
	
	ArrayList<ExternalTextureAtlasSprite> blocks = new ArrayList<ExternalTextureAtlasSprite>();
	
	public ExternalTextureStitcher(){
		
	}
	
	private boolean wasStitched=false;
	
	public void registerBlockSprite(ExternalTextureAtlasSprite sprite){
		if(wasStitched){throw new IllegalStateException("Already stitched!");}
		
		blocks.add(sprite);
		
	}
	
	@SubscribeEvent
	public void registerTextures(TextureStitchEvent.Pre event) {
		wasStitched=true;
		for(ExternalTextureAtlasSprite etas : blocks){
			boolean isAdded = event.map.setTextureEntry(etas.getIconName(), etas);
			//event.map.
			System.out.println("ETAS Registered: "+etas.getIconName());
		}
	}

	
}
