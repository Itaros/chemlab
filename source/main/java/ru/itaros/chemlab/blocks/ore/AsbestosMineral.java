package ru.itaros.chemlab.blocks.ore;

import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class AsbestosMineral extends Block {

	public enum AsbestosMineralType{
		Crocidolite,	//AMPHIBOLE  ASBESTOS(Igneous rocks-related)
		Tremolite,		//AMPHIBOLE  ASBESTOS(Quartz-related)
		Anthophyllite,	//AMPHIBOLE  ASBESTOS(-)
		Actinolite,		//AMPHIBOLE  ASBESTOS(Igneous rocks-related)
		Serpentinite	//SERPENTINE ASBESTOS
	}
	
	
	public AsbestosMineral(AsbestosMineralType type) {
		super(Material.rock);

		this.setBlockName("asbestosmineral."+type.toString());
		this.setBlockTextureName("chemlab:asbestosmineral-"+type.toString());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		
		this.setHardness(ChemLabValues.BASE_ORE_HARDNESS/2);
		this.setResistance(ChemLabValues.BASE_ORE_RESISTANCE);		
		
		this.setHarvestLevel("pickaxe", 1);
	}

}
