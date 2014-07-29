package ru.itaros.chemlab.blocks.ore;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLabCreativeTab;
import ru.itaros.chemlab.ChemLabValues;
import ru.itaros.chemlab.achievements.ChemLabAchievements;

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


	@Override
	public void onBlockHarvested(World world, int p_149681_2_,
			int p_149681_3_, int p_149681_4_, int p_149681_5_,
			EntityPlayer player) {
		ChemLabAchievements.checkOre(player, this);
		super.onBlockHarvested(world, p_149681_2_, p_149681_3_, p_149681_4_,
				p_149681_5_, player);
	}
	
	
	
}
