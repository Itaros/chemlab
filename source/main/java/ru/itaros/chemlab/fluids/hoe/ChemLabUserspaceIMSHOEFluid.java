package ru.itaros.chemlab.fluids.hoe;

import ru.itaros.chemlab.addon.cl3.userspace.UserspaceParsingAssist;
import ru.itaros.hoe.fluid.HOEFluid;
import ru.itaros.hoe.fluid.HOEFluid.HOEFluidState;
import ru.itaros.hoe.itemhandling.IUniversalStack;
import ru.itaros.hoe.physics.IMatterState;

public class ChemLabUserspaceIMSHOEFluid extends HOEFluid implements
		IMatterState {

	private String groupname,name;
	private String unlocalizedName;
	public ChemLabUserspaceIMSHOEFluid(String groupname, String name){
		this.groupname=groupname;
		this.name=name;
		unlocalizedName = "chemlab:"+"fluid."+groupname+"."+name;
		detectCommonName();
	}
	
	private int lowerPoint,upperPoint;
	private IMatterState lowerForm,upperForm;
	private float heatCapacity;
	private long resistance;	
	
	public final void setLowerPoint(int lowerPoint) {
		this.lowerPoint = lowerPoint;
	}
	public final void setUpperPoint(int upperPoint) {
		this.upperPoint = upperPoint;
	}
	public final void setLowerForm(IMatterState lowerForm) {
		this.lowerForm = lowerForm;
	}
	public final void setUpperForm(IMatterState upperForm) {
		this.upperForm = upperForm;
	}
	public final void setHeatCapacity(float heatCapacity) {
		this.heatCapacity = heatCapacity;
	}
	public final void setResistance(long resistance) {
		this.resistance = resistance;
	}
	
	@Override
	public int lowerPoint() {
		return lowerPoint;
	}

	@Override
	public IMatterState lowerForm() {
		return lowerForm;
	}

	@Override
	public int upperPoint() {
		return upperPoint;
	}

	@Override
	public IMatterState upperForm() {
		return upperForm;
	}

	@Override
	public float heatCapacity() {
		return heatCapacity;
	}

	@Override
	public long resistance() {
		return resistance;
	}
	
	private String lowerItemSearchString;
	private String upperItemSearchString;
	public void setLowerItemLinkString(String itemLower) {
		lowerItemSearchString=itemLower;
	}
	public void setUpperItemLinkString(String itemUpper) {
		upperItemSearchString=itemUpper;
	}
	
	
	public void link() {
		IUniversalStack lowerFormStack = UserspaceParsingAssist.parseLinkString(lowerItemSearchString);
		IUniversalStack upperFormStack = UserspaceParsingAssist.parseLinkString(upperItemSearchString);
		if(lowerFormStack!=null){
			lowerForm = (IMatterState)lowerFormStack.getItem();
		}
		if(upperFormStack!=null){
			upperForm = (IMatterState)upperFormStack.getItem();
		}
	}
	
	
	@Override
	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	@Override
	public int getColor() {
		return 0;
	}
	@Override
	public HOEFluidState getState() {
		return HOEFluidState.LIQUID;
	}

}
