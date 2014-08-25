package ru.itaros.chemlab.blocks.multiblock;

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
		layers[0]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,2,2,2,2,2,0,
			0,2,2,2,2,2,0,
			0,2,2,2,2,2,0,
			0,2,2,2,2,2,0,
			0,2,2,2,2,2,0,
			0,0,0,0,0,0,0
		});
		layers[1]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,2,2,2,2,2,0,
			0,2,3,3,3,2,0,
			0,2,3,3,3,2,0,
			0,2,3,3,3,2,0,
			0,2,2,2,2,2,0,
			0,0,0,0,0,0,0
		});	
		layers[2]=new MultiblockTemplateLayer(new short[]{
			0,2,2,2,2,2,0,
			0,3,3,3,3,3,0,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			0,3,3,3,3,3,0,
			0,2,2,2,2,2,0
		});	
		layers[3]=new MultiblockTemplateLayer(new short[]{
			0,2,2,2,2,2,0,
			0,3,3,3,3,3,0,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			2,3,-1,-1,-1,3,2,
			0,3,3,3,3,3,0,
			0,2,2,2,2,2,0
		});		
		layers[4]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			2,0,0,4,0,0,2,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0
		});	
		layers[5]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,3,3,3,0,0,
			2,2,2,4,2,2,2,
			0,0,3,3,3,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0
		});			
		layers[6]=new MultiblockTemplateLayer(new short[]{
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,1,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0,
			0,0,0,0,0,0,0
		});		
		makeSearchRoot(6,DEFID_CU);
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
	public int getYDim() {
		return 7;
	}
	
}
