package ru.itaros.chemlab.items.ore;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import ru.itaros.chemlab.convenience.ChemLabCreativeTab;

public class CrushedOre extends Item {
	
	private static ArrayList<CrushedOre> index = new ArrayList<CrushedOre>();
	
	
	private String dict;
	private ItemStack sourceItem;
	
	public CrushedOre(String dict){
		super();
		this.dict=dict;
		
		sourceItem = OreDictionary.getOres(dict).get(0);
		
		index.add(this);
		init();
	}
	
	private void init(){
		this.setUnlocalizedName("chemlab:"+getClass().getSimpleName()+"-"+dict);
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
		this.setTextureName("chemlab:crushedore-"+dict);		
	}

	public static CrushedOre[] getAll() {
		CrushedOre[] a = new CrushedOre[index.size()];
		return index.toArray(a);
	}

	public ItemStack getSourceItem() {
		return sourceItem;
	}

	public String getDictName(){
		return dict;
	}
	public String getDictNameAsCrushed(){
		return dict.replaceAll("ore", "crushed");
	}
	
	
	//UI
	
	
	
	
}
