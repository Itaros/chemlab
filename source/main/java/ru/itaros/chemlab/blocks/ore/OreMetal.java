package ru.itaros.chemlab.blocks.ore;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.oredict.OreDictionary;

public class OreMetal extends Block {

	public OreMetal(String group) {
		super(Material.rock);
		this.setBlockName("ore."+group.toLowerCase());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setBlockTextureName("chemlab:ore_"+group.toLowerCase());
		
	}
	
	
}
