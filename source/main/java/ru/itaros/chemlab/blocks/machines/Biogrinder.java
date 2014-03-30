package ru.itaros.chemlab.blocks.machines;

import net.minecraft.block.material.Material;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.client.ui.BiogrinderContainer;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.blocks.IOMachineBlock;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineTileEntity;

public class Biogrinder extends IOMachineBlock {

	
	@Override
	protected Object getOwnerMod() {
		return ChemLab.getInstance();
	}	
	@Override
	protected int getUIID() {
		return BiogrinderContainer.ID;
	}	
	

	public Biogrinder() {
		super(Material.iron);
		this.setBlockNameRaw("chemlab:machinebiogrinder");
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		//this.setTickRandomly(true);
	}


	@Override
	protected MachineTileEntity getNewTileEntity() {
		return new BiogrinderTileEntity();
	}









	
	
	
	
	

}
