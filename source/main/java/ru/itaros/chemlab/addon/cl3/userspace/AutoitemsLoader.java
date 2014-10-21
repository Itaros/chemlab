package ru.itaros.chemlab.addon.cl3.userspace;

import java.util.ArrayList;

import net.minecraft.item.Item;
import cpw.mods.fml.common.registry.GameRegistry;
import ru.itaros.chemlab.fluids.hoe.ChemLabUserspaceIMSHOEFluid;
import ru.itaros.chemlab.items.ChemLabItem;
import ru.itaros.chemlab.items.ChemLabUserspaceIMSItem;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.registries.HOEFluidRegistry;

public class AutoitemsLoader {

	ArrayList<Item> items = new ArrayList<Item>();
	ArrayList<HOEFluid> fluids = new ArrayList<HOEFluid>();
	
	void parse(String groupname, String[] data) {
		for(int x = 0 ; x < data.length ; x ++){
			String cur = data[x];
			if(cur.equalsIgnoreCase("ADD_ITEMS:")){
				x++;
				String newname = data[x];
				ChemLabItem item = new ChemLabItem(newname);
				items.add(item);
			}
			if(cur.equalsIgnoreCase("ADD_ITEMS_IMS:")){
				x++;
				String newname = data[x];
				ChemLabUserspaceIMSItem item = new ChemLabUserspaceIMSItem(groupname,newname);
				items.add(item);
				//Upper Point
				x++;item.setUpperPoint(Integer.parseInt(data[x]));
				x++;String itemUpper = data[x];
				item.setUpperItemLinkString(itemUpper);
				//Lower point
				x++;item.setLowerPoint(Integer.parseInt(data[x]));
				x++;String itemLower = data[x];	
				item.setLowerItemLinkString(itemLower);
				//Heat capacity
				x++;item.setHeatCapacity(Float.parseFloat(data[x]));
				//Resistance
				x++;item.setResistance(Long.parseLong(data[x]));
				
			}			
			if(cur.equalsIgnoreCase("ADD_HOEFLUIDS:")){
				x++;
				String newname = data[x];
				//TODO: Finish this ^
			}	
			if(cur.equalsIgnoreCase("ADD_HOEFLUID_IMS:")){
				x++;
				String newname = data[x];
				ChemLabUserspaceIMSHOEFluid item = new ChemLabUserspaceIMSHOEFluid(groupname,newname);
				fluids.add(item);
				//Upper Point
				x++;item.setUpperPoint(Integer.parseInt(data[x]));
				x++;String itemUpper = data[x];
				item.setUpperItemLinkString(itemUpper);
				//Lower point
				x++;item.setLowerPoint(Integer.parseInt(data[x]));
				x++;String itemLower = data[x];	
				item.setLowerItemLinkString(itemLower);
				//Heat capacity
				x++;item.setHeatCapacity(Float.parseFloat(data[x]));
				//Resistance
				x++;item.setResistance(Long.parseLong(data[x]));				
				
			}	
		}
		
	}

	public void registerItems(){
		//Registry pass
		for(Item i:items){
			GameRegistry.registerItem(i, i.getUnlocalizedName());
		}
		HOEFluidRegistry registry = HOEFluid.getFluidRegistry();
		for(HOEFluid f:fluids){
			registry.register(f);
		}
		//Linkage pass
		for(Item i:items){
			if(i instanceof ChemLabUserspaceIMSItem){
				try{
					ChemLabUserspaceIMSItem clu = (ChemLabUserspaceIMSItem)i;
					clu.link();
				}catch(Exception e){
					String dmp = registry.dumpAllEntries();
					System.out.println(dmp);
					throw new UserspaceLinkageException(e);
				}
			}
		}
		for(HOEFluid f:fluids){
			if(f instanceof ChemLabUserspaceIMSHOEFluid){
				try{
					ChemLabUserspaceIMSHOEFluid clu = (ChemLabUserspaceIMSHOEFluid)f;
					clu.link();
				}catch(Exception e){
					throw new UserspaceLinkageException(e);
				}
			}
		}
		
	}
	
}
