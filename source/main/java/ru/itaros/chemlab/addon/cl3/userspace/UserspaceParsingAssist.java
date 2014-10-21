package ru.itaros.chemlab.addon.cl3.userspace;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluidStack;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.UniversalFluidStack;
import ru.itaros.hoe.itemhandling.UniversalItemStack;
import ru.itaros.hoe.registries.HOEFluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class UserspaceParsingAssist {
	
	public static final String DEFCONST_ITEM="[ITEM]";
	public static final String DEFCONST_FLUID="[FLUID]";
	public static final String DEFCONST_NULL="[NULL]";
	
	public static IUniversalStack parseLinkString(String linkString) {
		if(linkString.startsWith(DEFCONST_ITEM)){
			linkString=linkString.replace(DEFCONST_ITEM, "");
			String[] d = linkString.split(" # ");
			String sourcename = d[0];
			String hostMod = sourcename.split(":")[0];
			sourcename = sourcename.replace(hostMod+":", "");
			int meta = 0;
			if(sourcename.contains("[")){
				//Contains meta
				int mix = sourcename.indexOf('[');
				int max = sourcename.indexOf(']');
				String metastring = sourcename.substring(mix+1, max);
				meta = Integer.parseInt(metastring);
				sourcename=sourcename.replace("["+meta+"]", "");
			}
			int amount = Integer.parseInt(d[1]);
			
			//Search
			Item i = GameRegistry.findItem(hostMod, sourcename);
			if(i==null){
				throw new UserspaceLinkageException(linkString);
			}
			ItemStack proxy = new ItemStack(i,amount,meta);
			//Constructing stack
			IUniversalStack targetStack = new UniversalItemStack(proxy);
			//targetStack.setStackSize(amount);
			return targetStack;
		}else if(linkString.startsWith(DEFCONST_FLUID)){
			linkString=linkString.replace(DEFCONST_FLUID, "");
			String[] d = linkString.split(" # ");
			String sourcename = d[0];
			//DON'T REMOVE HOSTMOD FROM FLUID
			//String hostMod = sourcename.split(":")[0];
			//sourcename = sourcename.replace(hostMod+":", "");
			int meta = 0;
			if(sourcename.contains("[")){
				//Contains meta
				int mix = sourcename.indexOf('[');
				int max = sourcename.indexOf(']');
				String metastring = sourcename.substring(mix+1, max);
				meta = Integer.parseInt(metastring);
				sourcename=sourcename.replace("["+meta+"]", "");
			}
			int amount = Integer.parseInt(d[1]);
			
			//Search
			//Item i = GameRegistry.findItem(hostMod, sourcename);
			HOEFluid f = HOEFluidRegistry.getInstance().pop(sourcename);
			if(f==null){
				throw new UserspaceLinkageException(linkString);
			}
			HOEFluidStack proxy = new HOEFluidStack(f,amount);
			//ItemStack proxy = new ItemStack(i,amount,meta);
			//Constructing stack
			IUniversalStack targetStack = new UniversalFluidStack(proxy);
			//targetStack.setStackSize(amount);
			return targetStack;			
		
		}else if(linkString.equals(DEFCONST_NULL)){
			return null;
		}
		throw new UserspaceLinkageException(linkString);
	}
}
