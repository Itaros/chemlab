package ru.itaros.chemlab.addon.bc.builder;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import buildcraft.api.blueprints.BuilderAPI;
import buildcraft.api.blueprints.BuildingPermission;
import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.MappingNotFoundException;
import buildcraft.api.blueprints.MappingRegistry;
import buildcraft.api.blueprints.SchematicTile;

//Requires clean creation(without NBT)
public class SchematicSyndicationBus extends SchematicTile {

	@Override
	public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
		try {
			block = registry.getBlockForId(nbt.getInteger("blockId"));
		} catch (MappingNotFoundException e) {
			defaultPermission = BuildingPermission.CREATIVE_ONLY;
			return;
		}		
	}

	@Override
	public void initializeFromObjectAt(IBuilderContext arg0, int arg1, int arg2,
			int arg3) {	
	}

	@Override
	public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
		nbt.setInteger("blockId", registry.getIdForBlock(block));
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y,
			int z, LinkedList<ItemStack> arg4) {

		context.world().setBlock(x, y, z, block, meta, 3);
	}

	@Override
	public void getRequirementsForPlacement(IBuilderContext arg0,
			LinkedList<ItemStack> requirements) {
		requirements.add(new ItemStack(block, 1));
	}

	public static void init(Block...blocks){
		for(Block b:blocks){
			if(BuilderAPI.schematicRegistry!=null){
				BuilderAPI.schematicRegistry.registerSchematicBlock(b, SchematicSyndicationBus.class);
			}
		}
	}		
	
	
}
