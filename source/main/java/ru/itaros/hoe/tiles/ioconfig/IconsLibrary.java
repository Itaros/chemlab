package ru.itaros.hoe.tiles.ioconfig;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class IconsLibrary {

	public IconsLibrary() {	}
	
	public void register(IIconRegister reg, String string1, String string2, String string3,
		String string4, String string5, String string6, String string7){
		inputItem=reg.registerIcon(string1);
		outputItem=reg.registerIcon(string2);
		inputFluid=reg.registerIcon(string3);
		outputFluid=reg.registerIcon(string4);
		inputPower=reg.registerIcon(string5);
		outputPower=reg.registerIcon(string6);
		nothing=reg.registerIcon(string7);		
	}

	protected IIcon inputItem, outputItem, inputFluid, outputFluid, inputPower, outputPower, nothing;

	public IIcon getInputItem() {
		return inputItem;
	}

	public IIcon getOutputItem() {
		return outputItem;
	}

	public IIcon getInputFluid() {
		return inputFluid;
	}

	public IIcon getOutputFluid() {
		return outputFluid;
	}

	public IIcon getInputPower() {
		return inputPower;
	}

	public IIcon getOutputPower() {
		return outputPower;
	}

	public IIcon getNothing() {
		return nothing;
	}
	
}
