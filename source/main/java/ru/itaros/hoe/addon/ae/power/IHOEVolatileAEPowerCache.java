package ru.itaros.hoe.addon.ae.power;

import appeng.api.config.Actionable;

public interface IHOEVolatileAEPowerCache {

	public double extractAEPower(double amt, Actionable mode);
	
	public double injectAEPower(double amt, Actionable mode);
	
	public double getAECurrentPower();
	
	public double getAEMaxPower();
	
}
