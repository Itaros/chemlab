package ru.itaros.chemlab.addon.bc.builder;

import java.util.LinkedList;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold.ManifoldFilter;
import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.hoe.blocks.IRotatableBlock;
import ru.itaros.hoe.blocks.RotatableBlockUtility;
import ru.itaros.hoe.tiles.MachineTileEntity;
import buildcraft.api.blueprints.BuilderAPI;
import buildcraft.api.blueprints.BuildingPermission;
import buildcraft.api.blueprints.IBuilderContext;
import buildcraft.api.blueprints.MappingNotFoundException;
import buildcraft.api.blueprints.MappingRegistry;
import buildcraft.api.blueprints.SchematicTile;

public class SchematicChemLabMachine extends SchematicTile {

	public SchematicChemLabMachine(){}
	
	private HOENBTManifold manifold;
	
	private boolean isSyndicated=false;
	
	@Override
	public void initializeFromObjectAt(IBuilderContext context, int x, int y,
			int z) {

		MachineTileEntity tile = (MachineTileEntity)context.world().getTileEntity(x, y, z);

		if (tile != null) {
			manifold = tile.writeBlueprintNBT();
		}
	
	}

	@Override
	public void placeInWorld(IBuilderContext context, int x, int y,
			int z, LinkedList<ItemStack> stacks) {
		//super.writeToWorld(context, x, y, z, stacks);

		context.world().setBlock(x, y, z, block, meta, 3);
		context.world().setBlockMetadataWithNotify(x, y, z, meta, 3);
		
		if (block.hasTileEntity(meta)) {
			MachineTileEntity tile = (MachineTileEntity) context.world().getTileEntity(x, y, z);
	
//			if(tile==null){
//				tile=(MachineTileEntity) block.createTileEntity(context.world(), 0);
			//To ensure correct HOEFingerprint Data
			//TODO: shouldn't do that. It should be in manifolded postloader
			tile.setWorldObj(context.world());
			tile.xCoord=x;
			tile.yCoord=y;
			tile.zCoord=z;
			tile.onPostLoad();
//			}
			
			//if (tile != null) {
			//Tile data should be accessed imperatively
			tile.readBlueprintNBT(manifold);
			//}
		}		
	}

	@Override
	public void writeSchematicToNBT(NBTTagCompound nbt, MappingRegistry registry) {
		nbt.setInteger("blockId", registry.getIdForBlock(block));
		nbt.setInteger("blockMeta", meta);
		
		manifold.filter(ManifoldFilter.BCSCHEMATICS,true);
		manifold.mergeInto(nbt);
	}
	
	@Override
	public void readSchematicFromNBT(NBTTagCompound nbt, MappingRegistry registry) {
		try {
			block = registry.getBlockForId(nbt.getInteger("blockId"));
		} catch (MappingNotFoundException e) {
			defaultPermission = BuildingPermission.CREATIVE_ONLY;
			return;
		}
		
		meta = nbt.getInteger("blockMeta");
		
		manifold = HOENBTManifold.deploy(nbt);
	}	

	
	@Override
	public void storeRequirements(IBuilderContext context, int x,
			int y, int z) {
		
	}
	
	

	@Override
	public void getRequirementsForPlacement(IBuilderContext arg0,
			LinkedList<ItemStack> requirements) {
		requirements.add(new ItemStack(block, 1));
	}

	@Override
	public void postProcessing(IBuilderContext context, int x, int y, int z) {
		super.postProcessing(context, x, y, z);
		if(isSyndicated){
			MachineTileEntity tile = (MachineTileEntity) context.world().getTileEntity(x, y, z);
		}
	}

	public static void init(Block...blocks){
		for(Block b:blocks){
			if(BuilderAPI.schematicRegistry!=null){
				BuilderAPI.schematicRegistry.registerSchematicBlock(b, SchematicChemLabMachine.class);
			}
		}
	}

	@Override
	public void rotateLeft(IBuilderContext context) {
		IRotatableBlock irb = (IRotatableBlock)block;
		ForgeDirection[] rotChain = irb.getRotationChain();
		meta = RotatableBlockUtility.calculateSpinIncrement(meta,rotChain.length);
	}	
	
	
	
	
}
