package ru.itaros.hoe.physics;

import java.util.Iterator;

import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.MixtureStack;

public class StateOnlyTransmutationGraphEvaluator implements
		IMixtureReactionGraphEvaluator {

	@Override
	public void evaluateAgainstMixture(MixtureStack mixture,
			MixtureReactionFramework framework) {
		
		//All mixture components can change state if heated\cooled
		
		Iterator<IUniversalStack> i = mixture.getViewIterator();

		while(i.hasNext()){
			IUniversalStack stack = i.next();
			
			Object h = stack.getItem();
			if(h instanceof IMatterState){
				IReaction r = new StateTransition((IMatterState) h);
				framework.addReaction(r);
			}
		}
		
		
	}

}
