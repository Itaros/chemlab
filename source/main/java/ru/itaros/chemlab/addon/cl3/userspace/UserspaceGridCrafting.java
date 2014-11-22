package ru.itaros.chemlab.addon.cl3.userspace;

import javax.xml.bind.annotation.XmlElement;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class UserspaceGridCrafting extends UserspaceContract {

	public String recipe;
	
	@XmlElement(name="component")
	public UserspaceCraftingLink[] components;
	
	public UserspaceLink result;
	
	public void registerRecipe(){
		
		ItemStack res = (ItemStack) result.getTarget(null);//We need ONLY item
		String[] rstring;
		rstring = recipe.split("\\|");
		
		Object[] parr=new Object[rstring.length+(components.length*2)];
		
		parr[0]=rstring[0];
		parr[1]=rstring[1];
		parr[2]=rstring[2];
		int i = 3;
		for(int o=0;o<components.length;o++){
			UserspaceCraftingLink ucl = components[o];
			parr[i]=ucl.literal.charAt(0);
			i++;
			parr[i]=ucl.getTarget(null);//Only ItemStack
			i++;
		}
		
		GameRegistry.addRecipe(res, parr);
	}
	
}
