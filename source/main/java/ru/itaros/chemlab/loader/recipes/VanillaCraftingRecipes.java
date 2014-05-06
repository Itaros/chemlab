package ru.itaros.chemlab.loader.recipes;

import ru.itaros.chemlab.loader.BlockLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class VanillaCraftingRecipes {

	public static void load() {
		
		//CONTROL INTERFACE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.controlinterface), 
				"000",
				"BBB",
				"PPP",
				'P',new ItemStack(ItemLoader.pcbpad),
				'B',new ItemStack(Blocks.stone_button));
		
		
		//MACHINE CORE
		GameRegistry.addShapedRecipe(new ItemStack(ItemLoader.centralassembly), 
				"SCS",
				"SPS",
				"SMS",
				'M',new ItemStack(ItemLoader.mjconversionunit),
				'C',new ItemStack(ItemLoader.controlinterface),
				'S',new ItemStack(ItemLoader.screw),
				'P',new ItemStack(ItemLoader.pcbpad));		
		
		//MACHINE CASING
		GameRegistry.addShapedRecipe(new ItemStack(BlockLoader.casing), 
				"PFP",
				"FCF",
				"PFP",
				'F',new ItemStack(ItemLoader.frame),
				'P',new ItemStack(ItemLoader.panel),
				'C',new ItemStack(ItemLoader.centralassembly));
		
	}

}
