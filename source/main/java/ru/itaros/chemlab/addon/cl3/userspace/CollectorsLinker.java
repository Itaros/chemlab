package ru.itaros.chemlab.addon.cl3.userspace;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEIO;
import ru.itaros.chemlab.items.ChemLabItem;
import ru.itaros.hoe.io.HOEMachineCrafterIO;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalStackFactory;
import ru.itaros.hoe.recipes.FixedConversionRecipe;
import ru.itaros.hoe.recipes.RecipesCollection;
import cpw.mods.fml.common.registry.GameRegistry;

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
			for(UserspaceRigidProcess urp : cc.rigidProcesses){
				System.out.print(urp.IOPackage);
				HOEIO hoeio = iior.get(urp.IOPackage);
				HOEMachineCrafterIO hmcio = (HOEMachineCrafterIO)hoeio;
				RecipesCollection col = hmcio.getRecipesCollection();
				System.out.print("[REFFOUND]");
				
				IUniversalStack[] iinn = new IUniversalStack[urp.in.length];
				IUniversalStack[] ioutt = new IUniversalStack[urp.out.length];
				System.out.print("[MEMALLOCATED]");
				
				for(int i = 0 ; i < urp.in.length; i++){
					UserspaceLink l = urp.in[i];
					String[] search = filterSearchString(l.nodeName);
					Item item = GameRegistry.findItem(search[0], search[1]);
					if(item==null){throw new UserspaceLinkageException("Unable to find: "+search[0]+":"+search[1]);}
					ItemStack stack = new ItemStack(item,l.count,l.meta);
					iinn[i] = UniversalStackFactory.wrap(stack);
				}
				System.out.print("[IN:"+urp.in.length+"]");
				for(int i = 0 ; i < urp.out.length; i++){
					UserspaceLink l = urp.out[i];
					String[] search = filterSearchString(l.nodeName);
					Item item = GameRegistry.findItem(search[0], search[1]);
					if(item==null){throw new UserspaceLinkageException("Unable to find: "+search[0]+":"+search[1]);}
					ItemStack stack = new ItemStack(item,l.count,l.meta);
					ioutt[i] = UniversalStackFactory.wrap(stack);
				}
				System.out.print("[OUT:"+urp.in.length+"]");				
				
				FixedConversionRecipe fcr = new FixedConversionRecipe(urp.time,urp.power,iinn,ioutt);
				
				col.injectAfter(fcr);
				col.register();
				System.out.println("[ACCEPTED]");
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
			
			for(UserspaceGenericItemContract ugic : cc.genericItems){
				System.out.print(cc.groupName+"."+ugic.nodeName);
				ChemLabItem item = new ChemLabItem(cc.groupName,ugic.nodeName);
				item.setIcon(invoker);
				GameRegistry.registerItem(item, item.getInternalName());
				System.out.println("[ACCEPTED]");
			}
			
		}
		
		System.out.println("...Done!");
	}
	
	
	
}
