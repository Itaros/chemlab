package ru.itaros.hoe.recipes;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.hoe.data.machines.HOEMachineCrafterData;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.utils.StackUtility;
import cpw.mods.fml.common.registry.LanguageRegistry;

public abstract class Recipe {
	

	private String name;
	public String getName(){
		return name;
	}
	
	public Recipe(String name) {
		this.name=name;
	}
	public abstract int getIncomingSlots();
	public abstract int getOutcomingSlots();
	
	public abstract ItemStack[] getIncomingStricttypes();
	public abstract ItemStack[] getOutcomingStricttypes();
	
	
	//TODO: HOE-style exception
	public static IHOERecipeRegistry getRecipeRegistry(){
		try{
		return (IHOERecipeRegistry) Class.forName("ru.itaros.hoe.registries.HOERecipeRegistry").getMethod("getInstance").invoke(null);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	public abstract boolean checkStorage(HOEMachineCrafterData hoeMachineData);

	public abstract boolean checkResources(HOEMachineCrafterData hoeMachineData);

	public abstract void consumeResources(HOEMachineCrafterData hoeMachineData);

	public abstract boolean tryToConsumeEnergy(HOEMachineData hoeMachineData);
	
	public abstract void incrementProduction(HOEMachineCrafterData hoeMachineData);
	
	public void performProduction(HOEMachineCrafterData data){
		consumeResources(data);
		incrementProduction(data);
	}
	
	
	private String unlocalizedName="";
	public Recipe setUnlocalizedName(String name){
		unlocalizedName = "recipe."+name;
		return this;
	}
	public String getLocalizedName(){
		if(unlocalizedName==""){
			return "error.notset.name";
		}
		String r = LanguageRegistry.instance().getStringLocalization(unlocalizedName+".name");
		if(r==""){return unlocalizedName;}else{return r;}
	}

	public int getSlotIdFor(ItemStack type, boolean ignoreMetadata) {
		if(type==null){return -1;}
		ItemStack[] stacks = getIncomingStricttypes();
		for(int i = 0 ; i < stacks.length ; i++){
			if(stacks[i]==null){continue;}
			if(StackUtility.isItemEqual(type, stacks[i],ignoreMetadata)){
				return i;
			}
		}
		return -1;
	}

	//Recipe indication

	public void makeFinal() {
		normalizedIn = new ItemStack[getIncomingSlots()];//getIncomingStricttypes().clone();
		normalizedOut = new ItemStack[getOutcomingSlots()];//getOutcomingStricttypes().clone();
		for(int i = 0 ; i < normalizedIn.length; i++){
			normalizedIn[i]=getIncomingStricttypes()[i].copy();
			normalizedIn[i].stackSize=0;
		}
		for(int i = 0 ; i < normalizedOut.length; i++){
			normalizedOut[i]=getOutcomingStricttypes()[i].copy();
			normalizedOut[i].stackSize=0;
		}		
	}	
	
	private ItemStack[] normalizedIn;
	private ItemStack[] normalizedOut;	
	
	public ItemStack[] getNormalziedIncomingStricttypes() {
		return normalizedIn;
	}

	public ItemStack[] getNormalziedOutcomingStricttypes() {
		return normalizedOut;
	}

	
	private HOEMachineCrafterIO owner;
	public void claim(HOEMachineCrafterIO owner) {
		if(this.owner!=null && this.owner==owner){
			System.out.println("("+this.owner.getClass().getSimpleName()+")HOEIO RECIPE OWNERSHIP CONFLICT: ");
		}
		this.owner=owner;
		System.out.println(owner.getClass().getSimpleName()+" took ownership over "+this.name);
	}
	public HOEMachineCrafterIO getOwnerIO(){
		return owner;
	}
	

	
}
