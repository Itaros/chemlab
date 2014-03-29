package ru.itaros.chemlab.convenience;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ChemLabCreativeTab extends CreativeTabs {

	private static ChemLabCreativeTab instance;
	
	private static final String TAB_NAME = "ChemLab";

	public static CreativeTabs getInstance(){
		return instance;
	}
	
	public ChemLabCreativeTab() {
		super(TAB_NAME);
		instance=this;
	}

	@Override
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Block.getBlockFromName("dirt"));
	}

}
