package ru.itaros.hoe.registries;

import java.util.Hashtable;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import ru.itaros.hoe.blocks.ForgeSidedFluidBlock;
import ru.itaros.hoe.fluid.FluidToHOE;
import ru.itaros.hoe.fluid.ForgeFluidNameFilter;
import ru.itaros.hoe.fluid.HOEFluid;

public class HOEFluidRegistry {

	ForgeFluidNameFilter namefilter = new ForgeFluidNameFilter();
	
	private static HOEFluidRegistry instance;
	public static HOEFluidRegistry getInstance(){
		return instance;
	}
	public HOEFluidRegistry(){
		instance = this;
	}
	
	public void register(HOEFluid fluid){
		fluids.put(fluid.getUnlocalizedName(), fluid);
		createForgeFluidPair(fluid);
	}
	private void createForgeFluidPair(HOEFluid fluid) {
		if(FluidToHOE.isActive()){
			//Instantianting
			//Fluid f = new Fluid(fluid.getCommonName());
			Fluid f = namefilter.process(fluid);
			//Pairing
			fluid.setForgeFluid(f);
			FluidToHOE.set(f, fluid);
			//Fitlering
			//Registering
			if(!FluidRegistry.isFluidRegistered(f)){
				FluidRegistry.registerFluid(f);
				
				//Creating block
				
				ForgeSidedFluidBlock b = new ForgeSidedFluidBlock(f);
				GameRegistry.registerBlock(b, b.getUnlocalizedName());
			}
			System.out.println("Registered HOEFluid<->ForgeFluid: "+f.getName());
		}
	}
	public HOEFluid pop(String key){
		return fluids.get(key);
	}
	public HOEFluid[] all(){
		HOEFluid[] arr = new HOEFluid[fluids.values().size()];
		return  fluids.values().toArray(arr);
	}
	
	private Hashtable<String,HOEFluid> fluids = new Hashtable<String,HOEFluid>();
	
	
	
	
	
}
