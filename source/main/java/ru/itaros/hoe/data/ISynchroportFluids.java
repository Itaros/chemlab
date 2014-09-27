package ru.itaros.hoe.data;

import net.minecraftforge.fluids.FluidStack;

public interface ISynchroportFluids extends ISynchroport{
	public FluidStack tryToPutFluidsIn(FluidStack fluid);
	public FluidStack tryToPutFluidsIn(FluidStack fluid, FluidStack filter);
	public FluidStack tryToGetFluidsOut(FluidStack fluid);
	public FluidStack tryToGetFluidsOut(FluidStack fluid, FluidStack filter);	
}
