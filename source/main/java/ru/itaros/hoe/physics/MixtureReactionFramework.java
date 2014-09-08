package ru.itaros.hoe.physics;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import ru.itaros.hoe.itemhandling.MixtureStack;

/*
 * Defines possible transmutations in a mixture.
 * Cached on volume change or mutual transfers.
 * HOE DATA by IO invocation is responsible to evaluate vats. Otherwise they are considered autoinert!
 */
public class MixtureReactionFramework {

	private MixtureStack vat;
	
	private float lowestTempCutoff,higherTempCutoff;
	private IReaction lowerNeareastTempEventCutoff,higherNeareastTempEventCutoff;
	private ArrayList<IReaction> reactions = new ArrayList<IReaction>();//Expected to happen in current mix
	
	private Queue<IReaction> happensNow = new LinkedList<IReaction>();//Happening right now, or time-tunneled into present time from past
	
	public MixtureReactionFramework(MixtureStack vat, IReaction...reactions){
		this.vat=vat;
		for(IReaction r:reactions){
			addReaction(r,false);
		}
		reevaluate();
	}
	
	public void addReaction(IReaction reaction){
		addReaction(reaction,true);
	}
	
	protected void addReaction(IReaction reaction,boolean reevaluate){
		reactions.add(reaction);
		if(reevaluate){
			reevaluate();
		}
	}
	
	private void clearOngoingAndScheduledReactions(){
		reactions.clear();
		happensNow.clear();
	}
	
	private IMixtureReactionGraphEvaluator evaluator;
	
	public void update(){
		if(vat!=null){
			//Evaluating state transitions due to energy supplied/taken
			IReaction reaction=null;
			float temp = vat.getCurrentTemperature();
			if(temp<lowestTempCutoff){
				reaction=lowerNeareastTempEventCutoff;
			}else if(temp>higherTempCutoff){
				reaction=higherNeareastTempEventCutoff;
			}
			if(reaction!=null){
				reaction.perform(vat);
			}
			
			//Evaluating new additions
			if(vat.queryAdditionalComponents()){
				//New mixture components arrived. Time to reevaluate shit
				clearOngoingAndScheduledReactions();
				if(evaluator!=null){
					evaluator.evaluateAgainstMixture(vat,this);
				}else{
					throw new IllegalStateException("MixtureReactionGraphEvaluator is not specified!");
				}
			}
		}
	}
	
	private void reevaluate() {
		lowestTempCutoff=Float.MIN_VALUE;
		higherTempCutoff=Float.MAX_VALUE;
		for(IReaction r:reactions){
			if(r instanceof IReactionTempCutoffNotifier){
				IReactionTempCutoffNotifier irctcn = (IReactionTempCutoffNotifier)r;
				if(irctcn.getLow()>lowestTempCutoff){
					if(irctcn.getLow()<vat.getCurrentTemperature()){
						//SCHEDULABLE
						lowestTempCutoff=irctcn.getLow();
						lowerNeareastTempEventCutoff=r;
					}else{
						//HAPPENED IN THE PAST. FFFFUUUCK!
						happensNow.add(r);
					}
				}
				if(irctcn.getHigh()<higherTempCutoff){
					if(irctcn.getHigh()>vat.getCurrentTemperature()){
						//SCHEDULABLE
						higherTempCutoff=irctcn.getHigh();
						higherNeareastTempEventCutoff=r;
					}else{
						//HAPPENED IN THE PAST. FFFFUUUCK!
						happensNow.add(r);
					}
				}				
			}
		}
	}
	
}
