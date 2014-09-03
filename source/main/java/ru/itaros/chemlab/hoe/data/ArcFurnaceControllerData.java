package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.MixtureStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;

public class ArcFurnaceControllerData extends HOEMachineData {

	/*
	 * Reflection autocaster
	 */
	public ArcFurnaceControllerData(HOEData parent){
		super(parent);
	}	
	
	public ArcFurnaceControllerData(){
		super();
	}

	private float heatResistance=0;
	private float volumeResistance=0;
	
	public void setHeatResistance(float f) {
		heatResistance=f;
	}
	public void setVolumeResistance(float f) {
		volumeResistance=f;
	}

	public float getHeatResistance() {
		return heatResistance;
	}
	public float getVoluResistance() {
		return volumeResistance;
	}	
	
	@Override
	protected void readInventoryNBT(NBTTagCompound nbt) {
		heatResistance=nbt.getFloat("heatResistance");
		volumeResistance=nbt.getFloat("volumeResistance");
		vat.readNBT(nbt, "mixvat");
	}

	@Override
	protected void writeInventoryNBT(NBTTagCompound nbt) {
		nbt.setFloat("heatResistance",heatResistance);
		nbt.setFloat("volumeResistance",volumeResistance);
		vat.writeNBT(nbt, "mixvat");		
	}

	@Override
	public void sync() {
		
		ArcFurnaceControllerData ch = (ArcFurnaceControllerData) this.child;
		ch.heatResistance=heatResistance;
		ch.volumeResistance=volumeResistance;
		
		ch.vat=vat;//HACK: SYNC, not assign! DANGER!
		
		super.sync();
	}	
	
	//Arc Furnace
	private MixtureStack vat = new MixtureStack();
	
	private volatile IUniversalStack injectionCache;
	public float getVolumeCapacity(){
		return 18F;
	}
	public float getFreeVolume(){
		return getVolumeCapacity()-vat.getTotalVolume();
	}
	public ItemStack queryAddition(ItemStack candidate){
		if(candidate==null){return candidate;}
		if(injectionCache!=null){return candidate;}//Already transferring
		float realV = UniversalItemStack.getVolume(candidate.stackSize, candidate.getItem());
		float deltaV = getFreeVolume()-realV;
		if(deltaV>=0F){
			//Fits
			injectionCache = new UniversalItemStack(candidate.copy());
			return null;
		}else{
			//Doesn't fit
			UniversalItemStack local = new UniversalItemStack(candidate.copy());
			int excess = local.substractVolume(deltaV);
			injectionCache=local;
			candidate.stackSize-=excess;
			return candidate;
		}
	}

	public void pushCache() {
		if(injectionCache!=null){
			vat.add(injectionCache);
		}
		injectionCache=null;//Make cache clean
	}

	public MixtureStack getMixtureVat() {
		return vat;
	}
	
}
