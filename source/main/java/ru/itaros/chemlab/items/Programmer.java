package ru.itaros.chemlab.items;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.achievements.ChemLabAchievements;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class Programmer extends Item {

	public Programmer(){
		super();
		this.setUnlocalizedName(getClass().getSimpleName().toLowerCase());
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:programmer");
		
		this.setMaxStackSize(1);

	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.epic;
	}
	
	
	
	@Override
	public void onCreated(ItemStack stack, World world,
			EntityPlayer player) {
		ChemLabAchievements.onCrafting(player,stack);
		super.onCreated(stack, world, player);
	}	
	
	
	
}
