package ru.itaros.chemlab.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class AdvancedComponentItemBlock extends ItemBlock {
	
	
	
	public AdvancedComponentItemBlock(Block p_i45328_1_) {
		super(p_i45328_1_);
		
		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
}
