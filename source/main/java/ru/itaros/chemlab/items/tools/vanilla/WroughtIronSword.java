package ru.itaros.chemlab.items.tools.vanilla;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class WroughtIronSword extends ItemSword {

	public WroughtIronSword() {
		super(Item.ToolMaterial.IRON);
		this.setUnlocalizedName("tool.sword.wrought");
		this.setTextureName("chemlab:tool.sword.wrought");
	}

}
