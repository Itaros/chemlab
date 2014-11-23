package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlRootElement;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.UniversalStackFactory;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@XmlRootElement
public class UserspaceLink extends UserspaceContract {

	public int meta=0;
	
	public int count=1;
	
	
	public Object getTarget(HOEFluidRegistry hoeflreg){
		
		String[] search = CollectorsLinker.filterSearchString(nodeName);
		Item item = GameRegistry.findItem(search[0], search[1]);
		if(item==null){
			Block b = GameRegistry.findBlock(search[0], search[1]);
			if(b!=null){
				ItemStack stack = new ItemStack(b,count,meta);
				return stack;
			}
		}
		if(item!=null){
			//If item
			ItemStack stack = new ItemStack(item,count,meta);
			return stack;
		}else if(hoeflreg==null){
			throw new UserspaceLinkageException("Can't find: "+search[0]+":"+search[1]+"(ITEM)");
		}else{
			//Trying with fluid
			HOEFluid hoefl = hoeflreg.pop(search[1]);
			if(hoefl!=null){
				HOEFluidStack stack = new HOEFluidStack(hoefl,count);
				return stack;
			}else{
				//Shit...
				throw new UserspaceLinkageException("Can't find: "+search[0]+":"+search[1]);
			}
		}
		
	}
	
}
