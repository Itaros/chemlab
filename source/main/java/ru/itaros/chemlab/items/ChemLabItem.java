package ru.itaros.chemlab.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.achievements.ChemLabAchievements;

public class ChemLabItem extends Item {

	
	private String name;
	public ChemLabItem(String name){
		super();
		this.name=name;
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setUnlocalizedName("genericitem."+name);
		this.setTextureName("chemlab:genericitem."+name);
	}
	@Override
	public void onCreated(ItemStack stack, World world,
			EntityPlayer player) {
		ChemLabAchievements.onCrafting(player,stack);
		super.onCreated(stack, world, player);
	}
	
}
