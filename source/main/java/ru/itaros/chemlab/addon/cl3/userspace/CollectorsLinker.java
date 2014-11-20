package ru.itaros.chemlab.addon.cl3.userspace;

import ru.itaros.chemlab.items.ChemLabItem;
import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;

public class CollectorsLinker {
	
	private CL3AddonLoader invoker;
	
	private ContractCollector[] collectors;
	
	public CollectorsLinker(CL3AddonLoader invoker){
		this.invoker=invoker;
		this.collectors=invoker.getCollectors();
	}

	public void deploy(){
		executeRegistrationQueries();
		
		
	}

	private void executeRegistrationQueries() {
		System.out.println("Inspecting registration queries:");
		
		for(ContractCollector cc : collectors){
			
			for(UserspaceGenericItemContract ugic : cc.genericItems){
				System.out.print(cc.groupName+"."+ugic.nodeName);
				ChemLabItem item = new ChemLabItem(cc.groupName,ugic.nodeName);
				item.setIcon(invoker);
				GameRegistry.registerItem(item, item.getUnlocalizedName());
				System.out.println("[ACCEPTED]");
			}
			
		}
		
		System.out.println("...Done!");
	}
	
	
	
}
