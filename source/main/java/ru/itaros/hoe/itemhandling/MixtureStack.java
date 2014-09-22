package ru.itaros.hoe.itemhandling;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.hoe.physics.IEnergyReceiver;
import ru.itaros.hoe.physics.IMatterState;
import ru.itaros.hoe.utils.StackUtility;

/*
 * Class to describe volume containment of a device(machine or container).
 * If reaction framework is available it is possible to evaluate or plan(like,aE runaway)
 * events of mixing.
 * THERE IS NO THREAD SAFETY. CONTROL YOUR CONTEXTS!
 */
public class MixtureStack implements IEnergyReceiver{

	private volatile boolean hasAdditionalComponents=true;
	
	public boolean queryAdditionalComponents(){
		if(hasAdditionalComponents){
			hasAdditionalComponents=false;
			return true;
		}else{
			return false;
		}
	}
	
	//TODO: Might be beneficial to have the ability to create mapped mixtures to boost performance on automated advanced machines
	private ArrayList<IUniversalStack> mixture = new ArrayList<IUniversalStack>();
	
	private float volume=0F;
	
	private float heatCapacity=0F;
	
	/*
	 * Returns average heat capacity of a mixture in MJoules/K.
	 * Note: if state is transfered it doesn't invoke heat capacity recalc,
	 * until full transformation is encountered for performance reasons.
	 */
	public float getHeatCapacity(){
		return heatCapacity;
	}
	
	/*
	 * This method provides an iterator to enumerate through mixture compounds.
	 * DO NOT WRITE INTO OBJECTS UNDER ANY CIRCUMSTANCES. Otherwise this may result in negative volume
	 */
	public Iterator<IUniversalStack> getViewIterator(){
		return mixture.iterator();
	}
	
	public float getVolumeOf(IUniversalStack template){
		//Does not allocate iterator to preserve GC operation time
		for(int i = 0 ; i < mixture.size() ; i++){
			IUniversalStack comparable = mixture.get(i);		
			if(StackUtility.isItemEqual(comparable, template, true)){
				return comparable.getVolume();
			}
		}
		return 0F;//Nothing has zero volume
	}
	
	public float getTotalVolume(){
		return volume;
	}
	
	public boolean addPrecise(IUniversalStack in, int mixcid){
		IUniversalStack comparable = mixture.get(mixcid);
		if(StackUtility.isItemEqual(comparable, in, true)){
			comparable.increment(in.getStackSize());
			volume+=in.getVolume();
			addHeatCapacity(in);
			return true;
		}
		return false;
	}
	public MixtureStack add(IUniversalStack in){
		if(in==null){return this;}//Wut?
		//Comparing against existant ones.
		//Does not allocate iterator to preserve GC operation time
		for(int i = 0 ; i < mixture.size() ; i++){
			//IUniversalStack comparable = mixture.get(i);
			//if(StackUtility.isItemEqual(comparable, in, true)){
			if(addPrecise(in, i)){return this;}
			//}
		}
		//No match. Injecting
		mixture.add(in);
		volume+=in.getVolume();
		addHeatCapacity(in);
		hasAdditionalComponents=true;
		return this;
	}
	
	
	private void addHeatCapacity(IUniversalStack in) {
		if(in.getItem() instanceof IMatterState){
			IMatterState ims = (IMatterState)in.getItem();
			heatCapacity+=ims.heatCapacity()*in.getVolume();
		}
	}

	public void writeNBT(NBTTagCompound nbt, String tag){
		NBTTagCompound mix = new NBTTagCompound();
		
		NBTTagList list = new NBTTagList();
		for(IUniversalStack s:mixture){
			if(s!=null){
				NBTTagCompound local = new NBTTagCompound();
				s.writeToNBT(local);
				list.appendTag(local);
			}
		}
		mix.setTag("mix", list);
		mix.setFloat("volume", volume);
		mix.setFloat("heatCapacity", heatCapacity);
		mix.setFloat("temperature", temperature);
		mix.setFloat("floatingEnergy", floatingEnergy);
		nbt.setTag(tag, mix);
	}
	public void readNBT(NBTTagCompound nbt, String tag){
		NBTTagCompound mix = nbt.getCompoundTag(tag);
		if(mix!=null){
			NBTTagList list = mix.getTagList("mix", Constants.NBT.TAG_COMPOUND);
			if(list!=null){
				
				mixture = new ArrayList<IUniversalStack>(list.tagCount());//Optimization
				for(int i = 0 ; i<list.tagCount();i++){
					NBTTagCompound local = list.getCompoundTagAt(i);
					IUniversalStack cmp = UniversalStackUtils.loadItemStackFromNBT(local);
					mixture.add(cmp);
				}
				
			}
			
			volume = mix.getFloat("volume");
			heatCapacity = mix.getFloat("heatCapacity");
			temperature = mix.getFloat("temperature");
			floatingEnergy = mix.getFloat("floatingEnergy");
		}
	}
	public static MixtureStack constructFromNBT(NBTTagCompound nbt, String tag){
		MixtureStack me = new MixtureStack();
		me.readNBT(nbt, tag);
		return me;
	}

	
	private float temperature;
	//Energy which is not accounted as part of temperature shift
	private float floatingEnergy;
	
	@Override
	public float getCurrentTemperature() {
		return temperature;
	}

	@Override
	public void setCurrentTemperature(float temp) {
		temperature=temp;
	}

	@Override
	public void giveEnergy(float mj) {
		floatingEnergy+=mj;
		if(floatingEnergy>heatCapacity){
			float rem = floatingEnergy%heatCapacity;
			temperature+=(float)((int)floatingEnergy/((int)heatCapacity+1));
			floatingEnergy=rem;
		}
	}

	@Override
	public float receiveEnergy(float mj) {
		if(floatingEnergy>=mj){floatingEnergy-=mj;return mj;}
		if(temperature>0){
			float mjacc = temperature*heatCapacity;
			if(mjacc>=mj){
				mjacc-=mj;
				temperature = mjacc/heatCapacity;
				return mj;
			}else{
				float reminder = mj-mjacc;
				temperature=0;
				return reminder;
			}
		}
		temperature=0;
		return 0;
	}

	public IUniversalStack getUpperLayer() {
		int last = mixture.size()-1;
		if(last<0){return null;}
		while(mixture.get(last)==null){
			last--;
			if(last<0){return null;}
		}
		return mixture.get(last);
	}

	public void remove(IUniversalStack s) {
		mixture.remove(s);
		//I don't think it needs reevaluation, because IReaction will check anyway
	}
	
	
}
