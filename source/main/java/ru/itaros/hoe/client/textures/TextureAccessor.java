package ru.itaros.hoe.client.textures;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TextureAccessor {

	/*
	 * Used to access fluid and blocks textures
	 */
	public static ResourceLocation getCommonSheet(){
		return TextureMap.locationBlocksTexture;
	}
	
	public static IIcon getFluidTexture(FluidStack fluid){
		return getFluidTexture(fluid.getFluid());
	}
	
	public static IIcon getFluidTexture(Fluid fluid){
		
		IIcon candidate = fluid.getStillIcon();
		if(candidate==null){
			candidate = fluid.getFlowingIcon();
		}
		
		return candidate;
		
	}
	
}
