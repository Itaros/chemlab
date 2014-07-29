package ru.itaros.chemlab.hoe.data.syndication;

import java.util.ArrayList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import ru.itaros.api.chemlab.syndication.utilities.ISyndicationCapacitor;
import ru.itaros.api.chemlab.syndication.utilities.ISyndicationUtility;
import ru.itaros.api.emf.EMFBattery;
import ru.itaros.api.emf.EMFDynamicBattery;
import ru.itaros.api.emf.IEMFCollector;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.ChemLab;
import ru.itaros.chemlab.tiles.syndication.SyndicationItemPortTileEntity;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.HOEDataFingerprint;
import ru.itaros.hoe.data.utils.InventoryManager;

public class SyndicationHubData extends HOEMachineData implements IEMFCollector {
	/*
	 * Reflection autocaster
	 */
	public SyndicationHubData(HOEData parent){
		super(parent);
	}	
	
	public SyndicationHubData(){
		super();
	}

	HOEMachineData[] machines = new HOEMachineData[0];
	
	
	public HOEMachineData[] getListOfMachines() {
		return machines;
	}	
	

	
	
	@Override
	public boolean isPerformingBlockUpdates() {
		return true;//Always :)
	}

	ArrayList<HOEMachineData> newset;
	public void startNewInjectionSet(){
		manager = new InventoryManager();
		if(newset==null){
			newset = new ArrayList<HOEMachineData>();
		}else{
			newset.clear();
		}
	}
	public void pushNewMachine(HOEMachineData data){
		validateInjectionSet();
		newset.add(data);
	}
	public void pushInjectionSet(){
		validateInjectionSet();
		HOEMachineData[] darrr = new HOEMachineData[newset.size()];
		darrr = newset.toArray(darrr);
		this.machines = darrr;
	}	
	private void validateInjectionSet(){
		if(newset==null){throw new IllegalStateException("You need to start new set before doing so!");}
	}
	
	public void reconnectAllAsBeingUnSyndicated(){
		Class<? extends HOEMachineData>[] blacklist = ChemLab.getConfig().hoesyndic_blacklist;
		for(HOEMachineData hmd:machines){
			boolean allowed=true;
			for(Class<? extends HOEMachineData> b : blacklist){
				if(b.isInstance(hmd)){
					allowed=false;
					break;
				}
			}
			if(allowed){hmd.setSyndicated(false);}
		}		
	}
	public void disconnectAllAsBeingSyndicated(){
		Class<? extends HOEMachineData>[] blacklist = ChemLab.getConfig().hoesyndic_blacklist;
		for(HOEMachineData hmd:machines){
			boolean allowed=true;
			for(Class<? extends HOEMachineData> b : blacklist){
				if(b.isInstance(hmd)){
					allowed=false;
					break;
				}
			}
			if(allowed){hmd.invalidate();hmd.setSyndicated(true);}
		}
	}
	
	
	private HOEDataFingerprint[] loadInvoices;
	public HOEDataFingerprint[] getLoadInvoicesAndClear(){
		HOEDataFingerprint[] loadInvoices_temp = loadInvoices;
		loadInvoices = null;
		return loadInvoices_temp;
	}
	
	@Override
	public void readNBT(NBTTagCompound nbt) {
		super.readNBT(nbt);
		
		battery.readNBT(nbt);
		
		capacitors_amount=nbt.getInteger("list_capacitors_amount");
		
		NBTTagList machines_list = nbt.getTagList("fingerprints", Constants.NBT.TAG_COMPOUND);
		//HOEMachineData[] machines_new = new HOEMachineData[machines_list.tagCount()];
		HOEDataFingerprint[] loadInvoices_temp = new HOEDataFingerprint[machines_list.tagCount()];
		for (int i = 0; i < machines_list.tagCount(); ++i) {
			NBTTagCompound item = (NBTTagCompound) machines_list.getCompoundTagAt(i);
			HOEDataFingerprint target_finger = HOEDataFingerprint.generateFromNBT(item);
			loadInvoices_temp[i]=target_finger;
		}
		loadInvoices=loadInvoices_temp;
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		super.writeNBT(nbt);
		
		battery.writeNBT(nbt);
		
		nbt.setInteger("list_capacitors_amount", capacitors_amount);
		
		NBTTagList machines_fingers = new NBTTagList();
		for(HOEMachineData hda : machines){
			if(hda==null){continue;}
			machines_fingers.appendTag(hda.getOwnerFingerprint().getNBT());
		}
		nbt.setTag("fingerprints", machines_fingers);
	}
	
	

	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		super.readInventoryNBT(nbt);
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		super.writeInventoryNBT(nbt);
	}

	//EMF
	EMFDynamicBattery battery = new EMFDynamicBattery();
	@Override
	public int getStoredAmount() {
		return battery.getStoredAmount();
	}
	@Override
	public int getMaxAmount() {
		return battery.getMaxAmount();
	}
	@Override
	public int getFreeSpace() {
		return battery.getFreeSpace();
	}
	@Override
	public void injectPower(int amount) {
		battery.injectPower(amount);
	}
	@Override
	public int ejectPower(int amount) {
		return battery.ejectPower(amount);
	}
	
	
	//Lists
	ArrayList<ISyndicationCapacitor> capacitors = new ArrayList<ISyndicationCapacitor>();
	int capacitors_amount;

	public void applyUtilityEffect(ISyndicationUtility utility) {
		if(utility instanceof ISyndicationCapacitor){
			capacitors.add((ISyndicationCapacitor) utility);
			return;
		}
		if(utility instanceof SyndicationItemPortTileEntity){
			manager.addItemport((SyndicationItemPortTileEntity)utility);
		}
		
	}

	public void recalculateUtilities() {
		int powermax=0;
		capacitors_amount=capacitors.size();
		for(ISyndicationCapacitor cap:capacitors){
			powermax+=cap.storageSize();
		}
		battery.setMax(powermax);
		
		
	}

	public int getCapacitorsListAmount() {
		return capacitors_amount;
	}

	public EMFBattery getBattery() {
		return battery;
	}
	
	
	//Inventory
	public InventoryManager getInventoryManager(){
		return manager;
	}
	InventoryManager manager;


	public void freeMachinesFromContext() {
		for(HOEMachineData data : machines){
			data.revalidate();
		}
		this.reconnectAllAsBeingUnSyndicated();
		machines=new HOEMachineData[0];
	}
	
	
}
