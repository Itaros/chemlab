package ru.itaros.chemlab.addon.cl3.userspace;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.chemlab.fluids.hoe.UserspaceHOEFluid;
import ru.itaros.chemlab.items.ChemLabItem;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluid.HOEFluidState;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackFactory;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

public class CollectorsLinker {
	
	private CL3AddonLoader invoker;
	
	private ContractCollector[] collectors;
	
	public CollectorsLinker(CL3AddonLoader invoker){
		this.invoker=invoker;
		this.collectors=invoker.getCollectors();
	}

	public void deployPre(){
		executeRegistrationQueries();
	}
	public void deployPost(){
		executeRecipeRegistrationQueries();
	}

	private void executeRecipeRegistrationQueries() {
		System.out.println("Inspecting recipe queries:");
		ru.itaros.api.hoe.registries.IIORegistry iior = HOEIO.getIORegistry();
		System.out.println("(IORegistry is acquared. Proceeding with injections)");
		for(ContractCollector cc : collectors){
			if(cc.rigidProcesses!=null){
				for(UserspaceRigidProcess urp : cc.rigidProcesses){
					System.out.print(urp.IOPackage);
					HOEIO hoeio = iior.get(urp.IOPackage);
					HOEMachineCrafterIO hmcio = (HOEMachineCrafterIO)hoeio;
					RecipesCollection col = hmcio.getRecipesCollection();
					HOEFluidRegistry hoeflreg = HOEFluidRegistry.getInstance();
					System.out.print("[REFFOUND]");
					
					IUniversalStack[] iinn = new IUniversalStack[urp.in.length];
					IUniversalStack[] ioutt = new IUniversalStack[urp.out.length];
					System.out.print("[MEMALLOCATED]");
					
					for(int i = 0 ; i < urp.in.length; i++){
						UserspaceLink l = urp.in[i];
						String[] search = filterSearchString(l.nodeName);
						Item item = GameRegistry.findItem(search[0], search[1]);
						if(item!=null){
							//If item
							ItemStack stack = new ItemStack(item,l.count,l.meta);
							iinn[i] = UniversalStackFactory.wrap(stack);
						}else{
							//Trying with fluid
							HOEFluid hoefl = hoeflreg.pop(search[1]);
							if(hoefl!=null){
								HOEFluidStack stack = new HOEFluidStack(hoefl,l.count);
								iinn[i] = UniversalStackFactory.wrap(stack);
							}else{
								//Shit...
								throw new UserspaceLinkageException("Can't find: "+search[0]+":"+search[1]);
							}
						}
					}
					System.out.print("[IN:"+urp.in.length+"]");
					for(int i = 0 ; i < urp.out.length; i++){
						UserspaceLink l = urp.out[i];
						String[] search = filterSearchString(l.nodeName);
						Item item = GameRegistry.findItem(search[0], search[1]);
						if(item!=null){
							//If item
							ItemStack stack = new ItemStack(item,l.count,l.meta);
							ioutt[i] = UniversalStackFactory.wrap(stack);
						}else{
							//Trying with fluid
							HOEFluid hoefl = hoeflreg.pop(search[1]);
							if(hoefl!=null){
								HOEFluidStack stack = new HOEFluidStack(hoefl,l.count);
								ioutt[i] = UniversalStackFactory.wrap(stack);
							}else{
								//Shit...
								throw new UserspaceLinkageException("Can't find: "+search[0]+":"+search[1]);
							}
						}
					}
					System.out.print("[OUT:"+urp.out.length+"]");				
					
					FixedConversionRecipe fcr = new FixedConversionRecipe(urp.time,urp.power,iinn,ioutt);
					
					col.injectAfter(fcr);
					col.register();
					System.out.println("[ACCEPTED]");
				}
			}
		}
		System.out.println("...Done!");
	}

	protected static String[] filterSearchString(String nodeName) {
		if(nodeName.contains(":")){
			return nodeName.split(":");
		}else{
			return new String[]{"chemlab",nodeName};
		}
	}

	private void executeRegistrationQueries() {
		System.out.println("Inspecting registration queries:");
		
		for(ContractCollector cc : collectors){
			System.out.println("(Items)");
			if(cc.genericItems!=null){
				for(UserspaceGenericItemContract ugic : cc.genericItems){
					System.out.print(cc.groupName+"."+ugic.nodeName);
					ChemLabItem item = new ChemLabItem(cc.groupName,ugic.nodeName);
					if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
						item.setIcon(invoker);
					}
					GameRegistry.registerItem(item, item.getInternalName());
					System.out.println("[ACCEPTED]");
				}
			}
			System.out.println("(HOEFluids)");
			HOEFluidRegistry fluidreg = HOEFluidRegistry.getInstance();
			if(cc.genericFluids!=null){
				for(UserspaceGenericHOEFluid ugfc : cc.genericFluids){
					System.out.print(cc.groupName+"."+ugfc.nodeName);
					
					UserspaceHOEFluid uhf = new UserspaceHOEFluid(cc.groupName,ugfc.nodeName, 0, HOEFluidState.LIQUID);
					fluidreg.register(uhf);
					
					System.out.println("[ACCEPTED]");
				}
			}
			
		}
		
		System.out.println("...Done!");
	}
	
	
	
}
