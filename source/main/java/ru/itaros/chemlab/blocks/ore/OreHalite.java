package ru.itaros.chemlab.blocks.ore;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class OreHalite extends Block {

	public OreHalite() {
		super(Material.glass);
		this.setBlockName("ore.halite");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setBlockTextureName("chemlab:ore_halite");
	}

	
	
	
	
}
