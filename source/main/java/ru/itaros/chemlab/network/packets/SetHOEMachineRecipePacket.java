package ru.itaros.chemlab.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.chemlab.network.IPacketCodecDescriptor;
import ru.itaros.hoe.vanilla.tiles.MachineCrafterTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.Recipe;
import cpw.mods.fml.common.network.ByteBufUtils;

public class SetHOEMachineRecipePacket implements IPacketCodecDescriptor {

	//DATA
	int x,y,z;
	int dim;
	String rectoken;
	//ACCESSORS
	public void execute(){
		System.out.println("ATTEMPTING TO CHANGE RECIPE");
		World w = DimensionManager.getWorld(dim);
		TileEntity tile = w.getTileEntity(x, y, z);
		if(tile==null){return;}
		if(tile instanceof MachineCrafterTileEntity){
			//Trying to get recipe
			IHOERecipeRegistry repreg = Recipe.getRecipeRegistry();
			Recipe r = repreg.get(rectoken);//TODO: CAN CRASH SERVER. Need to discard on error.
			//Setting
			MachineCrafterTileEntity me = (MachineCrafterTileEntity)tile;
			me.trySetRecipe(r);
			System.out.println("RECIPE CHANGED");
		}else{
			return;
		}
	}
	//EXEC
	public SetHOEMachineRecipePacket(){} //we need this for the packet decoding
	public SetHOEMachineRecipePacket(MachineCrafterTileEntity tile, Recipe r){
		//Getting coordinates
		x = tile.xCoord;
		y = tile.yCoord;
		z = tile.zCoord;
		World w = tile.getWorldObj();
		dim = w.provider.dimensionId;
		//Preparing recipe for marshalling
		rectoken = r.getName();
		
		//TODO: needs to validate if hardware programmer is available and has rights
	}
	//
	
	public static int getInternalCodecID() {
		return 0;
	}

	@Override
	public void readBytes(ByteBuf bytes) {
		x=bytes.readInt();
		y=bytes.readInt();
		z=bytes.readInt();
		dim=bytes.readInt();
		rectoken = ByteBufUtils.readUTF8String(bytes);
	}

	@Override
	public void writeBytes(ByteBuf bytes) {
		bytes.writeInt(x);
		bytes.writeInt(y);
		bytes.writeInt(z);
		bytes.writeInt(dim);
		
		//TODO: change to ASCII to be more compact
		ByteBufUtils.writeUTF8String(bytes, rectoken);

	}

}
