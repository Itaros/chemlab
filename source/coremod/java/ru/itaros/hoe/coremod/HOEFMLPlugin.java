package ru.itaros.hoe.coremod;

import java.util.Map;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@TransformerExclusions({"ru.itaros.hoe.coremod"})
public class HOEFMLPlugin implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"ru.itaros.hoe.coremod.FluidHOEConversionFieldInjector"};
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
