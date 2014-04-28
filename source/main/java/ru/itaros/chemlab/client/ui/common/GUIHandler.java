package ru.itaros.chemlab.client.ui.common;

import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.minecraft.tileentity.BiogrinderTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.tileentity.MachineCrafterTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GUIHandler implements IGuiHandler {

	private static Class<? extends HOEContainer>[] index;
	
	public static void registerGUIs(Class<? extends HOEContainer>... class1){
		try{
			index = (Class<? extends HOEContainer>[]) new Class<?>[class1.length];
			for(Class<? extends HOEContainer> g:class1){
				int order=g.getField("ID").getInt(null);
				if(index[order]!=null){
					System.err.println("There is dublicate UI detected!");
				}
				index[order]=g;
			}
		}catch(Exception e){
			throw new RuntimeException("Failed to register UIs",e);
		}
	}
	
	public GUIHandler(){
		super();
		NetworkRegistry.INSTANCE.registerGuiHandler(ChemLab.getInstance(), this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		
		try{
			Class<? extends HOEContainer> guitr = index[ID];
			return guitr.getConstructor(InventoryPlayer.class,MachineCrafterTileEntity.class).newInstance(player.inventory,te);
			
		}catch(Exception e){
			System.err.println("Something wrong with UI(SERVER)!");
		}		
		
		//switch(ID){
		//case GUIBiogrinder.ID:
		//	BiogrinderTileEntity bte = (BiogrinderTileEntity)te;
		//	return new BiogrinderContainer(player.inventory,bte);
		//}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);	
		
		try{
			Class<? extends HOEContainer> guitr = index[ID];
			Class<? extends GUIHOEClassicalMachine> containert = (Class<? extends GUIHOEClassicalMachine>) guitr.getMethod("getGUIType").invoke(null);
			return containert.getConstructor(InventoryPlayer.class,MachineCrafterTileEntity.class).newInstance(player.inventory,te);
		}catch(Exception e){
			System.err.println("Something wrong with UI(CLIENT)!");
		}
			//switch(ID){
		//case GUIBiogrinder.ID:
		//	BiogrinderTileEntity bte = (BiogrinderTileEntity)te;
		//	return new GUIBiogrinder(player.inventory,bte);
		//}		
		return null;
	}

}
