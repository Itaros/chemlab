package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.ArrayList;

import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.items.ChemLabItem;

public class AutoitemsLoader {

	ArrayList<ChemLabItem> items = new ArrayList<ChemLabItem>();
	
	void parse(String[] data) {
		for(int x = 0 ; x < data.length ; x ++){
			String cur = data[x];
			if(cur.equalsIgnoreCase("ADD_ITEMS:")){
				x++;
				String newname = data[x];
				ChemLabItem item = new ChemLabItem(newname);
				items.add(item);
			}
		}
		
	}

	public void registerItems(){
		for(ChemLabItem i:items){
			GameRegistry.registerItem(i, i.getUnlocalizedName());
		}
		
	}
	
}
