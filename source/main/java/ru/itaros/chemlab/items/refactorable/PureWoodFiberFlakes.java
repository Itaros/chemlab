package ru.itaros.chemlab.items.refactorable;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.item.Item;

public class PureWoodFiberFlakes extends Item {

	public PureWoodFiberFlakes(){
		super();
		this.setUnlocalizedName("chemlab:"+getClass().getSimpleName());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:purelignocelluloseflakes");
	}
	
}
