package ru.itaros.chemlab.loader.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.items.HiVolumeLiquidCell;
import ru.itaros.chemlab.loader.HOEFluidLoader;
import ru.itaros.chemlab.loader.ItemLoader;
import ru.itaros.chemlab.loader.TierLoader;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionMetaUnawareRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.FixedConversionRecipe;
import ru.itaros.toolkit.hoe.machines.basic.io.minecraft.recipes.RecipesCollection;

public class HiTFurnaceRecipes {

	public static RecipesCollection recipes;
	
	public static final String[] smeltableDusts = {
		"Gold",
		"Iron"
		//"Platinum"
	};
	
	public static void load(){
		ItemStack emptyhvlcs;
		//Dusts
		FixedConversionRecipe[] dusts = new FixedConversionRecipe[smeltableDusts.length];
		int x = -1;
		for(String s:smeltableDusts){
			x++;
			String dustName = "dust"+s;
			String ingotName = "ingot"+s;
			
			ItemStack i = OreDictionary.getOres(dustName).get(0).copy();
			ItemStack o = OreDictionary.getOres(ingotName).get(0).copy();
			
			
			FixedConversionRecipe fcr = new FixedConversionRecipe(100,100,i,o);
			fcr.setUnlocalizedName("furnace.dusts."+s.toLowerCase());
			dusts[x]=fcr;
		}
		//Compisitors & decompositors
		ItemStack pyrite_dust = OreDictionary.getOres("dustPyrite").get(0).copy();
		pyrite_dust.stackSize=4;
		ItemStack oxygene = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.oxygen_gas),11);
		ItemStack[] po_i = new ItemStack[]{pyrite_dust,oxygene};
		ItemStack sulphurdioxide = new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.sulphurdioxide_gas),8);
		ItemStack ferricoxide = new ItemStack(ItemLoader.ferricoxide,2);
		emptyhvlcs = new ItemStack(ItemLoader.emptyhvlc,11-8);
		ItemStack[] po_o = new ItemStack[]{ferricoxide,sulphurdioxide,emptyhvlcs};
		FixedConversionRecipe pyriteoxygeneation = new FixedConversionRecipe(200,200,po_i,po_o);
		pyriteoxygeneation.setUnlocalizedName("furnace.oxpyrite");
		
		//Carbothermic reaction
		emptyhvlcs = new ItemStack(ItemLoader.emptyhvlc,3);
		ItemStack[] i = {new ItemStack(ItemLoader.ferricoxide,2),new ItemStack(ItemLoader.amorphousGraphite,3),emptyhvlcs};
		ItemStack[] o = {new ItemStack(ItemLoader.pigiron,4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbondioxide_gas),3)};
		FixedConversionRecipe carbothermic_ferricoxide = new FixedConversionRecipe(100,100,i,o);
		carbothermic_ferricoxide.setUnlocalizedName("furnace.carbothermal.ferricoxide");
		
		//Carbothermic reaction for gematite with lime
		ItemStack hematite = OreDictionary.getOres("crushedHematite").get(0).copy();
		hematite.stackSize=2;
		ItemStack limestone = OreDictionary.getOres("crushedLimestone").get(0).copy();
		limestone.stackSize=1;	
		emptyhvlcs = new ItemStack(ItemLoader.emptyhvlc,3);
		i = new ItemStack[]{hematite,limestone,new ItemStack(ItemLoader.amorphousGraphite,3),emptyhvlcs};
		o = new ItemStack[]{new ItemStack(ItemLoader.sulfuricatedpigiron,4),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbondioxide_gas),3),new ItemStack(ItemLoader.slag,1)};
		FixedConversionRecipe carbothermic_ferricoxide_hematite = new FixedConversionRecipe(100,100,i,o);
		carbothermic_ferricoxide_hematite.setUnlocalizedName("furnace.carbothermal.ferricoxide_hematite");		
		
		emptyhvlcs = new ItemStack(ItemLoader.emptyhvlc,1);
		i = new ItemStack[]{OreDictionary.getOres("crushedPericlase").get(0).copy(),new ItemStack(ItemLoader.amorphousGraphite),emptyhvlcs};
		o = new ItemStack[]{new ItemStack(ItemLoader.magnesium),new ItemStack(HiVolumeLiquidCell.getByFluid(HOEFluidLoader.carbonmonooxide_gas))};
		FixedConversionRecipe carbothermic_mgo = new FixedConversionRecipe(100,100,i,o);
		carbothermic_mgo.setUnlocalizedName("furnace.carbothermal.magnesium");
		
		FixedConversionRecipe wrought_wire=null;
		FixedConversionRecipe iron_wire=null;
		if(TierLoader.L0_WroughtIron.isEnabled()){
			wrought_wire = new FixedConversionMetaUnawareRecipe(100,500,new ItemStack(ItemLoader.rod_swg_brittle_wroughtIron),new ItemStack(ItemLoader.rod_swg_hot_wroughtIron));
			wrought_wire.setUnlocalizedName("furnace.wire.wrought");
		}
		iron_wire = new FixedConversionMetaUnawareRecipe(100,500,new ItemStack(ItemLoader.rod_swg_brittle_iron),new ItemStack(ItemLoader.rod_swg_hot_iron));
		iron_wire.setUnlocalizedName("furnace.wire.iron");
		
		recipes = new RecipesCollection(dusts);
		recipes.injectAfter(pyriteoxygeneation,carbothermic_ferricoxide,carbothermic_ferricoxide_hematite,carbothermic_mgo);
		recipes.injectAfter(wrought_wire,iron_wire);
		recipes.register();
		
		
	}
	
}
