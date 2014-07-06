package ru.itaros.chemlab.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ChemLabWireItem extends ChemLabItem {

	public ChemLabWireItem(String name, int maxGauge) {
		super(name);
		
		this.setMaxDamage(maxGauge);
		this.setNoRepair();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack stack,
			EntityPlayer par2EntityPlayer, List l, boolean par4) {
		
		int dam = stack.getItemDamage();
		l.add(dam+" SWG");
		
		super.addInformation(stack, par2EntityPlayer, l, par4);
	}

	//@Override
	//public double getDurabilityForDisplay(ItemStack stack) {
	//	return 0;
	//}

	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return false;
	}
	
	
	
	
	

}
