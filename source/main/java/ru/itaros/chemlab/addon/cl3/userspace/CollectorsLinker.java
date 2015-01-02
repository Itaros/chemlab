package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.chemlab.fluids.hoe.UserspaceHOEFluid;
import ru.itaros.chemlab.items.ChemLabChemicalItem;
import ru.itaros.chemlab.items.ChemLabItem;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluid.HOEFluidState;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.framework.chemistry.ChemicalCompound;
import ru.itaros.hoe.framework.chemistry.ChemicalReaction;
import ru.itaros.hoe.framework.chemistry.registries.CompoundDatabase;
import ru.itaros.hoe.framework.chemistry.registries.ReactionDatabase;
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
		executeHOEChemistryQueries();
		executeRegistrationQueries();
	}

	public void deployPost(){
		executeHOEChemistryRectionQueries();
		executeRecipeRegistrationQueries();
		evaluateChemGraphs();
	}
	
	private ArrayList<ChemLabChemicalItem> graphEvalQueue = new ArrayList<ChemLabChemicalItem>();
	
	private void evaluateChemGraphs() {
		System.out.println("Evaluating ChemGraphs index:...");
		for(ChemLabChemicalItem clci : graphEvalQueue){
			System.out.println("Evaluating "+clci.getUnlocalizedName()+"...");
			clci.buildReactiveIndex();
		}
		System.out.println("...Done!");
	}	

	private void executeHOEChemistryRectionQueries() {
		System.out.println("Registering Reactions:...");
		for(ContractCollector cc : collectors){
			if(cc.hoeChemicalReactions!=null){
				for(UserspaceReaction ur : cc.hoeChemicalReactions){
					ChemicalReaction reaction = new ChemicalReaction(ur.equation);
					reaction.calculateReactionEnthalpy();
					ReactionDatabase.getInstance().register(reaction);
				}
			}
		}
		System.out.println("...Done!");
	}

	private void executeHOEChemistryQueries() {
		System.out.println("Registering Compounds:...");
		
		for(ContractCollector cc : collectors){
			if(cc.hoeChemicalCompounds!=null){
				for(UserspaceCompound uc : cc.hoeChemicalCompounds){
					ChemicalCompound compound = new ChemicalCompound(uc.stoichiometric);
					compound.setConventionalName(uc.classicalName);
					if(uc.functionalName!=null){
						compound.setStoichiometryString(uc.functionalName);
					}
					compound.setFormationEnthalpy(uc.formationEnthalpy);
					CompoundDatabase.getInstance().addCompound(compound);
					System.out.println(compound.toString()+" is registered!");
				}
			}
		}
		
		System.out.println("...Done!");
	}	

	private void executeRecipeRegistrationQueries() {
		System.out.println("Inspecting recipe queries:");
		ru.itaros.api.hoe.registries.IIORegistry iior = HOEIO.getIORegistry();
		System.out.println("(IORegistry is acquared. Proceeding with injections)");
		for(ContractCollector cc : collectors){
			if(cc.gridCraftings!=null){
				for(UserspaceGridCrafting ugc : cc.gridCraftings){
					System.out.println("Attempting to register GCR for "+ugc.result.nodeName);
					ugc.registerRecipe();
				}
			}
			if(cc.rigidProcesses!=null){
				for(UserspaceRigidProcess urp : cc.rigidProcesses){
					System.out.print(urp.IOPackage);
					HOEIO hoeio = iior.get(urp.IOPackage);
					HOEMachineCrafterIO hmcio = (HOEMachineCrafterIO)hoeio;
					RecipesCollection col = hmcio.getRecipesCollection();
					HOEFluidRegistry hoeflreg = HOEFluidRegistry.getInstance();
					System.out.print("[REFFOUND]");
					
					
					IUniversalStack[] iinn = new IUniversalStack[urp.in!=null?urp.in.length:0];
					IUniversalStack[] ioutt = new IUniversalStack[urp.out!=null?urp.out.length:0];
					System.out.print("[MEMALLOCATED]");
					
					for(int i = 0 ; i < iinn.length; i++){
						UserspaceLink l = urp.in[i];
						ItemStack oreCandidate = l.tryResolveOreDictFirst();
						if(oreCandidate!=null){
							iinn[i] = UniversalStackFactory.wrap(oreCandidate);
						}else{
							iinn[i] = UniversalStackFactory.wrap(l.getTarget(hoeflreg));
						}
					}
					System.out.print("[IN:"+iinn.length+"]");
					for(int i = 0 ; i < ioutt.length; i++){
						UserspaceLink l = urp.out[i];
						ItemStack oreCandidate = l.tryResolveOreDictFirst();
						if(oreCandidate!=null){
							ioutt[i] = UniversalStackFactory.wrap(oreCandidate);
						}else{
							ioutt[i] = UniversalStackFactory.wrap(l.getTarget(hoeflreg));
						}
					}
					System.out.print("[OUT:"+ioutt.length+"]");				
					
					FixedConversionRecipe fcr = new FixedConversionRecipe(urp.time,urp.power,iinn,ioutt);
					
					fcr.setUnlocalizedName(urp.nodeName);
					
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
					if(ugic.oreDict!=null && ugic.oreDict.length>0){
						for(String s : ugic.oreDict){
							OreDictionary.registerOre(s, new ItemStack(item));
						}
					}
					System.out.println("[ACCEPTED]");
				}
			}
			System.out.println("(ChemicalItems)");
			if(cc.chemicalItems!=null){
				for(UserspaceChemicalItemContract ucic : cc.chemicalItems){
					System.out.print(cc.groupName+"."+ucic.nodeName);
					ChemLabChemicalItem item = new ChemLabChemicalItem(cc.groupName,ucic.nodeName,ucic.getCompounds());
					if(FMLCommonHandler.instance().getEffectiveSide()==Side.CLIENT){
						item.setIcon(invoker);
					}
					GameRegistry.registerItem(item, item.getInternalName());
					graphEvalQueue.add(item);
					if(ucic.oreDict!=null && ucic.oreDict.length>0){
						for(String s : ucic.oreDict){
							OreDictionary.registerOre(s, new ItemStack(item));
						}
					}					
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
