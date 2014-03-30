package ru.itaros.chemlab.client.ui;

import ru.itaros.chemlab.client.ui.common.HOEContainer;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.debug.RecipeSetSlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.gui.ReadonlySlot;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class BiogrinderContainer extends HOEContainer {

	public static final int ID = 0;
	
	public BiogrinderContainer(InventoryPlayer playerInv, MachineTileEntity tile) {
		super(playerInv, tile);
	}


	
}
