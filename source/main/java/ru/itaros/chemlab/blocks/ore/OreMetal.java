package ru.itaros.chemlab.blocks.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;

public class OreMetal extends Block {

	public OreMetal(String group) {
		super(Material.rock);
		this.setBlockName("ore."+group.toLowerCase());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setBlockTextureName("chemlab:ore_"+group.toLowerCase());
		
		this.setHardness(ChemLabValues.BASE_ORE_HARDNESS);
		this.setResistance(ChemLabValues.BASE_ORE_RESISTANCE);
		
		this.setHarvestLevel("pickaxe", 1);
		
	}
	
	
}
