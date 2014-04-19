package ru.itaros.chemlab.items;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.item.Item;

public class ChemLabItem extends Item {

	
	private String name;
	public ChemLabItem(String name){
		super();
		this.name=name;
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setUnlocalizedName("genericitem."+name);
		this.setTextureName("chemlab:genericitem."+name);
	}
	
}
