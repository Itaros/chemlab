//package ru.itaros.chemlab.hoe.io.special;
//
//import ru.itaros.api.hoe.internal.HOEData;
//import ru.itaros.chemlab.hoe.data.HVLCFillerData;
//import ru.itaros.chemlab.loader.HOEFluidLoader;
//import ru.itaros.hoe.io.HOEMachineIO;
//
//public class HVLCFillerIO extends HOEMachineIO {
//
//	@Override
//	public void configureData(HOEData data) {
//		
//		if(data instanceof HVLCFillerData){
//			HVLCFillerData hff = (HVLCFillerData)data;
//			hff.setMachine(this);
//			hff.configureDepot();
//			hff.setConfigured();
//		}
//	}
//
//	@Override
//	protected boolean isMachineActive(HOEData data) {
//		if(data instanceof HVLCFillerData){
//			HVLCFillerData hff = (HVLCFillerData)data;	
//			if(hff.getCellsCount()>=64){
//				return false;//Stuffed
//			}
//			if(!hff.isThereSpareCell()){return false;}
//			if(hff.getFluidDepot().extractAtomic(HOEFluidLoader.water_natural, 1000, false)){
//				return true;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	protected void produce(HOEData data, boolean doReal) {
//		if(data instanceof HVLCFillerData){
//			HVLCFillerData hff = (HVLCFillerData)data;
//			if(hff.decrementEmptyCells()){
//				if(hff.getFluidDepot().extractAtomic(HOEFluidLoader.water_natural, 1000, doReal)){
//					hff.incrementCellsCount();
//				}
//			}
//		}
//		
//
//	}
//
//}
