package ru.itaros.chemlab.items.tools.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class WroughtIronAxe extends ItemAxe {

	public WroughtIronAxe() {
		super(Item.ToolMaterial.IRON);
		this.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses()/2);
		this.setUnlocalizedName("tool.axe.wrought");
		this.setTextureName("chemlab:tool.axe.wrought");
		
	}

}
