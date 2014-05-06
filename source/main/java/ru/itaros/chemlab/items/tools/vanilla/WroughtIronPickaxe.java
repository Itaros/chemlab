package ru.itaros.chemlab.items.tools.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class WroughtIronPickaxe extends ItemPickaxe {

	public WroughtIronPickaxe() {
		super(Item.ToolMaterial.IRON);
		this.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses()/2);
		this.setUnlocalizedName("tool.pickaxe.wrought");
		this.setTextureName("chemlab:tool.pickaxe.wrought");
	}

}
