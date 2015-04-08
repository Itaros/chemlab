package ru.itaros.chemlab.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.itaros.hoe.framework.chemistry.ChemicalCompound;
import ru.itaros.hoe.framework.chemistry.ChemicalReaction;
import ru.itaros.hoe.framework.chemistry.StoichiometricCoefficient;
import ru.itaros.hoe.framework.chemistry.registries.ReactionDatabase;

public class ChemLabChemicalItem extends ChemLabItem {

	private ChemicalCompound[] composition;
	
	private ChemicalReaction[] reactiveIndexAsResource;
	
	public ChemLabChemicalItem(String groupname, String name, ChemicalCompound[] composition) {
		super(groupname, name);
		this.composition=composition;
	}

	public void buildReactiveIndex(){
		reactiveIndexAsResource = ReactionDatabase.getInstance().getReactionsForResources(composition);
	}
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player,
			List list, boolean whatever) {
		
		//list.add("Composition:");
		
		if(composition!=null && composition.length>0){
			String formulae="";
			for(int i = 0 ; i < composition.length; i++){
				ChemicalCompound cc = composition[i];
				if(cc==null){continue;}// Ewh. Something is broken. Fail silently :/
				formulae+=cc.getStoichiometryString();
				if(composition.length-1>i){
					formulae+=",";
				}
			}
			list.add(formulae);
		}
		
		super.addInformation(stack, player, list, whatever);
	}

	/*
	 * This method is SLOW AS HELL. Beware!
	 * (Must be superseded by lookup pool)
	 * !!!CHECK -HACK- section!!!
	 */
	public ChemicalReaction getAtmospericCombustionReaction(){
		for(ChemicalReaction r : reactiveIndexAsResource){
			StoichiometricCoefficient[] ress = r.getResources();
			return r;//HACK: You know it doesn't work as intended
			//But this will accelerate things for Dom
		}
		return null;
	}
	
}
