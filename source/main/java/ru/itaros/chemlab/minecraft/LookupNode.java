package ru.itaros.chemlab.minecraft;

import java.util.ArrayList;

import ru.itaros.api.chemlab.syndication.utilities.ISyndicationUtility;
import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping;
import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping.PipingMode;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationHubTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;

public class LookupNode {

	boolean isFinished=false;
	boolean isSplitted=false;
	
	int x;
	int y;
	int z;
	
	int heat;
	
	
	public int getHeat() {
		return heat;
	}


	public void setHeat(int heat) {
		this.heat = heat;
	}


	int splits;
	LookupNode[] branches = new LookupNode[5];
	
	
	protected SyndicationHubTileEntity host;
	
	LookupNode father;
	public LookupNode(int x, int y, int z, SyndicationHubTileEntity host) {
		super();
		
		this.host=host;
		
		this.x = x;
		this.y = y;
		this.z = z;
		
		//this.snapshot_hash=snapshotHash;
		this.heat=1;
	}
	
	
		int snap_x;
		int snap_y;
		int snap_z;	
	
		int step_x=x;
		int step_y=y;
		int step_z=z;		
		
	public void step(World world) {
		
		if(isFinished){
			
			
			return;
		}		
		
		if(isSplitted){
			int splitcounter = branches.length;
			for(LookupNode node:branches){
				if(node==null){splitcounter--;continue;}
				this.isConflicted=node.isConflicted;//Regain conflict status
				if(node.isFinished){splitcounter--;continue;}
				node.step(world);
			}
			if(splitcounter==0){
				isFinished=true;
			}
			return;
		}
		

		
		
		ISyndicationPiping operative;
		ISyndicationPiping confirmed=null;
		
		
		
		int sides=0;
		
		stepBack();
		//try right
		step_x++;
		operative = getBySide(world, step_x,step_y,step_z);
		if(operative!=null){
			sides++;
			confirmed=operative;
			setSnap();
		}
		
		stepBack();
		//try left
		step_x--;
		operative = getBySide(world, step_x,step_y,step_z);
		if(operative!=null){
			sides++;
			if(sides>1){
				pushSplit(confirmed);
			}
			confirmed=operative;
			setSnap();
		}	
		stepBack();
		//try forward
		step_z++;
		operative = getBySide(world, step_x,step_y,step_z);
		if(operative!=null){
			sides++;
			if(sides>1){
				pushSplit(confirmed);
			}
			confirmed=operative;
			setSnap();
		}	
		stepBack();
		//try backward
		step_z--;
		operative = getBySide(world, step_x,step_y,step_z);
		if(operative!=null){
			sides++;
			if(sides>1){
				pushSplit(confirmed);
			}
			confirmed=operative;
			setSnap();
		}		
		stepBack();
		//try up
		step_y++;
		operative = getBySide(world, step_x,step_y,step_z);
		if(operative!=null){
			sides++;
			if(sides>1){
				pushSplit(confirmed);
			}
			confirmed=operative;
			setSnap();
		}
		stepBack();
		//try down
		step_y--;
		operative = getBySide(world, step_x,step_y,step_z);
		if(operative!=null){
			sides++;
			if(sides>1){
				pushSplit(confirmed);
			}
			confirmed=operative;
			setSnap();
		}		
		
		if(sides==1){
			stepFurther(confirmed);
		}else if(sides!=0){
			pushSplit(confirmed);
		}else{
			isFinished=true;
		}
		//TODO: 0 and 256 stuff
		
		
		
		
	}
	
	
	public boolean isFinished() {
		return isFinished;
	}


	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}


	private void stepFurther(ISyndicationPiping confirmed) {
		x = confirmed.getX();
		y = confirmed.getY();
		z = confirmed.getZ();
		setAsSearched(confirmed);
		heat++;
	}


	protected void setAsSearched(ISyndicationPiping confirmed){
		confirmed.setHeat(heat);
		confirmed.setMode(PipingMode.SEARCHING);
		confirmed.setBlockMetadata();
		if(fillable!=null){
			fillable.add(confirmed);
		}
	}
	
	protected LookupNode getNewSplitInitiator(ISyndicationPiping operative){
		return new LookupNode(operative.getX(),operative.getY(),operative.getZ(),host).setFather(this);
	}
	
	private void pushSplit(ISyndicationPiping operative) {
		isSplitted=true;
		branches[splits] = getNewSplitInitiator(operative);
		branches[splits].setFillable(fillable);
		setAsSearched(operative);
		branches[splits].setHeat(heat+1);
		splits++;
	}


	protected LookupNode setFather(LookupNode lookupNode) {
		father=lookupNode;
		return this;
	}


	private void stepBack(){
		step_x=x;
		step_y=y;
		step_z=z;		
	}
	private void setSnap() {
		snap_x=step_x;
		snap_y=step_y;
		snap_z=step_z;
	}

	protected boolean validProgression(ISyndicationPiping spte){
		int local_heat = spte.getHeat();
		return local_heat==0;
	}

	private ISyndicationPiping getBySide(World world, int step_x,int step_y,int step_z){
		TileEntity tile = world.getTileEntity(step_x, step_y, step_z);
		if(tile instanceof ISyndicationPiping){
			ISyndicationPiping spte = (ISyndicationPiping)tile;
			//int local_heat = spte.getHeat();
			if(validProgression(spte)){
				return spte;
			}else{
				//This is null if host is the same.
				//If host is not valid this means conflict
				//Btw, feels like crash
				SyndicationHubTileEntity controller = spte.getController(spte.getWorld());
				if(this.host==controller || controller==null){
					return null;
				}else{
					//Oops, conflict!
					setConflicted();
					setFinished(true);
					return null;
				}
			}
		}else{
			return null;
		}
	}


	private boolean isConflicted;
	public boolean isConflicted(){
		return isConflicted;
	}
	private void setConflicted() {
		isConflicted=true;
	}


	ArrayList<ISyndicationPiping> fillable;

	
	public void setFillable(ArrayList<ISyndicationPiping> pipesInSystem) {
		fillable=pipesInSystem;
	}


	
	
	
	
	
}
