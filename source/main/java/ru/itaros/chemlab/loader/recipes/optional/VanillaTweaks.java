package ru.itaros.chemlab.loader.recipes.optional;

import java.util.Map;

import ru.itaros.chemlab.items.tools.vanilla.*;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaTweaks {

	private static boolean isIronTweaked=false;
	
	public static void tweakIron(){
		if(TierLoader.L0_WroughtIron.isEnabled()){
			//Preparing
			ItemStack wroughtIronStack = new ItemStack(ItemLoader.wroughtiron);
			
			//Overriding vanilla smelting
			Map vanilla = FurnaceRecipes.smelting().getSmeltingList();
			ItemStack origin_iron = getOriginInMap(vanilla, Blocks.iron_ore);
			vanilla.put(origin_iron, wroughtIronStack);
			//Adding symmetrical recipe
			FurnaceRecipes.smelting().func_151394_a(OreDictionary.getOres("crushedIron").get(0).copy(), wroughtIronStack, 0);
			
			isIronTweaked=true;
		}
	}
	
	public static void createWroughtIronTools(){
		if(isIronTweaked){
			
			Item wrought_pickaxe = new WroughtIronPickaxe();
			Item wrought_axe = new WroughtIronAxe();
			Item wrought_hoe = new WroughtIronHoe();
			Item wrought_sword = new WroughtIronSword();
			Item wrought_spade = new WroughtIronSpade();
			
			GameRegistry.registerItem(wrought_pickaxe, wrought_pickaxe.getUnlocalizedName());
			GameRegistry.registerItem(wrought_axe, wrought_axe.getUnlocalizedName());
			GameRegistry.registerItem(wrought_hoe, wrought_hoe.getUnlocalizedName());
			GameRegistry.registerItem(wrought_sword, wrought_sword.getUnlocalizedName());
			GameRegistry.registerItem(wrought_spade, wrought_spade.getUnlocalizedName());
			
			GameRegistry.addRecipe(new ItemStack(wrought_pickaxe),
					"XXX",
					"0S0",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));
			
			
			GameRegistry.addRecipe(new ItemStack(wrought_axe),
					"XX0",
					"XS0",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));			
			GameRegistry.addRecipe(new ItemStack(wrought_axe),
					"0XX",
					"0SX",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));			
			
			GameRegistry.addRecipe(new ItemStack(wrought_hoe),
					"0XX",
					"0S0",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));			
			GameRegistry.addRecipe(new ItemStack(wrought_hoe),
					"XX0",
					"0S0",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));			

			GameRegistry.addRecipe(new ItemStack(wrought_spade),
					"0X0",
					"0S0",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));				
			
			
			GameRegistry.addRecipe(new ItemStack(wrought_sword),
					"0X0",
					"0X0",
					"0S0",
					'X',new ItemStack(ItemLoader.wroughtiron),
					'S',new ItemStack(Items.stick));			
			
			
		}
	}
	
	
	private static ItemStack getOriginInMap(Map<?,?> vanilla, Block target){
		Item pattern = Item.getItemFromBlock(target);
		for(Object origin:vanilla.keySet()){
			ItemStack o = (ItemStack)origin;
			if(o.getItem()==pattern){
				return o;
			}
		}		
		return null;
	}
	
}
