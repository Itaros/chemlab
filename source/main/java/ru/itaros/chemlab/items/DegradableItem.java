package ru.itaros.chemlab.items;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.item.Item;

public class DegradableItem extends Item {

	//asbestos-diaphragm
	
	private String internalName;
	public DegradableItem(String name, int uses){
		super();
		internalName = name;
		this.setUnlocalizedName("chemlab:"+internalName);
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setMaxStackSize(1);
		this.setMaxDamage(uses);
		this.setTextureName("chemlab:"+internalName);
	}
	
	
}
