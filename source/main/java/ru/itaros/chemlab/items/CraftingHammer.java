package ru.itaros.chemlab.items;

import ru.itaros.chemlab.ChemLabCreativeTab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CraftingHammer extends Item {

	public CraftingHammer(){
		this.setUnlocalizedName("crafting.hammer");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:hammer");
		this.setMaxStackSize(1);
		this.setMaxDamage(100);
	}

	@Override
	public Item getContainerItem() {
		return null;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		ItemStack container = itemStack.copy();
		container.setItemDamage(itemStack.getItemDamage()+1);
		return container;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
		return !(stack.getItemDamage()==100);
	}
	
	
	
}
