package ru.itaros.chemlab.items.tools.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;

public class WroughtIronSpade extends ItemSpade {

	public WroughtIronSpade() {
		super(Item.ToolMaterial.IRON);
		this.setMaxDamage(Item.ToolMaterial.IRON.getMaxUses()/2);
		this.setUnlocalizedName("tool.shovel.wrought");
		this.setTextureName("chemlab:tool.shovel.wrought");
	}

}
