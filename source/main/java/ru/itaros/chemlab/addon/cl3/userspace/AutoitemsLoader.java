package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.items.ChemLabItem;
import ru.itaros.hoe.fluid.HOEFluid;

public class AutoitemsLoader {

	ArrayList<ChemLabItem> items = new ArrayList<ChemLabItem>();
	ArrayList<HOEFluid> fluids = new ArrayList<HOEFluid>();
	
	void parse(String[] data) {
		for(int x = 0 ; x < data.length ; x ++){
			String cur = data[x];
			if(cur.equalsIgnoreCase("ADD_ITEMS:")){
				x++;
				String newname = data[x];
				ChemLabItem item = new ChemLabItem(newname);
				items.add(item);
			}
			if(cur.equalsIgnoreCase("ADD_HOEFLUIDS:")){
				x++;
				String newname = data[x];
				//TODO: Finish this ^
			}			
		}
		
	}

	public void registerItems(){
		for(ChemLabItem i:items){
			GameRegistry.registerItem(i, i.getUnlocalizedName());
		}
		
	}
	
}
