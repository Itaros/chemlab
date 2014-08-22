package ru.itaros.chemlab.blocks.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class StructuralItemBlock extends ItemBlock {

	
	
	public StructuralItemBlock(Block block) {
		super(block);

		this.setHasSubtypes(true);
	}

	@Override
	public int getMetadata (int damageValue) {
		return damageValue;
	}
	
}
