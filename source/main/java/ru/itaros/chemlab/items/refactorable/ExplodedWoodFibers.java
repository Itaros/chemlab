package ru.itaros.chemlab.items.refactorable;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.item.Item;

public class ExplodedWoodFibers extends Item {
	
	public ExplodedWoodFibers(){
		super();
		this.setUnlocalizedName("chemlab:"+getClass().getSimpleName());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:explodedwoodfibers");
	}
	
}
