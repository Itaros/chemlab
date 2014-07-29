package ru.itaros.chemlab.minecraft.tileentity.syndication;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.itaros.api.chemlab.syndication.utilities.ISyndicationUtility;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.HOELinker;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationHubData;
import ru.itaros.chemlab.hoe.syndication.SyndicationHubIO;
import ru.itaros.chemlab.minecraft.InverseLookupNode;
import ru.itaros.chemlab.minecraft.LookupNode;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.vanilla.tiles.MachineTileEntity;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachines;
import ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO;

public class SyndicationHubTileEntity extends MachineTileEntity {

	ArrayList<ISyndicationPiping> pipesInSystem = new ArrayList<ISyndicationPiping>();
	ArrayList<HOEMachineData> detectedMachinery = new ArrayList<HOEMachineData>();
	
	@Override
	protected HOEMachineData acquareData(HOEMachines machines, HOEDataFingerprint fingerprint) {
		SyndicationHubData sbd= new SyndicationHubData();
		sbd.setOwnerFingerprint(fingerprint);
		machines.injectCustomData(sbd);
		return sbd;
	}

	@Override
	public HOELinker getLinker() {
		return ChemLab.proxy.getLinker();
	}

	@Override
	public HOEMachineIO getSuperIO() {
		return (SyndicationHubIO) ChemLab.getIOCollection().getHOEIO("SyndicationHubIO");
	}

	private static final int HASH_ODD_NUMBER=21;
	public int getIdentityHash() {
		return (HASH_ODD_NUMBER*this.worldObj.provider.dimensionId)+(HASH_ODD_NUMBER*this.xCoord)+(HASH_ODD_NUMBER*this.yCoord)+(HASH_ODD_NUMBER*this.zCoord);
	}
	
	private boolean isReadyForConfiguration=true;
	private boolean isSyndicationInspectionEngaged=false;
	private boolean isConfigured=false;
	private boolean isDisengaging=false;
	private LookupNode rootnode;
	private LookupNode activenode;
	
	public boolean isSyndicationEngaged() {
		return isSyndicationInspectionEngaged;
	}

	public boolean isReadyForConfiguration() {
		return isReadyForConfiguration;
	}

	public void engageSyndicationInspection() {
		if(isReadyForConfiguration){
			System.out.println("ENGAGED");
			detectedMachinery.clear();
			rootnode = new LookupNode(this.xCoord,this.yCoord,this.zCoord,this);
			pipesInSystem.clear();
			rootnode.setFillable(pipesInSystem);
			activenode=rootnode;
			isReadyForConfiguration=false;
			isSyndicationInspectionEngaged=true;
			steps=0;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		
		
		isReadyForConfiguration=nbt.getBoolean("isReadyForConfiguration");
		isSyndicationInspectionEngaged=nbt.getBoolean("isSyndicationInspectionEngaged");		
		isConfigured=nbt.getBoolean("isConfigured");
		steps=nbt.getInteger("steps");	
		
		isOverloaded=nbt.getBoolean("isOverloaded");
		isMalfunctioned=nbt.getBoolean("isMalfunctioned");
		
	}

	
	
	
	@Override
	public void onPostLoad() {
		super.onPostLoad();

		if(this.worldObj.isRemote){return;}
		//Selecting owned machines
		SyndicationHubData server = (SyndicationHubData) this.getServerData();
		HOEDataFingerprint[] fingers = server.getLoadInvoicesAndClear();
		if(fingers!=null){
			server.startNewInjectionSet();
			for(HOEDataFingerprint f:fingers){
				World w = DimensionManager.getWorld(f.getWid());
				int lx = f.getX();
				int lz = f.getZ();
				w.getChunkProvider().loadChunk(lx>>4, lz>>4);
				TileEntity t = w.getTileEntity(f.getX(), f.getY(), f.getZ());
				if(t==null){continue;}
				if(t instanceof MachineTileEntity){
					MachineTileEntity mta = (MachineTileEntity)t;
					mta.onPostLoad();
					HOEMachineData match = mta.getServerData();
					server.pushNewMachine(match);
				}
				if(t instanceof ISyndicationUtility){
					ISyndicationUtility ut = (ISyndicationUtility)t;
					applyUtilityEffect(ut);
				}					
			}
			server.pushInjectionSet();			
		}
//		engageSyndicationInspection();
		
		
		
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		
		nbt.setBoolean("isReadyForConfiguration", isReadyForConfiguration);
		nbt.setBoolean("isSyndicationInspectionEngaged", isSyndicationInspectionEngaged);
		nbt.setBoolean("isConfigured",isConfigured);
		nbt.setInteger("steps", steps);
		
		nbt.setBoolean("isOverloaded", isOverloaded);
		nbt.setBoolean("isMalfunctioned", isMalfunctioned);
	}

	private int steps;
	public int getSyndicationSteps(){
		return steps;
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if(isMalfunctioned){return;}
		if(this.worldObj.isRemote){return;}
		if(isSyndicationInspectionEngaged || isDisengaging){
			steps++;
			if(rootnode==null){
				failsafe();
				return;
			}
			rootnode.step(this.worldObj);
			if(rootnode.isConflicted()){
				this.setOverloaded();
			}
			if(rootnode.isFinished()){
				if(isSyndicationInspectionEngaged){
					isSyndicationInspectionEngaged=false;
					makeOnline();
					selectMachines();
					isConfigured=true;
				}else if(isDisengaging){
					isDisengaging=false;
					makeOffline();
					deselectMachines();
					isReadyForConfiguration=true;
				}
			}
			return;//Doesn't allow overloaded to be executed before those operations
		}
		
		if(isOverloaded){
			//BOOOOOOOOM!
			isMalfunctioned=true;
			this.worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			this.worldObj.createExplosion(null, xCoord, yCoord, zCoord, 4.0F, true);
		}
	}

	
	private void deselectMachines() {
		for(ISyndicationPiping isp:pipesInSystem){
			if(isp instanceof MachineTileEntity){
				MachineTileEntity mte = (MachineTileEntity)isp;
				//Pushing back
				HOEData old = mte.getLinker().getMyMachinesHandler().checkForFingerprint(mte.getServerData().getOwnerFingerprint());
				if(old==null){
					mte.getLinker().getMyMachinesHandler().injectCustomData(mte.getServerData());	
				}
			}
		}
	}
	private void selectMachines() {
		SyndicationHubData myServer = (SyndicationHubData) this.getServerData();
		myServer.startNewInjectionSet();
		for(ISyndicationPiping isp:pipesInSystem){
			if(isp instanceof ISyndicationUtility){
				ISyndicationUtility ut = (ISyndicationUtility)isp;
				applyUtilityEffect(ut);
			}			
			if(isp instanceof MachineTileEntity){
				MachineTileEntity mte = (MachineTileEntity)isp;
				HOEMachineData hmd = mte.getServerData();
				myServer.pushNewMachine(hmd);
			}
		}
		myServer.pushInjectionSet();
		myServer.disconnectAllAsBeingSyndicated();
		myServer.recalculateUtilities();
	}

	private void applyUtilityEffect(ISyndicationUtility utility) {
		SyndicationHubData data = (SyndicationHubData) this.getServerData();
		data.applyUtilityEffect(utility);
	}

	public boolean isConfigured() {
		return isConfigured;
	}

	private void failsafe() {
		isReadyForConfiguration=true;
		isSyndicationInspectionEngaged=false;
	}

	private void makeOnline(){
		getServerData().setSyndicated(true);
		for(ISyndicationPiping isp:pipesInSystem){
			isp.setController(this);
			isp.setMode(PipingMode.ACTIVE).setBlockMetadata();
		}
	}

	private void makeOffline(){
		getServerData().setSyndicated(false);
		for(ISyndicationPiping isp:pipesInSystem){
			isp.setController(null);
			isp.setMode(PipingMode.DISABLED).setBlockMetadata();
			if(isp instanceof MachineTileEntity){
				((MachineTileEntity)isp).enforceBlockUpdate();
			}
		}
	}
	
	@Override
	public boolean canUpdate() {
		return true;//Disable optimizer
	}

	
	@Override
	public boolean askControllerToDie(EntityPlayer player){
		return SyndicationPipingTileEntity.utility_askControllerToDie(player, this);
	}

	private boolean isMalfunctioned;
	private boolean isOverloaded;
	public void setOverloaded() {
		isOverloaded=true;
	}

	public void disengageSyndication() {
		if(isConfigured){
			
			SyndicationHubData myServer = (SyndicationHubData) this.getServerData();
			myServer.freeMachinesFromContext();
			
			System.out.println("DISENGAGED");
			detectedMachinery.clear();
			rootnode = new InverseLookupNode(this.xCoord,this.yCoord,this.zCoord,this);
			pipesInSystem.clear();
			rootnode.setFillable(pipesInSystem);
			activenode=rootnode;
			isDisengaging=true;
			steps=0;
			this.worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}	
	
	
}
