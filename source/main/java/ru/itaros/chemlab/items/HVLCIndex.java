package ru.itaros.chemlab.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.hoe.fluid.HOEFluid;

public class HVLCIndex {

	private HOEFluid fluid;
	
	private Fluid pair;
	private FluidStack pairCached;
	
	public HVLCIndex(HOEFluid fluid){
		this.fluid=fluid;
		
		pair = fluid.getForgeFluid();
		pairCached = new FluidStack(pair,1000);
		
		this.setUnlocalizedName("item.hvlc."+fluid.getCommonName());	
		this.setTextureName("chemlab:hvlc-"+fluid.getCommonName());
	}
	
	private String unlocalized;
	private String texname;
	
	private IIcon icon;
	
	protected void setUnlocalizedName(String s){
		unlocalized=s;
	}
	protected void setTextureName(String s){
		texname=s;
	}
	public void registerIcons(IIconRegister register) {
		icon=register.registerIcon(texname);
	}
	public IIcon getIcon() {
		return icon;
	}
	public HOEFluid getFluid() {
		return fluid;
	}
	public String getUnlocalizedName() {
		return unlocalized;
	}
	
	
	public FluidStack getCachedForgeFluidStack() {
		return pairCached;
	}
	
	
}
