package ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes;

import cpw.mods.fml.common.registry.LanguageRegistry;
import ru.itaros.api.hoe.registries.IHOERecipeRegistry;
import ru.itaros.toolkit.hoe.machines.basic.HOEMachineData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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

	public abstract boolean checkStorage(HOEMachineData hoeMachineData);

	public abstract boolean checkResources(HOEMachineData hoeMachineData);

	public abstract void consumeResources(HOEMachineData hoeMachineData);

	public abstract void incrementProduction(HOEMachineData hoeMachineData);
	
	
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
	
}
