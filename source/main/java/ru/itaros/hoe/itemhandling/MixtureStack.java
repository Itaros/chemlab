package ru.itaros.hoe.itemhandling;

import java.util.ArrayList;
import java.util.Iterator;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import ru.itaros.chemlab.addon.bc.builder.HOENBTManifold;
import ru.itaros.hoe.utils.StackUtility;

/*
 * Class to describe volume containment of a device(machine or container).
 * If reaction framework is available it is possible to evaluate or plan(like,aE runaway)
 * events of mixing.
 * THERE IS NO THREAD SAFETY. CONTROL YOUR CONTEXTS!
 */
public class MixtureStack {

	//TODO: Might be beneficial to have the ability to create mapped mixtures to boost performance on automated advanced machines
	private ArrayList<IUniversalStack> mixture = new ArrayList<IUniversalStack>();
	
	private float volume;
	
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
		return this;
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
		}
	}
	public static MixtureStack constructFromNBT(NBTTagCompound nbt, String tag){
		MixtureStack me = new MixtureStack();
		me.readNBT(nbt, tag);
		return me;
	}
	
}
