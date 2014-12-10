package ru.itaros.hoe.adapter;

import ru.itaros.hoe.adapter.envirolab.ImpostorAdapterEnviroLab;

public final class HOEAdapters {
	private static HOEAdapters me;
	public static HOEAdapters getInstance(){
		return me;
	}
	public HOEAdapters(){
		me=this;
	}
	
	private ImpostorAdapterEnviroLab enviroLab=new ImpostorAdapterEnviroLab();
	public ImpostorAdapterEnviroLab getEnviroLab(){
		return enviroLab;
	}
	
}
