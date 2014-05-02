package ru.itaros.chemlab.items.ore;

import java.util.ArrayList;

import ru.itaros.chemlab.convenience.ChemLabCreativeTab;
import net.minecraft.item.Item;

public class Dust extends Item {
	
	
	private String dict;
	private boolean isReversable;
	private int reversionRate=0;
	public Dust(String dict, int reversionRate){
		super();
		this.dict=dict;
		
		this.reversionRate=reversionRate;
		if(reversionRate<=0){
			isReversable=false;
		}else{
			isReversable=true;
		}
		
		this.setUnlocalizedName(assumeDustNameFor(dict));
		this.setTextureName("chemlab:dust-"+dict);
		this.setCreativeTab(ChemLabCreativeTab.getInstance());
	}
	
	
	public String getOredictName(){
		String dm = dict.substring(0, 1).toUpperCase() + dict.substring(1);
		return "dust"+dm;
	}
	
	public static String assumeDustNameFor(String s){
		return "dust."+s;
	}
	
}
