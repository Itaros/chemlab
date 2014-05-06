package ru.itaros.chemlab.items.tools.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;

public class WroughtIronHoe extends ItemHoe {

	public WroughtIronHoe() {
		super(Item.ToolMaterial.IRON);
		this.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses()/2);
		this.setUnlocalizedName("tool.hoe.wrought");
		this.setTextureName("chemlab:tool.hoe.wrought");
	}

}
