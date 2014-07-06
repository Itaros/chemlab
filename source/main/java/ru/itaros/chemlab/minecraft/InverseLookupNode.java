package ru.itaros.chemlab.minecraft;

import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping;
import ru.itaros.chemlab.minecraft.tileentity.syndication.ISyndicationPiping.PipingMode;
import ru.itaros.chemlab.minecraft.tileentity.syndication.SyndicationHubTileEntity;

public class InverseLookupNode extends LookupNode {

	public InverseLookupNode(int x, int y, int z, SyndicationHubTileEntity host) {
		super(x, y, z, host);
	}
	
	@Override
	protected boolean validProgression(ISyndicationPiping spte){
		SyndicationHubTileEntity controller = spte.getController(spte.getWorld());
		
		return this.host==controller & spte.getMode()==PipingMode.ACTIVE;
	}	
	
	
	@Override
	protected LookupNode getNewSplitInitiator(ISyndicationPiping operative){
		return new InverseLookupNode(operative.getX(),operative.getY(),operative.getZ(),host).setFather(this);
	}
	
	
	@Override	
	protected void setAsSearched(ISyndicationPiping confirmed){
		confirmed.setHeat(0);
		confirmed.setMode(PipingMode.SEARCHING);
		confirmed.setBlockMetadata();
		if(fillable!=null){
			fillable.add(confirmed);
		}
	}	

}
