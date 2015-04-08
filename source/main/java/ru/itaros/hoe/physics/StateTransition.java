package ru.itaros.hoe.physics;

import java.util.Iterator;

import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.itemhandling.MixtureStack;
import ru.itaros.hoe.itemhandling.UniversalStackFactory;

public class StateTransition implements IReaction, IReactionTempCutoffNotifier {

	private IMatterState matter;
	
	public StateTransition(IMatterState matter){
		this.matter=matter;
	}
	
	@Override
	public EventType getEventType() {
		return EventType.TEMP_CUTOFF;
	}

	@Override
	public float getLow() {
		return matter.lowerPoint();
	}

	@Override
	public float getHigh() {
		return matter.upperPoint();
	}

	public IMatterState getMatter(){
		return matter;
	}

	@Override
	public void perform(MixtureStack stack) {
		Iterator<IUniversalStack> i = stack.getViewIterator();
		while(i.hasNext()){
			IUniversalStack s = i.next();
			if(s.getItem()==matter){
				IMatterState n=null;
				if(stack.getCurrentTemperature()>=getHigh()){
					n=matter.upperForm();
				}else if(stack.getCurrentTemperature()<=getLow()){
					n=matter.lowerForm();
				}
				if(n!=null){
					stack.remove(s);
					IUniversalStack c = UniversalStackFactory.construct(n, s.getStackSize());
					stack.add(c);
				}
			}
		}
	}
	
}
