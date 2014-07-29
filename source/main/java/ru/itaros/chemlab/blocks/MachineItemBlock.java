package ru.itaros.chemlab.blocks;

import ru.itaros.chemlab.achievements.ChemLabAchievements;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MachineItemBlock extends ItemBlock {

	public MachineItemBlock(Block block) {
		super(block);
	}

	@Override
	public void onCreated(ItemStack stack, World par2World,
			EntityPlayer player) {
		ChemLabAchievements.onCrafting(player, stack);
		super.onCreated(stack, par2World, player);
	}
	
	

}
