package ru.itaros.hoe.client.textures;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

public class ExternalTextureAtlasSprite extends TextureAtlasSprite {

	byte[] inmemCache;
	
	private static Field mipmapLevel;
	
	static{
        try {
            mipmapLevel = TextureMap.class.getDeclaredField("field_147636_j");
        } catch (NoSuchFieldException e) {
            try {
                mipmapLevel = TextureMap.class.getDeclaredField("mipmapLevels");
            } catch (NoSuchFieldException f) {
                throw new RuntimeException(f);
            }
        }
        mipmapLevel.setAccessible(true);
	}
	
	public ExternalTextureAtlasSprite(String iconName, byte[] blob) {
		super(iconName);
		inmemCache=blob;
	}

	@Override
	public boolean hasCustomLoader(IResourceManager manager,
			ResourceLocation location) {
		return true;
	}

	@Override
	public boolean load(IResourceManager manager, ResourceLocation location) {
		
		try{
			int mipmapLevels = mipmapLevel.getInt(Minecraft.getMinecraft().getTextureMapBlocks());
		
			BufferedImage[] bfis=new BufferedImage[1 + mipmapLevels];
			ByteArrayInputStream bais = new ByteArrayInputStream(inmemCache);
			bfis[0] = ImageIO.read(bais);
			//bais.close();
			
			super.loadSprite(bfis, null, false);
			bais.close();
			
		}catch(Exception e){
			throw new RuntimeException("Failed to load "+this.toString(),e);
		}
		
		return false;
	}

	
	
	
}
