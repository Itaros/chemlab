package ru.itaros.chemlab.hoe.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.MixtureStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.physics.IMatterState;
import ru.itaros.hoe.physics.MixtureReactionFramework;
import ru.itaros.hoe.physics.StateOnlyTransmutationGraphEvaluator;
import ru.itaros.hoe.utils.StackUtility;

public class ArcFurnaceControllerData extends HOEMachineData  implements ISynchroportItems {

	/*
	 * Reflection autocaster
	 */
	public ArcFurnaceControllerData(HOEData parent){
		super(parent);
		configure();
	}	
	
	public ArcFurnaceControllerData(){
		super();
		configure();
	}
	private void configure(){
		framework.setEvaluator(new StateOnlyTransmutationGraphEvaluator());
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
	
	MixtureReactionFramework framework = new MixtureReactionFramework(vat);
	
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
			return StackUtility.verify(candidate);
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

	
	private volatile boolean isRedstonePowered=false;
	public void setPowered(boolean isPowered) {
		isRedstonePowered=isPowered;
	}
	public boolean getPowered(){
		return isRedstonePowered;
	}

	public static final float VOLTAGE = 900;//V
	public static final float VA_PER_TICK = (VOLTAGE*VOLTAGE)/20F*0.0001F*20F;//VoltAmperes/tick
	//0.0001F is nOhm to Mj scaleframe factor
	//20F is RL Time to MC Time frame factor
	
	public void putCurrent() {
		if(isRedstonePowered){
			IUniversalStack contactor = vat.getUpperLayer();
			if(contactor==null){return;}//Nowhere to apply
			if(contactor.getItem() instanceof IMatterState){
				IMatterState matter = (IMatterState) contactor.getItem();
				float resistantEnergy = VA_PER_TICK/matter.resistance();
				vat.giveEnergy(resistantEnergy);
			}
			
		}
	}

	public MixtureReactionFramework getReactionFramework() {
		return framework;
	}

	//ISynchroportItems
	@Override
	public ItemStack tryToPutItemsIn(ItemStack source) {
		return tryToPutItemsIn(source, null);
	}

	@Override
	public ItemStack tryToPutItemsIn(ItemStack source, ItemStack filter) {
		if(source!=null){
			if(source.getItem() instanceof IMatterState){
				return queryAddition(source);
			}
		}
		return source;
	}

	@Override
	public ItemStack tryToGetItemsOut(ItemStack target) {
		return tryToGetItemsOut(target, null);
	}

	@Override
	public ItemStack tryToGetItemsOut(ItemStack target, ItemStack filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack tryToPutFluidsIn(FluidStack fluid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FluidStack tryToPutFluidsIn(FluidStack fluid, FluidStack filter) {
		// TODO Auto-generated method stub
		return null;
	}

	protected boolean isDirty=false;
	@Override
	public void markDirty() {
		isDirty=true;
	}
	@Override
	public boolean pollDirty() {
		boolean cache = isDirty;
		isDirty=false;
		return cache;
	}
	
}
