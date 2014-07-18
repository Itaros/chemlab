package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ReadonlySlot extends Slot {


	
	private HOESlotType type;
	
	public final HOESlotType getType() {
		return type;
	}

	public final Slot setType(HOESlotType type) {
		this.type = type;
		return this;
	}

	public ReadonlySlot(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void putStack(ItemStack par1ItemStack) {
		return;
	}

	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int slot) {
		return new ItemStack(Block.getBlockFromName("dirt"),0);
	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer) {
		return false;
	}


	
	
	

}
