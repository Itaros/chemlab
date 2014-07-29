package ru.itaros.chemlab.hoe.messages;

import net.minecraft.item.ItemStack;
import ru.itaros.api.hoe.internal.HOEData;
import ru.itaros.hoe.data.IHasReplacableParts;
import ru.itaros.hoe.io.HOEDataMessage;

public class ReplacePartMessage extends HOEDataMessage {

	private ItemStack part;
	
	@Override
	public void run(HOEData context) {
		if(context instanceof IHasReplacableParts){
			IHasReplacableParts ihrp = (IHasReplacableParts)context;
			if(!ihrp.requiresReplacements()){
				this.setAnswered();
			}else{
				//For now...
				part=ihrp.getTypeOfReplacableRequired().copy();
				part=ihrp.exchangeParts(part);
				this.setAnswered();
			}
		}
		
	}

}
