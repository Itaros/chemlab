package ru.itaros.chemlab.hoe.io.syndication;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationEMFGeneratorData;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationHubData;
import ru.itaros.chemlab.hoe.data.syndication.SyndicationItemPortData;
import ru.itaros.hoe.data.ISynchroportItems;
import ru.itaros.hoe.data.machines.HOEMachineData;
import ru.itaros.hoe.data.utils.InventoryManager;
import ru.itaros.hoe.io.HOEMachineIO;
import ru.itaros.hoe.utils.StackUtility;

public class SyndicationHubIO extends HOEMachineIO {

	@Override
	public void configureData(HOEData data) {
		if(data instanceof SyndicationHubData){
			SyndicationHubData gcd = (SyndicationHubData)data;
			gcd.setMachine(this);
			gcd.ticksRequared=20;
			gcd.setConfigured();
		}

	}

	@Override
	protected boolean isMachineActive(HOEData data) {
		return false;//((SyndicationHubData)data).hasWork();
	}

	
	
	/*
	 * Bridge(non-Javadoc)
	 * @see ru.itaros.toolkit.hoe.machines.basic.io.HOEMachineIO#tick(ru.itaros.api.hoe.internal.HOEData, boolean)
	 */
	@Override
	public void tick(HOEData data, boolean doReal) {
		produce(data,doReal);
	}

	@Override
	protected void produce(HOEData data, boolean doReal) {
		SyndicationHubData d = (SyndicationHubData)data;
		
		
		InventoryManager inventory = d.getInventoryManager();
		if(inventory==null){return;}//failsafe
		inventory.peek();
		
		HOEMachineData[] machines=d.getListOfMachines();
		for(HOEMachineData hda:machines){
			if(hda==data){continue;}
			if(hda==null){
				System.out.println("fuck");
				}
			if(hda.isSyndicated()){
				hda.getIO().tick(hda, doReal);//Looks awkward
				specialOperations(d, hda);
				if(hda instanceof ISynchroportItems){
					inventoryOperations(inventory,(ISynchroportItems)hda);
				}
			}
		}
		//d.produce(doReal);
	
}



	private void inventoryOperations(InventoryManager inventory,
			ISynchroportItems hda) {
		SyndicationItemPortData data = inventory.pop();
		if(data==null){return;}
		if(hda instanceof SyndicationItemPortData){
			//There is no need to transfer items between IPorts
			return;
		}
		ItemStack filter = data.getFilter();
		ItemStack stack = data.get_in();
		
		if(data.getSOM().canIn()){
			stack=hda.tryToPutIn(stack,filter);
		}
		if(data.getSOM().canOut()){
			stack=hda.tryToGetOut(stack,filter);
		}
		
		stack=StackUtility.verify(stack);
		data.set_in(stack);
		//inventory.push(stack);
	}

	/*
	 * Anything like power transfer and item distribution
	 */
	private void specialOperations(SyndicationHubData medata, HOEMachineData hda) {
		if(hda instanceof SyndicationEMFGeneratorData){
			SyndicationEMFGeneratorData segd = (SyndicationEMFGeneratorData) hda;
			int power = (int) segd.getPower();
			segd.decrementPower(power);
			medata.injectPower(power);
			medata.getBattery().truncatePowerAmountToMax();
		}else{
			int needed=(int)hda.getNeededPower();
			int got = medata.ejectPower(needed);
			hda.incrementPower(got);
		}

	}
	
	
	
}
