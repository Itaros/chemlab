package ru.itaros.chemlab.client.ui;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GUIHandler implements IGuiHandler {

	public GUIHandler(){
		super();
		NetworkRegistry.INSTANCE.registerGuiHandler(ChemLab.getInstance(), this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		switch(ID){
		case GUIBiogrinder.ID:
			BiogrinderTileEntity bte = (BiogrinderTileEntity)te;
			return new BiogrinderContainer(player.inventory,bte);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);		
		switch(ID){
		case GUIBiogrinder.ID:
			BiogrinderTileEntity bte = (BiogrinderTileEntity)te;
			return new GUIBiogrinder(player.inventory,bte);
		}		
		return null;
	}

}
