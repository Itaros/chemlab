package ru.itaros.chemlab.items;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.item.Item;

public class WoodchipClump extends Item {
	public WoodchipClump(){
		super();
		this.setUnlocalizedName("chemlab:"+getClass().getSimpleName());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:woodchipclump");
	}
}
