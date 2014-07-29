package ru.itaros.chemlab.blocks.ore;

import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.ChemLabValues;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class OreHalite extends Block {

	public OreHalite() {
		super(Material.glass);
		this.setBlockName("ore.halite");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setBlockTextureName("chemlab:ore_halite");
		
		this.setHardness(ChemLabValues.BASE_ORE_HARDNESS/2);
		this.setResistance(ChemLabValues.BASE_ORE_RESISTANCE/2);		
		
		this.setHarvestLevel("shovel", 0);
	}

	
	
	
	
}
