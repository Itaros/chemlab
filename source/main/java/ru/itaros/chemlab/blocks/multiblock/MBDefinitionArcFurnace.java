package ru.itaros.chemlab.blocks.multiblock;

import ru.itaros.chemlab.blocks.AdvancedComponentBlock;
import ru.itaros.chemlab.blocks.AdvancedComponentBlock.AdvancedComponentBlockType;
import ru.itaros.chemlab.loader.BlockLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class MBDefinitionArcFurnace extends MBDefinition{
	
	public final static short DEFID_FORCEEMPTY=-1;
	public final static short DEFID_IRRELEVANT=0;
	public final static short DEFID_CHASSIS=2;
	public final static short DEFID_INSULATION=3;
	public final static short DEFID_ELECTRODES=4;
	public final static short DEFID_CU=1;
	
	//HEIGHT:	6
	//DIMS	:	7x7
	//STATIC:	YES
	
	//Supergeil Arc Furnace
	//The first CL Multiblock
	//Idea by TrinaryBrain
	
	
	public MBDefinitionArcFurnace(){
		
		set_chassis=new ItemStack[]{new ItemStack(Blocks.iron_block,1)};
		set_insulation=new ItemStack[]{new ItemStack(Blocks.brick_block,1),new ItemStack(BlockLoader.structblock,1,0),new ItemStack(BlockLoader.structblock,1,1)};
		
		layers[0]=new MultiblockTemplateLayer(new short[]{
			0,0,0,2,0,0,0,
			0,2,2,2,2,2,0,
			0,2,2,2,2,2,0,
			2,2,2,2,2,2,2,
			0,2,2,2,2,2,0,
			0,2,2,2,2,2,0,
			0,0,0,2,0,0,0
		},this);
		layers[1]=new MultiblockTemplateLayer(new short[]{
			0,0,0,2,0,0,0,
			0,2,2,2,2,2,0,
			0,2,3,3,3,2,0,
			2,2,3,3,3,2,2,
			0,2,3,3,3,2,0,
			0,2,2,2,2,2,0,
			0,0,0,2,0,0,0
		},this);	
		layers[2]=new MultiblockTemplateLayer(new short[]{
			0,2,2,2,2,2,0,
			2,3,3,3,3,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,3,3,3,3,2,
			0,2,2,2,2,2,0
		},this);	
		layers[3]=new MultiblockTemplateLayer(new short[]{
			0,2,2,2,2,2,0,
			2,3,3,3,3,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,3,3,3,3,2,
			0,2,2,2,2,2,0
		},this);		
		layers[4]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			2,0,0,4,0,0,2,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0
		},this);	
		layers[5]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,3,3,3,0,0,
			2,2,3,4,3,2,2,
			0,0,3,3,3,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0
		},this);			
		layers[6]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,1,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0
		},this);		
		makeSearchRoot(6,DEFID_CU);
		
		prepareSamplesValues();
	}

	@Override
	public int getLevels(){
		return 6+1;
	}

	@Override
	public int getXDim() {
		return 7;
	}
	@Override
	public int getZDim() {
		return 7;
	}

	private ItemStack[] set_chassis;
	private ItemStack[] set_insulation;
	
	@Override
	public boolean compare(MBAssociativeDataPayload payload, Block block, int meta, TileEntity te, short query) {
		boolean isTrue=false;
		Integer step;
		
		switch(query){
		case DEFID_FORCEEMPTY:
			isTrue = block==Blocks.air;
			break;
		case DEFID_IRRELEVANT:
			isTrue = true;
			break;
		case DEFID_CHASSIS:
			step = compareBlock(block, meta, set_chassis);
			if(step>-1){
				isTrue=true;
				Integer volresist = (Integer) payload.get("volresist");
				if(volresist==null){volresist=0;}
				volresist+=step;
			}
			break;
		case DEFID_INSULATION:
			step = compareBlock(block, meta, set_insulation);
			if(step>-1){
				isTrue=true;
				Integer tempresist = (Integer) payload.get("tempresist");
				if(tempresist==null){tempresist=0;}
				tempresist+=step;
			}					
			break;
		case DEFID_ELECTRODES:
			isTrue = block==BlockLoader.advcompblock&&((AdvancedComponentBlock)block).getType(meta)==AdvancedComponentBlockType.ArcFurnaceElectrodes;
			break;
		case DEFID_CU:
			isTrue = block==BlockLoader.controller_arcFurnace||block==Blocks.air;
			break;
		default:
			throw new IllegalArgumentException("MBDefinition bounds error. "+query+" is not expected!");
		}
		return isTrue;
		
	}

	public int getChassisLevels(){
		return set_chassis.length;
	}
	public int getInsulationLevels(){
		return set_insulation.length;
	}
	
	
	@Override
	public boolean isSuatableForTEPointer(short query) {
		return super.isSuatableForTEPointer(query) && query!=DEFID_CU;
	}
	
	
	
	
}
