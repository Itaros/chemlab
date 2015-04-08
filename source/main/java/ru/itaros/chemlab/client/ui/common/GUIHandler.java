package ru.itaros.chemlab.client.ui.common;

import java.lang.reflect.Constructor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.hoe.tiles.MachineCrafterTileEntity;
import ru.itaros.hoe.tiles.MachineTileEntity;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class GUIHandler implements IGuiHandler {

	private static Class<? extends HOEContainer>[] index;
	
	public static void registerGUIs(Class<? extends HOEContainer>... class1){
		try{
			index = (Class<? extends HOEContainer>[]) new Class<?>[class1.length];
			int i=-1;
			for(Class<? extends HOEContainer> g:class1){
				i++;
				index[i]=g;
				HOEContainer.setID(g, i);
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

//			Constructor<?>[] constructors = guitr.getConstructors();
//			for(Constructor<?> c:constructors){
//				Class<?>[] params = c.getParameterTypes();
//				if(params.length==2){
//					if(params[0]==InventoryPlayer.class & params[1]==MachineTileEntity.class){
//						return c.newInstance(player.inventory,te);
//					}
//				}
//			}
			
			return guitr.getConstructor(InventoryPlayer.class,MachineTileEntity.class).newInstance(player.inventory,te);

			
		}catch(Exception e){
			System.err.println("Something wrong with UI(SERVER)!");
			System.err.println(e);
			e.printStackTrace();
		}		
		System.err.println("Something wrong with UI(SERVER) - ACQUISITION FAILED!");
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
			Class<? extends Container> guitr = index[ID];
			Class<? extends GUIHOEClassicalMachine> containert = (Class<? extends GUIHOEClassicalMachine>) guitr.getMethod("getGUIType").invoke(null);
			return containert.getConstructor(InventoryPlayer.class,MachineTileEntity.class).newInstance(player.inventory,te);
		}catch(Exception e){
			System.err.println("Something wrong with UI(CLIENT)!");
			System.err.println(e);
		}
			//switch(ID){
		//case GUIBiogrinder.ID:
		//	BiogrinderTileEntity bte = (BiogrinderTileEntity)te;
		//	return new GUIBiogrinder(player.inventory,bte);
		//}		
		return null;
	}

}
